package snake.game.fruits;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

import org.apache.log4j.Logger;

import snake.game.configuration.GameParameters;
import snake.game.data.CellData;
import snake.game.data.PanelData;
import snake.game.snake.SnakeBody;

public class FruitController {
	final static Logger logger = Logger.getLogger(FruitController.class);

	private static int width;
	private static int height;
	private static int cellSize;
	private static int fruitTimeSeconds;
	private static int fruitTimeBound;
	private static ZoneId zone = ZoneId.systemDefault();
	private static ZonedDateTime lastFruitTime;
	private static Random random;

	public static void initController(PanelData panelData) {
		FruitController.width = panelData.getWidth();
		FruitController.height = panelData.getHeight();
		FruitController.cellSize = panelData.getCellSize();
		FruitController.random = new Random();
		FruitController.fruitTimeBound = GameParameters.FRUIT_TIME_SECONDS_BOUND;

		resetNextCreateTime();
	}

	public static ZonedDateTime getNextCreateTime() {
		return lastFruitTime.plusSeconds(fruitTimeSeconds);
	}

	public static void resetNextCreateTime() {
		fruitTimeSeconds = random.nextInt(fruitTimeBound);
		lastFruitTime = ZonedDateTime.now(zone);
	}

	public static Fruit createFruit(SnakeBody snakeBody) {
		Fruit fruit;
		int fruitX;
		int fruitY;

		do {
			fruitX = random.nextInt(width);
			fruitY = random.nextInt(height);
		} while (0 <= snakeBody.getPositionForCell(fruitX, fruitY));
		CellData cellData = new CellData(fruitX, fruitY, cellSize, cellSize);
		fruit = 0 == random.nextInt(2) ? new Apple(cellData) : new Banana(cellData);
		logger.debug("FruitController.createFruit" + fruitX + " : " + fruitY + " -> " + fruit.getClass().getName());
		return fruit;
	}

}
