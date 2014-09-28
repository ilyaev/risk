package pbartz.games.systems;

import pbartz.games.components.DelayedComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;

public class DelayedComponentSystem extends DynamicIteratingSystem {
	
	public ComponentMapper<DelayedComponent> dm = ComponentMapper.getFor(DelayedComponent.class);
	DelayedComponent delayed;
	private PooledEngine engine;

	@SuppressWarnings("unchecked")
	public DelayedComponentSystem(PooledEngine engine) {
		super(Family.getFor(DelayedComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		delayed = dm.get(entity);
		
		if (delayed.isNextEvent(deltaTime)) {
			
			if (delayed.getComponent() == null) {
							
				engine.addEntity(delayed.getEntity());
				
			} else {
		
				delayed.getEntity().add(delayed.getComponent());
				
			}
			
			engine.removeEntity(entity);
			
		}

	}

}