package snake.game.fruits;

import org.junit.Test;

import snake.game.data.CellData;

public class FruitImplTest {

	@Test
	public void constructorWithNulls() {
		new FruitImpl(null);
	}

	@Test
	public void constructor() {
		CellData cellData = new CellData(4, 1, 10, 10);
		new FruitImpl(cellData);
	}

	// TODO: Test draw(Graphics g) with mocked Graphics 

}
