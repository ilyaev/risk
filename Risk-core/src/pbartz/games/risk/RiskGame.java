package pbartz.games.risk;

import pbartz.games.risk.screens.MainScreen;
import pbartz.games.risk.screens.SandboxScreen;
import pbartz.games.risk.screens.UITestScreen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class RiskGame extends Game {
	Texture img; 
	
	PooledEngine engine;
	MainScreen mainScreen;
	SandboxScreen sandboxScreen;
	UITestScreen uitestScreen;
	
	long lastTime;
	int frames;
	
	@Override
	public void create () {
		lastTime = TimeUtils.millis();		
		frames = 0;		
		
		mainScreen = new MainScreen(this);
		sandboxScreen = new SandboxScreen(this);
		//uitestScreen = new UITestScreen(this);
		
		
		setScreen(mainScreen);
		//setScreen(sandboxScreen);
		//setScreen(uitestScreen);
		
	}

	public void calcFPS() {
		if ((TimeUtils.millis() - lastTime) >= 1000 ) {
			lastTime = TimeUtils.millis();
			Gdx.app.log("APP", "FPS:" + Integer.toString(frames));
			frames = 0;
		} else {
			
			frames += 1;
		}
	}
	
}
