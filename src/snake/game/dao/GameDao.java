package snake.game.dao;

import java.util.LinkedList;

import snake.game.data.PlayerData;

public interface GameDao {

	boolean isReady();

	int getHighScore();

	boolean savePlayerData(PlayerData player);

	LinkedList<PlayerData> getTopPlayers();
}
