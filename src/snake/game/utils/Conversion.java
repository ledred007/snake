package snake.game.utils;

public final class Conversion {

	public static int getPositiveInteger(String strInteger) {
		int ret = -1;
		if (strInteger != null) {
			try {
				ret = Integer.parseInt(strInteger);
			} catch (NumberFormatException nfe) {
			}
		}
		return ret;
	}
}
