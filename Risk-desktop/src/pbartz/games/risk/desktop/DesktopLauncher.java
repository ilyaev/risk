package pbartz.games.risk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pbartz.games.risk.RiskGame;
import pbartz.games.utils.Metrics;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new RiskGame(), config);
		
		
		initMetrics();
	}
	
	private static void initMetrics() {

		float widthPx = 480;
		float heightPx = 800;
		
		Metrics.widthDp = 320;
		Metrics.heightDp = 533.3f;
		Metrics.widthPx = widthPx;
		Metrics.heightPx = heightPx;
		Metrics.density = 1.5f;	
		
		Metrics.initHexMetrics();
	}
}
