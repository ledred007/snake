package snake.game.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import snake.game.configuration.PanelParameters;
import snake.game.configuration.TextParameters;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 2007L;

	private final JLabel statusLabel;
	private final JButton javaButton;

	public StatusPanel() {
		super(PanelParameters.STATUS_LAYOUT);
		this.setBorder(PanelParameters.PANEL_BORDER);
		statusLabel = new JLabel(TextParameters.STATUS_LABEL_START);
		statusLabel.setBorder(PanelParameters.STATUS_LINE_BORDER);
		javaButton = new JButton(TextParameters.TOP_10_BUTTON);
		this.add(statusLabel);
		this.add(javaButton);
	}

	// For testing ...
	public StatusPanel(JLabel statusLabel, JButton javaButton) {
		super(PanelParameters.STATUS_LAYOUT);
		this.setBorder(PanelParameters.PANEL_BORDER);
		this.statusLabel = statusLabel;
		this.javaButton = javaButton;
		this.add(statusLabel);
		this.add(javaButton);
	}

	public JLabel getStatusLabel() {
		return statusLabel;
	}

	public JButton getJavaButton() {
		return javaButton;
	}

}
