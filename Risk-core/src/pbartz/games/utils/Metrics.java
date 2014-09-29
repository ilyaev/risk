package pbartz.games.utils;

import com.badlogic.gdx.Gdx;

public class Metrics {
	
	public static float widthDp;
	public static float heightDp;
	public static float widthPx;
	public static float heightPx;
	public static float density;
	public static int cellSize;
	public static int cellsH;
	public static int cellsV;
	
	public static int dp2px(float dp) {
		return (int) (dp * density + 0.5f);
	}

	public static void initHexMetrics() {
		
		cellSize = Metrics.dp2px(Metrics.widthDp / 50);
		cellsH = (int) ((Metrics.widthPx / cellSize) / 1.7);
		cellsV = (int) ((Metrics.heightPx / cellSize) / 1.5);
		
		if (cellsV % 2 != 0) {
			cellsV -= 1;
		}
		
		if (cellsH % 2 != 0) {
			cellsH -= 1;
		}		
	}
	
	
}