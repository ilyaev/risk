package pbartz.games.risk.screens;

import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.GameInputProcessor;
import pbartz.games.risk.MapGenerator;
import pbartz.games.risk.RiskGame;
import pbartz.games.systems.ArrowRenderingSystem;
import pbartz.games.systems.BlinkSystem;
import pbartz.games.systems.ColorAlphaInterpolationSystem;
import pbartz.games.systems.ColorInterpolationSystem;
import pbartz.games.systems.CommandExecutionSystem;
import pbartz.games.systems.DelayedComponentSystem;
import pbartz.games.systems.PositionInterpolationSystem;
import pbartz.games.systems.ShapeRenderingSystem;
import pbartz.games.systems.TextureRenderingSystem;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.systems.ZoneTouchSystem;
import pbartz.games.utils.Metrics;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MainScreen implements Screen {
	
	RiskGame game;
	private PooledEngine engine = null;
	
	public MainScreen(RiskGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		engine.getSystem(ShapeRenderingSystem.class).startRendering();
		engine.getSystem(TextureRenderingSystem.class).startRendering();
		
		engine.update(Gdx.graphics.getRawDeltaTime());
		
		engine.getSystem(ShapeRenderingSystem.class).endRendering();
		engine.getSystem(TextureRenderingSystem.class).endRendering();		
		
		game.calcFPS();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		if (engine == null) {
			initScreen();
		}
		
		EntityFactory.init(engine);
		Gdx.input.setInputProcessor(new GameInputProcessor());
		
	}

	private void initScreen() {
		
		engine  = new PooledEngine();
		
		engine.addSystem(new ShapeRenderingSystem());
		engine.addSystem(new PositionInterpolationSystem(engine));
		engine.addSystem(new ColorInterpolationSystem(engine));
		engine.addSystem(new ColorAlphaInterpolationSystem(engine));
		engine.addSystem(new TextureRenderingSystem());
		engine.addSystem(new BlinkSystem(engine));
		engine.addSystem(new ZoneTouchSystem(engine));
		engine.addSystem(new ArrowRenderingSystem(engine));
		engine.addSystem(new ZoneSelectionSystem());
		engine.addSystem(new CommandExecutionSystem(engine));
		engine.addSystem(new DelayedComponentSystem(engine));
		
		EntityFactory.init(engine);
		
		MapGenerator map = new MapGenerator(Metrics.cellsH, Metrics.cellsV, Metrics.cellSize);
		map.generate(engine);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
