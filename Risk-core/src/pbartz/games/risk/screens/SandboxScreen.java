package pbartz.games.risk.screens;

import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.RiskGame;
import pbartz.games.systems.ArrowRenderingSystem;
import pbartz.games.systems.BlinkSystem;
import pbartz.games.systems.ColorAlphaInterpolationSystem;
import pbartz.games.systems.ColorInterpolationSystem;
import pbartz.games.systems.CommandExecutionSystem;
import pbartz.games.systems.PositionInterpolationSystem;
import pbartz.games.systems.ShapeRenderingSystem;
import pbartz.games.systems.TextureRenderingSystem;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.systems.ZoneTouchSystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class SandboxScreen implements Screen {
	
	RiskGame game;
	private PooledEngine engine = null;
	
	public SandboxScreen(RiskGame game) {
		this.game = game;
	}
	
	
	private void createTest() {
		
		Texture texture = new Texture(Gdx.files.internal("dice.png"));
		
		for(int j = 0 ; j < 31 ; j++) {
			
			int x = MathUtils.random(480);
			int y = MathUtils.random(800);
		
			int step = 0;
			
			int len = MathUtils.random(8) + 1;
			
			for(int i = 0 ; i < len ; i++) {
				
				if (i == 4) {
					x += 12;
					y -= 10;
					step = 0;
				}
			
				EntityFactory.createTextureEntity(texture, x, y + (step * 12));
				
				step += 1;
				
			}
			
		}
		
		
	}
	
	@Override
	public void show() {
		if (engine == null) {
			initScreen();
		}
		
		EntityFactory.init(engine);
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
		
		EntityFactory.init(engine);

		createTest();
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
