package pbartz.games.systems;

import pbartz.games.components.ArrowComponent;
import pbartz.games.components.TouchComponent;
import pbartz.games.utils.Metrics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class ArrowRenderingSystem extends IteratingSystem {

	PooledEngine engine;
	private ComponentMapper<ArrowComponent> am = ComponentMapper.getFor(ArrowComponent.class);
	private ArrowComponent arrow;
	private Matrix4 clearMx = new Matrix4();
	private Matrix4 mx = new Matrix4();
	Vector3 rotationVector = new Vector3(0, 0, 1);
	
	float deltaX, deltaY, scaleX, scaleY, angleInDegrees;
	
	@SuppressWarnings("unchecked")
	public ArrowRenderingSystem(PooledEngine engine) {
		super(Family.getFor(ArrowComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		arrow = am.get(entity);
		
		SpriteBatch batch = engine.getSystem(TextureRenderingSystem.class).getBatch();		
		
		
		deltaY = arrow.getStartY() - arrow.getEndY();
		deltaX = arrow.getStartX() - arrow.getEndX();
		
		angleInDegrees = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
		
		scaleY = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		scaleX = Metrics.widthPx / 24;
		
		mx.set(clearMx);
		
		mx.translate(arrow.getStartX(), arrow.getStartY(), 0);		
		mx.rotate(rotationVector, angleInDegrees + 90);
		
		mx.scale(scaleX, scaleY, 0);
		
		
		batch.setTransformMatrix(mx);

		arrow = am.get(entity);
		
		batch.draw(arrow.getTexture(), 0, 0);
		
		batch.setTransformMatrix(clearMx);
	}

}
