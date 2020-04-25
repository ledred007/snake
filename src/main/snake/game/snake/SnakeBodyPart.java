package snake.game.snake;

import java.awt.Graphics;

import snake.game.configuration.GameParameters;
import snake.game.data.CellData;

public class SnakeBodyPart {

	private final CellData cellData;

	public SnakeBodyPart(CellData cellData) {
		this.cellData = cellData;
	}

	public void draw(Graphics g) {
		g.setColor(GameParameters.SNAKE_COLOR);
		g.fillRect(cellData.getX() * cellData.getWidth(),
				cellData.getY() * cellData.getHeight(),
				cellData.getWidth(), cellData.getHeight());

	}

	int getX() {
		return cellData.getX();
	}

	int getY() {
		return cellData.getY();
	}

	float getCenterX() {
		return cellData.getCenterX();
	}

	float getCenterY() {
		return cellData.getCenterY();
	}

}
