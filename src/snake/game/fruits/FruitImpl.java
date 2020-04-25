package snake.game.fruits;

import java.awt.Graphics;

import snake.game.data.CellData;

public class FruitImpl implements Fruit {

	private CellData cellData;

	public FruitImpl(CellData cellData) {
		this.cellData = cellData;
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillRect(cellData.getX() * cellData.getWidth(), cellData.getY() * cellData.getHeight(), cellData.getWidth(),
				cellData.getHeight());
	}

	public int getX() {
		return cellData.getX();
	}

	public int getY() {
		return cellData.getY();
	}

}
