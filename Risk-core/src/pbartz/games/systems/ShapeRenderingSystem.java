package pbartz.games.systems;

import pbartz.games.components.ColorComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.TextureComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ShapeRenderingSystem extends IteratingSystem {
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<ColorComponent> cm = ComponentMapper.getFor(ColorComponent.class);
	private ComponentMapper<ShapeComponent> sm = ComponentMapper.getFor(ShapeComponent.class);
	private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	
	private PositionComponent position;
	private ColorComponent color;
	private ShapeComponent shape;
	private TextureComponent texture;
	
	private ShapeRenderer shapes;
	
	@SuppressWarnings("unchecked")
	public ShapeRenderingSystem() {
		super(Family.getFor(ShapeComponent.class, PositionComponent.class, ColorComponent.class));
		shapes = new ShapeRenderer();
	}

	public void startRendering() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapes.begin(ShapeType.Filled);
	}
	
	public void endRendering() {
		shapes.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		position = pm.get(entity);
		color = cm.get(entity);
		shape = sm.get(entity);		
		texture = tm.get(entity);
		
		shapes.setColor(color.getColor());

		if (shape.isCircle()) {
			
			shapes.circle(position.x, position.y, shape.getcRadius());
			
		} else if (shape.isRectangle()) {
			
			if (texture == null) { 
				
				shapes.rect(position.x - shape.getrWidth() / 2, position.y - shape.getrHeight() / 2, shape.getrWidth(), shape.getrHeight());
				
			}
			
		}
		

	}

}
