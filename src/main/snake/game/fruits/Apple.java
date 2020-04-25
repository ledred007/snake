package snake.game.fruits;

import java.awt.Color;

import snake.game.configuration.GameParameters;
import snake.game.data.CellData;

public class Apple extends FruitImpl {

	public Apple(CellData cellData) {
		super(cellData);
	}

	public Color getColor() {
		return GameParameters.APPLE_COLOR;
	}

}
