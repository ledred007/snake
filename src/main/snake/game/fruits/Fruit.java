package snake.game.fruits;

import java.awt.Color;
import java.awt.Graphics;

import snake.game.configuration.GameParameters;

public interface Fruit {

	void draw(Graphics g);

	int getX();

	int getY();

	default Color getColor() {
		return GameParameters.FRUIT_COLOR;
	}

}
