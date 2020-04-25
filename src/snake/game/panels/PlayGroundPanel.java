package snake.game.panels;

import javax.swing.JPanel;

import snake.game.configuration.PanelParameters;

public class PlayGroundPanel extends JPanel {
	private static final long serialVersionUID = 1007L;

	public PlayGroundPanel() {
		super(PanelParameters.PLAYGROUND_LAYOUT);
		this.setBorder(PanelParameters.PANEL_BORDER);
	}

}
