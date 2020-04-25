package snake.game.configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public final class PanelParameters {

	private static final LineBorder lineBorder = new LineBorder(Color.DARK_GRAY);
	private static final EmptyBorder emptyBorder = new EmptyBorder(2, 2, 2, 2);
	public static final CompoundBorder PANEL_BORDER = new CompoundBorder(lineBorder, emptyBorder);
	public static final EmptyBorder STATUS_LINE_BORDER = new EmptyBorder(0, 0, 0, 20);
	public static final FlowLayout PLAYGROUND_LAYOUT = new FlowLayout(FlowLayout.CENTER);
	public static final GridBagLayout STATUS_LAYOUT = new GridBagLayout();
	public static final BorderLayout FRAME_LAYOUT = new BorderLayout();

}
