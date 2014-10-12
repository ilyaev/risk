package pbartz.games.systems;

import pbartz.games.components.ColorComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.UI.LabelComponent;
import pbartz.games.risk.EntityFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectMap;

public class UILabelSystem extends DynamicIteratingSystem {
	
	public ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	public ComponentMapper<LabelComponent> lm = ComponentMapper.getFor(LabelComponent.class);
	public ComponentMapper<ColorComponent> cm = ComponentMapper.getFor(ColorComponent.class);
	public ComponentMapper<ShapeComponent> sm = ComponentMapper.getFor(ShapeComponent.class);
	
	PositionComponent position;
	ColorComponent color;
	LabelComponent label;
	ShapeComponent shape;
	
	private static ObjectMap<String, Entity> labels = new ObjectMap<String, Entity>(); 

	@SuppressWarnings("unchecked")
	public UILabelSystem() {
		super(Family.getFor(LabelComponent.class, PositionComponent.class, ColorComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		position = pm.get(entity);
		label = lm.get(entity);
		color = cm.get(entity);
		shape = sm.get(entity);
		
		label.getLabel().setColor(color.getColor());
		label.getLabel().setPosition(position.x, position.y);
		
		if (shape != null) {
		
			label.getLabel().setSize(shape.getrWidth(), shape.getrHeight());
			
		}


	}
	
	public void removeLabel(String tag) {
		Entity entity = getLabel(tag);
		
		if (tag != null) {
			
			lm.get(entity).getLabel().remove();
			
		}
	}

	public void addLabel(Entity entity, String tag) {
		
		labels.put(tag, entity);
		
	}
	
	public Entity getLabel(String tag) {
		
		return labels.get(tag, null);
		
	}

}
