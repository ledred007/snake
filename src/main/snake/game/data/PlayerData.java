package snake.game.data;

public class PlayerData {

	private String name;
	private int score;

	public PlayerData(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

}
