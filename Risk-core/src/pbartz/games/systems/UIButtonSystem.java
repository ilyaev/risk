package pbartz.games.systems;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.UI.ButtonComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectMap;

public class UIButtonSystem extends DynamicIteratingSystem {

	public ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	public ComponentMapper<ButtonComponent> bm = ComponentMapper.getFor(ButtonComponent.class);
	public ComponentMapper<ShapeComponent> sm = ComponentMapper.getFor(ShapeComponent.class);
	
	PositionComponent position;
	ButtonComponent button;
	ShapeComponent shape;
	
	private static ObjectMap<String, Entity> buttons = new ObjectMap<String, Entity>(); 
	
	@SuppressWarnings("unchecked")
	public UIButtonSystem() {
		super(Family.getFor(ButtonComponent.class, PositionComponent.class, ShapeComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		button = bm.get(entity);
		position = pm.get(entity);
		shape = sm.get(entity);
		
		button.getButton().setSize(shape.getrWidth(), shape.getrHeight());

		button.getButton().setPosition(position.x, position.y);
		
	}
	
	public void removeButton(String tag) {
		Entity entity = getButton(tag);
		
		if (entity != null) {
			
			bm.get(entity).getButton().remove();
			
		}
	}
	
	public void addButton(Entity entity, String tag) {
		
		buttons.put(tag, entity);
		
	}
	
	public Entity getButton(String tag) {
		
		return buttons.get(tag, null);
		
	}

}
