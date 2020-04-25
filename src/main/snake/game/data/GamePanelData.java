package snake.game.data;

public class GamePanelData extends PanelData {

	private final boolean showGrid;

	public GamePanelData(int width, int height, int cellSize, boolean showGrid) {
		super(width, height, cellSize);
		this.showGrid = showGrid;
	}

	public boolean isShowGrid() {
		return showGrid;
	}

}
