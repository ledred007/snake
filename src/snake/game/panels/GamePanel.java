package snake.game.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import snake.game.configuration.GameParameters;
import snake.game.data.GamePanelData;
import snake.game.services.GameService;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	final static Logger logger = Logger.getLogger(GamePanel.class);
	private static final long serialVersionUID = 3007L;

	private final GamePanelData gamePanelData;
	private final int panelWidth;
	private final int panelHeight;
	private Thread thread;
	private boolean isRuning;
	private boolean showGrid = false;
	private GameService service;

	public GamePanel(GamePanelData gamePanelData, GameService service) {
		this.gamePanelData = gamePanelData;
		this.showGrid = gamePanelData.isShowGrid();
		this.service = service;
		this.service.initService(gamePanelData, this);

		panelWidth = gamePanelData.getWidth() * gamePanelData.getCellSize();
		panelHeight = gamePanelData.getWidth() * gamePanelData.getCellSize();

		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		start();
	}

	public void start() {
		isRuning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		isRuning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (!service.tick()) {
			stop();
		}
	}

	public void paint(Graphics graphics) {
		paintBackground(graphics);
		if (showGrid) {
			paintGrid(graphics);
		}
		service.paint(graphics);
	}

	@Override
	public void run() {
		while (isRuning) {
			tick();
			repaint();
			try {
				Thread.sleep(GameParameters.THREAD_SLEEP_IN_MILISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				stop();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyEventProcess(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	void setGameService(GameService service) {
		this.service = service;
	}

	private void paintBackground(Graphics graphics) {
		graphics.clearRect(0, 0, panelWidth, panelHeight);
		graphics.setColor(GameParameters.GAME_PANEL_BACKGROUND_COLOR);
		graphics.fillRect(0, 0, panelWidth, panelHeight);
	}

	private void paintGrid(Graphics graphics) {
		graphics.setColor(GameParameters.GAME_PANEL_GRID_COLOR);
		for (int i = 0; i < gamePanelData.getWidth(); i++) {
			graphics.drawLine(i * gamePanelData.getCellSize(), 0, i * gamePanelData.getCellSize(), panelHeight);
		}
		for (int i = 0; i < gamePanelData.getHeight(); i++) {
			graphics.drawLine(0, i * gamePanelData.getCellSize(), panelWidth, i * gamePanelData.getCellSize());
		}
	}

	private void keyEventProcess(int key) {
		service.keyEventProcess(key);
	}

}
