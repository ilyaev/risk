package pbartz.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import pbartz.games.components.ColorComponent;
import pbartz.games.components.ColorInterpolationComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.utils.Interpolation;

public class ColorInterpolationSystem extends DynamicIteratingSystem {

	ColorComponent color;
	ColorInterpolationComponent interpolation;
	
	private ComponentMapper<ColorComponent> cm = ComponentMapper.getFor(ColorComponent.class);
	public ComponentMapper<ColorInterpolationComponent> im = ComponentMapper.getFor(ColorInterpolationComponent.class);
	private PooledEngine engine;
	
	@SuppressWarnings("unchecked")
	public ColorInterpolationSystem(PooledEngine engine) {
		super(Family.getFor(ColorComponent.class, ColorInterpolationComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		color = cm.get(entity);
		interpolation = im.get(entity);
		
		interpolation.increaseTime(deltaTime);
		
		color.setColor(interpolation.getCurrentColor());
		
		if (interpolation.isCompleted()) {
			
			Color newColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), MathUtils.random());
			entity.add(ComponentFactory.getColorInterpolationComponent(engine, color.getColor(), newColor, MathUtils.random() * 2f, Interpolation.EASE_OUT));
			
			//entity.remove(ColorInterpolationComponent.class);
		}
		
	}

}