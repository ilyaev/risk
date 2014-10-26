package pbartz.games.risk.screens;

import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.GameInputProcessor;
import pbartz.games.risk.MapGenerator;
import pbartz.games.risk.ResourceFactory;
import pbartz.games.risk.RiskGame;
import pbartz.games.systems.ArrowRenderingSystem;
import pbartz.games.systems.BlinkSystem;
import pbartz.games.systems.ColorAlphaInterpolationSystem;
import pbartz.games.systems.ColorInterpolationSystem;
import pbartz.games.systems.CommandExecutionSystem;
import pbartz.games.systems.DelayedComponentSystem;
import pbartz.games.systems.GameFlowSystem;
import pbartz.games.systems.PositionInterpolationSystem;
import pbartz.games.systems.ShapeRenderingSystem;
import pbartz.games.systems.TextureRenderingSystem;
import pbartz.games.systems.UIButtonSystem;
import pbartz.games.systems.UIImageSystem;
import pbartz.games.systems.UILabelSystem;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.systems.ZoneTouchSystem;
import pbartz.games.utils.Metrics;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainScreen implements Screen {
	
	 private Skin skin;
	 private Stage stage;
	
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
		
		engine.getSystem(TextureRenderingSystem.class).startRendering();
		
		stage.draw();
		
        engine.getSystem(TextureRenderingSystem.class).endRendering();	
		
		
		//game.calcFPS();
	}
	
	private void initUI() {
		
		int panelHeight = 100;
		int eturnBtnWidth = 150;
		int labelX = 100;
		int labelY = 37;
		
		Table container = new Table();
		container.setHeight(panelHeight);
		container.setWidth(Gdx.graphics.getWidth());
		container.setPosition(0, 0);
		
		stage.addActor(container);

		
		Table table = new Table();

		final ScrollPane scroll = new ScrollPane(table, skin);
		
		container.add(scroll).expand().fill();
		
		EntityFactory.createUILabel("Your Turn", labelX, labelY, 1, 1, 1, 1, "LABEL_TURN_INFO");
		
		EntityFactory.createUIButton(
				"End Turn",
				Gdx.graphics.getWidth() - eturnBtnWidth - 25, 
				25, 
				eturnBtnWidth, 
				50, 
				"btnEndTurn"
		);
		
		EntityFactory.UISwitchPlayer(0, 1);
		
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
		
		engine.getSystem(GameFlowSystem.class).reset();
		
		EntityFactory.init(engine);
		EntityFactory.setStage(stage);
        EntityFactory.setSkin(skin);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GameInputProcessor());
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		Gdx.app.log("METRICS", String.format("widthDp: %f, heightDp: %f, Density: %f", Metrics.widthDp, Metrics.heightDp, Metrics.density));
		
	}

	private void initScreen() {
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        
        EntityFactory.setStage(stage);
        EntityFactory.setSkin(skin);
		
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
		engine.addSystem(new GameFlowSystem());
		engine.addSystem(new DelayedComponentSystem(engine));
		engine.addSystem(new UIButtonSystem());
		engine.addSystem(new UILabelSystem());
		engine.addSystem(new UIImageSystem());
		
		EntityFactory.init(engine);
		EntityFactory.initPallete();
		
		ResourceFactory.init();
		
		MapGenerator map = new MapGenerator(Metrics.cellsH, Metrics.cellsV, Metrics.cellSize);
		map.generate(engine);
		
		
		
		
		initUI();
		
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
