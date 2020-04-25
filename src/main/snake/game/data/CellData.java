package snake.game.data;

public class CellData {

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	public CellData(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getCenterX() {
		return x * width + ((float) width) / 2;
	}

	public float getCenterY() {
		return y * height + ((float) height) / 2;
	}

}
