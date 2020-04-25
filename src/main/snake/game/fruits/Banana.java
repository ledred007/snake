package snake.game.fruits;

import java.awt.Color;

import snake.game.configuration.GameParameters;
import snake.game.data.CellData;

public class Banana extends FruitImpl {

	public Banana(CellData cellData) {
		super(cellData);
	}

	public Color getColor() {
		return GameParameters.BANANA_COLOR;
	}

}
