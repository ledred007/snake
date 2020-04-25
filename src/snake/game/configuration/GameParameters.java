package snake.game.configuration;

import java.awt.Color;

public final class GameParameters {

	public static final int CELL_SIZE = 10;
	public static final int PANEL_MIN_WIDTH = 10;
	public static final int PANEL_MIN_HEIGHT = 10;
	public static final int WIDTH_GAP = 30;
	public static final int HEIGHT_GAP = 180;
	public static final boolean GAME_PANEL_SHOW_GRID = false;
	public static final int PLAYER_LIFE_MAX = 5;
	public static final int TOP_PLAYERS = 10;
	public static final Color GAME_PANEL_BORDER_COLOR = Color.RED;
	public static final Color GAME_PANEL_BACKGROUND_COLOR = Color.BLACK;
	public static final Color GAME_PANEL_GRID_COLOR = Color.WHITE;
	public static final Color SNAKE_COLOR = Color.GREEN;
	public static final Color FRUIT_COLOR = Color.GRAY;
	public static final Color APPLE_COLOR = Color.RED;
	public static final Color BANANA_COLOR = Color.YELLOW;
	public static final long SNEAK_MOVE_TIME_IN_NANOS = 1000000000l;
	public static final long THREAD_SLEEP_IN_MILISECONDS = 200l;
	public static final int FRUIT_TIME_SECONDS_BOUND = 6;
	public static final String DATABASE_NAME = "db_snake";

}
