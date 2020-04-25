package snake.game.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import snake.game.configuration.GameParameters;
import snake.game.data.PlayerData;

public class DerbySqlGameDao implements GameDao {
	// TODO: change to debug logs
	final static Logger logger = Logger.getLogger(DerbySqlGameDao.class);

	static final String DB_URL = "jdbc:derby:" + GameParameters.DATABASE_NAME + ";create=true";
	private static final String USERS_TABLE = "Users";
	private static final String CHECK_TABLE_QUERY = "SELECT COUNT(*) AS total FROM " + USERS_TABLE;
	private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + USERS_TABLE + " ("
			+ "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
			+ "name VARCHAR(255) NOT NULL DEFAULT '', "
			+ "score INT NOT NULL DEFAULT 0, "
			+ "date VARCHAR(255) NOT NULL DEFAULT '')";
	private static final String SELECT_HIGHSCORE_QUERY = "SELECT MAX(score) AS highscore FROM " + USERS_TABLE;
	private static final String SELECT_USER_QUERY = "SELECT id, score FROM " + USERS_TABLE + " WHERE name=?";
	private static final String INSERT_USER_QUERY = "INSERT INTO " + USERS_TABLE + "(name, score, date) VALUES(?,?,?)";
	private static final String UPDATE_USER_QUERY = "UPDATE " + USERS_TABLE + " SET score=? WHERE id=?";
	private static final String SELECT_TOP_USERS_QUERY = "SELECT name, score FROM " + USERS_TABLE
			+ " ORDER BY score DESC, name ASC";
	private static final ZoneId zone = ZoneId.systemDefault();

	private Connection conn;
	private boolean ready;

	public DerbySqlGameDao() {
		try {
			conn = DriverManager.getConnection(DB_URL);
			ready = isUsersTableExists();
			if (!ready) {
				ready = createUsersTable();
			}
		} catch (SQLException e) {
			logger.error("Database Open failed -> ", e);
		}
	}

	public boolean isReady() {
		return ready;
	}

	public int getHighScore() {
		int highScore = 0;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(SELECT_HIGHSCORE_QUERY);
			if (rs != null) {
				if (rs.next()) {
					highScore = rs.getInt("highscore");
					logger.info("Derby DB Users HighScore -> " + highScore);
				}
				rs.close();
			}
		} catch (SQLException e) {
			logger.error("Derby DB Users Users HighScore Error -> ", e);
		}
		return highScore;
	}

	public boolean savePlayerData(PlayerData player) {
		boolean result = false;
		Entry<Integer, Integer> entry = selectUserData(player);
		if (entry.getKey() == 0) {
			result = insertUserData(player);
		} else if (entry.getValue() < player.getScore()) {
			result = updateUserData(entry.getKey(), player.getScore());
		}
		return result;
	}

	public LinkedList<PlayerData> getTopPlayers() {
		LinkedList<PlayerData> players = new LinkedList<PlayerData>();
		try (Statement stmt = conn.createStatement()) {
			stmt.setMaxRows(GameParameters.TOP_PLAYERS);
			ResultSet rs = stmt.executeQuery(SELECT_TOP_USERS_QUERY);
			int counter = 0;
			while (counter++ < GameParameters.TOP_PLAYERS && rs != null && rs.next()) {
				players.add(new PlayerData(rs.getString("name"), rs.getInt("score")));
			}
			rs.close();
		} catch (SQLException e) {
			logger.error("Derby DB Users Users HighScore Error -> ", e);
		}

		return players;
	}

	private boolean isUsersTableExists() {
		boolean result = false;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(CHECK_TABLE_QUERY);
			if (rs != null && rs.next()) {
				int counter = rs.getInt("total");
				logger.info("Derby DB Users table counter -> " + counter);
				result = 0 <= counter;
				rs.close();
			}
		} catch (SQLException e) {
			logger.debug("Derby DB Users table counter error -> ", e);
			logger.info("Derby DB table didn't exist! - Table creation required!");
		}
		return result;
	}

	private boolean createUsersTable() {
		boolean result = false;
		try (Statement stmt = conn.createStatement()) {
			logger.debug("Derby DB Users table create SQL -> " + CREATE_TABLE_QUERY);
			stmt.execute(CREATE_TABLE_QUERY);
			conn.commit();
			result = true;
			logger.info("Derby DB Users table created!");
		} catch (SQLException e) {
			logger.error("Derby DB table create failed: ", e);
		}
		return result;
	}

	private Entry<Integer, Integer> selectUserData(PlayerData player) {
		int id = 0;
		int score = 0;
		try (PreparedStatement ps = conn.prepareStatement(SELECT_USER_QUERY)) {
			ps.setString(1, player.getName());
			logger.info("savePlayerData.selectUserData -> " + player.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs != null && rs.next()) {
					id = rs.getInt("id");
					score = rs.getInt("score");
					logger.info("savePlayerData.executeQuery -> " + id + " : " + score);
				}
			} catch (SQLException e) {
				logger.error("Derby DB Users table select error -> ", e);
			}
		} catch (SQLException e) {
			logger.info("Derby DB Users table select prepare error -> ", e);
		}
		return new SimpleImmutableEntry<Integer, Integer>(id, score);
	}

	private boolean insertUserData(PlayerData player) {
		boolean result = false;
		try (PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY)) {
			ps.setString(1, player.getName());
			ps.setInt(2, player.getScore());
			ps.setString(3, ZonedDateTime.now(zone).toString());
			logger.debug("insertUserData.insert -> " + player.getName() + " : " + player.getScore());
			try {
				boolean execresult = ps.execute();
				logger.info("insertUserData.execresult -> " + execresult);
				result = true;
				conn.commit();
			} catch (SQLException e) {
				logger.error("Derby DB Users table insert error -> ", e);
			}
		} catch (SQLException e) {
			logger.error("Derby DB Users table insert prepare error -> ", e);
		}
		return result;
	}

	private boolean updateUserData(int id, int score) {
		boolean result = false;
		try (PreparedStatement ps = conn.prepareStatement(UPDATE_USER_QUERY)) {
			ps.setInt(1, score);
			ps.setInt(2, id);
			logger.info("updateUserData.update -> " + id + " : " + score);
			try {
				boolean execresult = ps.execute();
				logger.info("updateUserData.execresult -> " + execresult);
				result = true;
				conn.commit();
			} catch (SQLException e) {
				logger.error("Derby DB Users table update error -> ", e);
			}
		} catch (SQLException e) {
			logger.error("Derby DB Users table insert update prepare error -> ", e);
		}
		return result;
	}

}
