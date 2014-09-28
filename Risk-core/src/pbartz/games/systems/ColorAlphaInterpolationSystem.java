package pbartz.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.ColorComponent;
import pbartz.games.components.ColorInterpolationComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.utils.Interpolation;

public class ColorAlphaInterpolationSystem extends DynamicIteratingSystem {

	ColorAlphaComponent color;
	ColorInterpolationComponent interpolation;
	
	private ComponentMapper<ColorAlphaComponent> cm = ComponentMapper.getFor(ColorAlphaComponent.class);
	private ComponentMapper<ColorInterpolationComponent> im = ComponentMapper.getFor(ColorInterpolationComponent.class);
	private PooledEngine engine;
	
	@SuppressWarnings("unchecked")
	public ColorAlphaInterpolationSystem(PooledEngine engine) {
		super(Family.getFor(ColorAlphaComponent.class, ColorInterpolationComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		color = cm.get(entity);
		interpolation = im.get(entity);
		
		interpolation.increaseTime(deltaTime);
		
		color.setAlpha(interpolation.getCurrentAlpha());
		
		if (interpolation.isCompleted()) {
			
			color.setAlpha(interpolation.getFinalAlpha());
		}
		
	}

}