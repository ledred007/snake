package snake.game.services;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

import snake.game.dao.GameDao;
import snake.game.data.GamePanelData;
import snake.game.data.PlayerData;
import snake.game.dialogs.Dialogs;
import snake.game.panels.StatusPanel;

public class GameServiceTest {

	class MockGameDao implements GameDao {

		public boolean isReady() {
			return true;
		}

		public int getHighScore() {
			return 123;
		}

		public boolean savePlayerData(PlayerData player) {
			return true;
		}

		public LinkedList<PlayerData> getTopPlayers() {
			return new LinkedList<PlayerData>();
		}

	}

	class MockStatusLabel extends JLabel {
		private static final long serialVersionUID = 12340071L;

	}

	class MockJavaButton extends JButton {
		private static final long serialVersionUID = 23450071L;

	}

	class MockDialogs extends Dialogs {

		public MockDialogs(JPanel parent) {
			super(parent);
		}

	}

	private final GameDao dbase = new MockGameDao();
	private final JLabel statusLabel = new MockStatusLabel();
	private final JButton javaButton = new MockJavaButton();
	private final StatusPanel statusPanel = new StatusPanel(statusLabel, javaButton);
	private final JPanel parent = new JPanel();
	private final Dialogs dialogs = new MockDialogs(parent);

	@Test(expected = NullPointerException.class)
	public void constructorWithNull() {
		new GameService(null, null);
	}

	@Test
	public void constructorWithMock() {
		GameService gameService = new GameService(dbase, statusPanel);
		GamePanelData gamePanelData = new GamePanelData(50, 50, 10, false);
		gameService.initService(gamePanelData, dialogs);
	}

	// TODO: Test for SnakeBody.crashedIntoItself

}
