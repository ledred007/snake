package snake.game.snake;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import snake.game.data.CellData;

public class SnakeBodyPartTest {

	@Test
	public void getCenterOnAllZeros() {
		CellData cellData = new CellData(0, 0, 0, 0);
		SnakeBodyPart snakeBodyPart = new SnakeBodyPart(cellData);
		assertEquals(0.0, snakeBodyPart.getCenterX(), 0.0);
		assertEquals(0.0, snakeBodyPart.getCenterY(), 0.0);
	}

	@Test
	public void getCenterOnIntegerResults() {
		CellData cellData = new CellData(10, 20, 10, 12);
		SnakeBodyPart snakeBodyPart = new SnakeBodyPart(cellData);
		assertEquals(105.0, snakeBodyPart.getCenterX(), 0.0);
		assertEquals(246.0, snakeBodyPart.getCenterY(), 0.0);
	}

	@Test
	public void getCenterOnFloatingResults() {
		CellData cellData = new CellData(10, 20, 11, 13);
		SnakeBodyPart snakeBodyPart = new SnakeBodyPart(cellData);
		assertEquals(115.5, snakeBodyPart.getCenterX(), 0.0);
		assertEquals(266.5, snakeBodyPart.getCenterY(), 0.0);
	}

	@Test
	public void getCenterOnNegativePositionsFloatingResults() {
		CellData cellData = new CellData(-10, -20, 11, 13);
		SnakeBodyPart snakeBodyPart = new SnakeBodyPart(cellData);
		assertEquals(-104.5, snakeBodyPart.getCenterX(), 0.0);
		assertEquals(-253.5, snakeBodyPart.getCenterY(), 0.0);
	}

	@Test
	public void getCenterOnNegativeDimensionFloatingResults() {
		CellData cellData = new CellData(10, 20, -11, -13);
		SnakeBodyPart snakeBodyPart = new SnakeBodyPart(cellData);
		assertEquals(-115.5, snakeBodyPart.getCenterX(), 0.0);
		assertEquals(-266.5, snakeBodyPart.getCenterY(), 0.0);
	}

}
