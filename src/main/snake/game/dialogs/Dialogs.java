package snake.game.dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import snake.Main;
import snake.game.configuration.TextParameters;
import snake.game.data.PlayerData;

public class Dialogs {
	final static Logger logger = Logger.getLogger(Dialogs.class);

	private final JPanel parent;

	public Dialogs(JPanel parent) {
		this.parent = parent;
	}

	public String gameOverDialog(int score) {
		String name = "";
		while (name.equals("")) {
			name = JOptionPane.showInputDialog(parent, TextParameters.GAME_OVER);
		}
		return name;
	}

	public void partOverDialog() {
		JOptionPane.showMessageDialog(parent, TextParameters.PART_OVER);
	}

	public void highScoreDialog(List<PlayerData> top) {
		JDialog dialog = new JDialog();

		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setLayout(new BorderLayout());
		dialog.setTitle(TextParameters.TOP_10_BUTTON);
		Object data[][] = new Object[10][3];
		for (PlayerData player : top) {
			Object[] rowData = new Object[] { top.indexOf(player) + 1, player.getScore(), player.getName() };
			data[top.indexOf(player)] = rowData;
			logger.debug("TopPlayers -> " + player.getName() + " : " + player.getScore());
		}
		JTable table = new JTable(data, TextParameters.HITLIST_HEADING);
		dialog.add(new JScrollPane(table));
		dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocation(Main.frame.getWidth() / 2 - dialog.getWidth() / 2,
				Main.frame.getHeight() / 2 - dialog.getHeight() / 2);
		dialog.setLocationRelativeTo(Main.frame);
		dialog.setVisible(true);
		parent.setFocusable(true);
		parent.requestFocusInWindow();
	}

}
