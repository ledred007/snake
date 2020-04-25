package snake.game.data;

public class PanelData {

	private final int width;
	private final int height;
	private final int cellSize;

	public PanelData(int width, int height, int cellSize) {
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCellSize() {
		return cellSize;
	}

}
