package snake.game.services;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import snake.game.configuration.GameParameters;
import snake.game.configuration.TextParameters;
import snake.game.dao.GameDao;
import snake.game.data.GamePanelData;
import snake.game.data.PanelData;
import snake.game.data.PlayerData;
import snake.game.dialogs.Dialogs;
import snake.game.fruits.Fruit;
import snake.game.fruits.FruitController;
import snake.game.panels.StatusPanel;
import snake.game.snake.SnakeBody;

public class GameService {
	final static Logger logger = Logger.getLogger(GameService.class);

	private final GameDao dbase;
	private final JLabel statusLabel;
	private final int highScore;
	private Dialogs dialogs;
	private int lifeCounter = GameParameters.PLAYER_LIFE_MAX;
	private int score = 0;
	private SnakeBody snakeBody;
	private Fruit fruit;
	private ZoneId zone = ZoneId.systemDefault();
	private boolean isStopped;

	public GameService(GameDao dbase, StatusPanel statusPanel) {
		this.dbase = dbase;
		this.statusLabel = statusPanel.getStatusLabel();
		statusPanel.getJavaButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diplayTopPlayers();
			}
		});
		this.highScore = getHighScore();
	}

	public void initService(GamePanelData gamePanelData, JPanel parent) {
		dialogs = new Dialogs(parent);
		PanelData firstCell = new PanelData(gamePanelData.getWidth(), gamePanelData.getHeight(),
				gamePanelData.getCellSize());
		FruitController.initController(firstCell);
		SnakeBody snakeBody = new SnakeBody(firstCell);
		initService(snakeBody);
	}

	// Usefull for testing purpose
	void initService(SnakeBody snakeBody) {
		this.snakeBody = snakeBody;
		displayStatus();
		start();
	}

	public boolean tick() {
		boolean isRuning = true;
		if (!isStopped) {
			ZonedDateTime now = ZonedDateTime.now(zone);
			if (now.isAfter(snakeBody.getNextMoveTime())) {
				if (snakeBody.move()) {
					if (fruit != null && snakeBody.isHeadOnFruit(fruit)) {
						snakeBody.eatFruit();
						score++;
						FruitController.resetNextCreateTime();
						fruit = null;
						displayStatus();
					}
					snakeBody.crawl();
				} else {
					lifeCounter--;
					displayStatus();
					if (0 < lifeCounter) {
						partOver();
						logger.debug("RESET -> " + lifeCounter);
						snakeBody.reset();
						fruit = null;
					} else {
						gameOver(score);
						logger.debug("GAMEOVER - points -> " + score);
						isRuning = false;
					}
				}
			}
			if (fruit == null && now.isAfter(FruitController.getNextCreateTime())) {
				fruit = FruitController.createFruit(snakeBody);
			}
		}
		return isRuning;
	}

	public void paint(Graphics graphics) {
		if (fruit != null) {
			fruit.draw(graphics);
		}
		snakeBody.draw(graphics);
	}

	private void start() {
		isStopped = false;
	}

	private void stop() {
		isStopped = true;
	}

	public void keyEventProcess(int key) {
		snakeBody.keyEventProcess(key);
	}

	private int getHighScore() {
		return dbase.getHighScore();
	}

	private void diplayTopPlayers() {
		stop();
		LinkedList<PlayerData> top = dbase.getTopPlayers();
		dialogs.highScoreDialog(top);
		start();
	}

	private void partOver() {
		dialogs.partOverDialog();
	}

	private void gameOver(int score) {
		String name = dialogs.gameOverDialog(score);
		PlayerData player = new PlayerData(name, score);
		dbase.savePlayerData(player);
	}

	private void displayStatus() {
		statusLabel.setText(String.format(TextParameters.STATUS_FORMAT, lifeCounter, score, highScore));
	}

}
