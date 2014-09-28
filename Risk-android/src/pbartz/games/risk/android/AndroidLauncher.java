package pbartz.games.risk.android;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import pbartz.games.risk.RiskGame;
import pbartz.games.utils.Metrics;

public class AndroidLauncher extends AndroidApplication {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		initMetrics();
		
		initialize(new RiskGame(), config);
	}

	private void initMetrics() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		float widthDp = displayMetrics.widthPixels / displayMetrics.density;
		float heightDp = displayMetrics.heightPixels / displayMetrics.density;
		float widthPx = displayMetrics.widthPixels;
		float heightPx = displayMetrics.heightPixels;
		
		Metrics.widthDp = widthDp;
		Metrics.heightDp = heightDp;
		Metrics.widthPx = widthPx;
		Metrics.heightPx = heightPx;
		Metrics.density = displayMetrics.density;		
		
		Metrics.initHexMetrics();
	}

}
