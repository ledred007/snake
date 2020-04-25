package snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import snake.game.configuration.GameParameters;
import snake.game.configuration.PanelParameters;
import snake.game.configuration.TextParameters;
import snake.game.dao.DerbySqlGameDao;
import snake.game.dao.GameDao;
import snake.game.data.GamePanelData;
import snake.game.panels.GamePanel;
import snake.game.panels.PlayGroundPanel;
import snake.game.panels.StatusPanel;
import snake.game.services.GameService;
import snake.game.utils.Conversion;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);

	public static JFrame frame;

	public Main(int width, int height, GameDao dbase) {
		frame = new JFrame();

		JPanel playGroundPanel = new PlayGroundPanel();
		StatusPanel statusPanel = new StatusPanel();

		GameService service = new GameService(dbase, statusPanel);
		GamePanelData gamePanelData = new GamePanelData(width, height, GameParameters.CELL_SIZE,
				GameParameters.GAME_PANEL_SHOW_GRID);
		JPanel gamePanel = new GamePanel(gamePanelData, service);

		playGroundPanel.add(gamePanel);

		frame.setLayout(PanelParameters.FRAME_LAYOUT);

		frame.add(playGroundPanel, BorderLayout.NORTH);
		frame.add(statusPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TextParameters.GAME_TITLE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		if (2 <= args.length) {
			int width = Conversion.getPositiveInteger(args[0]);
			int height = Conversion.getPositiveInteger(args[1]);
			LinkedList<String> errors = checkInputData(width, height);
			if (0 == errors.size()) {
				GameDao dbase = new DerbySqlGameDao();
				if (dbase.isReady()) {
					logger.info("Start-> " + TextParameters.GAME_TITLE);
					new Main(width, height, dbase);
				} else {
					logger.error("Database Open error!");
					System.exit(0);
				}
			} else {
				for (String error : errors) {
					logger.error(error);
				}
				System.exit(0);
			}
		} else {
			logger.info("Proper Usage is: java sneak <width> <height>");
			System.exit(0);
		}
	}

	private static LinkedList<String> checkInputData(int width, int height) {
		LinkedList<String> result = new LinkedList<String>();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (width < GameParameters.PANEL_MIN_WIDTH | height < GameParameters.PANEL_MIN_HEIGHT) {
			result.add("Proper Usage is: java sneak <width> <height>");
			result.add("- <width> and <height> are positive integer values");
			result.add("- <width> at least " + GameParameters.PANEL_MIN_WIDTH);
			result.add("- <height> at least " + GameParameters.PANEL_MIN_HEIGHT);
		} else if (screenSize.getWidth() < (width * GameParameters.CELL_SIZE) + GameParameters.WIDTH_GAP ||
				screenSize.getHeight() < (height * GameParameters.CELL_SIZE) + GameParameters.HEIGHT_GAP) {
			int maxWidth = (int) (screenSize.getWidth() - GameParameters.WIDTH_GAP) / GameParameters.CELL_SIZE;
			int maxHeight = (int) (screenSize.getHeight() - GameParameters.HEIGHT_GAP) / GameParameters.CELL_SIZE;
			result.add("The <width> or the <height> parameter too big for the display.");
			result.add("The possible value of the maximum <width> is " + maxWidth);
			result.add("The possible value of the maximum <height> is " + maxHeight);
		}
		return result;
	}

}
