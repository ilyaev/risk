package pbartz.games.systems;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.PositionInterpolationComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.utils.Interpolation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class PositionInterpolationSystem extends DynamicIteratingSystem {
	
	PositionComponent position;
	PositionInterpolationComponent interpolation;
	PooledEngine engine;
	
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<PositionInterpolationComponent> im = ComponentMapper.getFor(PositionInterpolationComponent.class);

	@SuppressWarnings("unchecked")
	public PositionInterpolationSystem(PooledEngine engine) {
		super(Family.getFor(PositionComponent.class, PositionInterpolationComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		position = pm.get(entity);
		interpolation = im.get(entity);
		
		interpolation.increaseTime(deltaTime);

		position.x = interpolation.getCurrentX();
		position.y = interpolation.getCurrentY();
		
		if (interpolation.isCompleted()) {

			position.x = interpolation.destX;
			position.y = interpolation.destY;
			
//			entity.add(ComponentFactory.getPositionInterpolationComponent(
//					
//					engine, position, MathUtils.random(480), MathUtils.random(800), MathUtils.random() * 3f, Interpolation.EASE_IN
//					
//			));
			
			entity.remove(PositionInterpolationComponent.class);
			
		}
		
	}

}
