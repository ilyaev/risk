package pbartz.games.systems;

import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.TextureComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureRenderingSystem extends IteratingSystem {
	
	public ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<ShapeComponent> sm = ComponentMapper.getFor(ShapeComponent.class);
	public ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	public ComponentMapper<ColorAlphaComponent> am = ComponentMapper.getFor(ColorAlphaComponent.class);
	
	private PositionComponent position;
	private ShapeComponent shape;
	private TextureComponent texture;
	private ColorAlphaComponent alpha;
	
	private SpriteBatch batch;

	@SuppressWarnings("unchecked")
	public TextureRenderingSystem() {
		super(Family.getFor(PositionComponent.class, ShapeComponent.class, TextureComponent.class));
		batch = new SpriteBatch();
	}
	
	public void startRendering() {
		batch.begin();
	}
	
	public void endRendering() {
		batch.end();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		position = pm.get(entity);
		shape = sm.get(entity);		
		texture = tm.get(entity);
		alpha = am.get(entity);
		
		if (alpha != null) {
			batch.setColor(1f, 1f, 1f, alpha.getAlpha());
		}
		
		if (!texture.isFlipped()) {
		
			batch.draw(texture.getTexture(), position.x, position.y);
			
		} else {

			batch.draw(
				texture.getTexture(), 
				position.x - shape.getrWidth() / 2, 
				position.y - shape.getrHeight() / 2,
				shape.getrWidth(),
				shape.getrHeight(),
				0,
				0,
				(int)shape.getrWidth(),
				(int)shape.getrHeight(),
				false,
				true
			);
			
		}
		
	}

}
