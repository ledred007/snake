package snake.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

import org.junit.Test;

import snake.game.data.CellData;
import snake.game.data.PanelData;
import snake.game.fruits.Apple;

public class SnakeBodyTest {
	private final ZoneId zone = ZoneId.systemDefault();

	class MockRandom extends Random {
		private static final long serialVersionUID = 9007L;

		public int nextInt(int bound) {
			int result = 0;
			switch (bound) {
			case 5:
				result = 2;
				break;
			case 6:
				result = 1;
				break;
			}
			return result;
		}

	}

	Random random = new MockRandom();

	@Test(expected = NullPointerException.class)
	public void constructorWithNull() {
		new SnakeBody(null, new Random());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void constructorWithZerosPanel() {
		PanelData panelData = new PanelData(0, 0, 0);
		new SnakeBody(panelData, new Random());
	}

	@Test
	public void constructorWithValidPanelCheckData() {
		PanelData panelData = new PanelData(10, 12, 10);
		SnakeBody snakeBody = new SnakeBody(panelData, random);
		assertTrue(ZonedDateTime.now(zone).isBefore(snakeBody.getNextMoveTime()));
		assertEquals(1, snakeBody.getSize());
		assertEquals(-1, snakeBody.getPositionForCell(0, 0));
		assertEquals(-1, snakeBody.getPositionForCell(0, 1));
		assertEquals(-1, snakeBody.getPositionForCell(0, 2));
		assertEquals(-1, snakeBody.getPositionForCell(0, 3));
		assertEquals(-1, snakeBody.getPositionForCell(1, 0));
		assertEquals(-1, snakeBody.getPositionForCell(1, 1));
		assertEquals(-1, snakeBody.getPositionForCell(1, 2));
		assertEquals(-1, snakeBody.getPositionForCell(1, 3));
		assertEquals(-1, snakeBody.getPositionForCell(2, 0));
		assertEquals(0, snakeBody.getPositionForCell(2, 1));
		assertEquals(-1, snakeBody.getPositionForCell(2, 2));
		assertEquals(-1, snakeBody.getPositionForCell(2, 3));
		assertEquals(-1, snakeBody.getPositionForCell(3, 0));
		assertEquals(-1, snakeBody.getPositionForCell(3, 1));
		assertEquals(-1, snakeBody.getPositionForCell(3, 2));
		assertEquals(-1, snakeBody.getPositionForCell(3, 3));
	}

	@Test
	public void moveAndEat() {
		PanelData panelData = new PanelData(10, 12, 10);
		SnakeBody snakeBody = new SnakeBody(panelData, random);
		assertEquals(1, snakeBody.getSize());
		CellData cellData = new CellData(4, 1, 10, 10);
		Apple apple = new Apple(cellData);
		assertEquals(false, snakeBody.isHeadOnFruit(apple));
		snakeBody.keyEventProcess(KeyEvent.VK_RIGHT);
		assertEquals(true, snakeBody.move());
		snakeBody.crawl();
		assertEquals(false, snakeBody.isHeadOnFruit(apple));
		assertEquals(true, snakeBody.move());
		snakeBody.crawl();
		assertEquals(true, snakeBody.isHeadOnFruit(apple));
		snakeBody.eatFruit();
		assertEquals(2, snakeBody.getSize());
	}

	@Test
	public void moveOutOfBoundary() {
		PanelData panelData = new PanelData(10, 12, 10);
		SnakeBody snakeBody = new SnakeBody(panelData, random);
		CellData cellData = new CellData(4, 1, 10, 10);
		Apple apple = new Apple(cellData);
		assertEquals(false, snakeBody.isHeadOnFruit(apple));
		snakeBody.keyEventProcess(KeyEvent.VK_UP);
		assertEquals(true, snakeBody.move());
		snakeBody.crawl();
		assertEquals(false, snakeBody.move());
	}

}
