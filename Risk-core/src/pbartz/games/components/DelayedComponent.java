package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DelayedComponent extends Component implements Poolable {
	
	Entity entity;
	Component component;
	private float delay;
	
	public void init(Entity entity, Component component, float delay) {
		
		this.entity = entity;
		this.component = component;
		this.delay = delay;
		
	}
	
	public boolean isNextEvent(float delta) {
		
		delay -= delta;
		return delay <= 0;
		
	}
	
	
		
	public Entity getEntity() {
		return entity;
	}



	public void setEntity(Entity entity) {
		this.entity = entity;
	}



	public Component getComponent() {
		return component;
	}



	public void setComponent(Component component) {
		this.component = component;
	}



	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
