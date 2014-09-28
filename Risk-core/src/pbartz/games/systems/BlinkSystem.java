package pbartz.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;

import pbartz.games.components.BlinkComponent;
import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.utils.Interpolation;

public class BlinkSystem extends DynamicIteratingSystem {
	
	public ComponentMapper<BlinkComponent> bm = ComponentMapper.getFor(BlinkComponent.class);
	private ComponentMapper<ColorAlphaComponent> am = ComponentMapper.getFor(ColorAlphaComponent.class);
	
	BlinkComponent blink;
	ColorAlphaComponent alpha;
	
	PooledEngine engine;

	@SuppressWarnings("unchecked")
	public BlinkSystem(PooledEngine engine) {
		super(Family.getFor(BlinkComponent.class, ColorAlphaComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		blink = bm.get(entity);
		alpha = am.get(entity);
		
		if (blink.isNextEvent(deltaTime)) {
			
			if (blink.getState() == BlinkComponent.BLINK_NOT_INITED) {
				
				blink.setState(BlinkComponent.BLINK_OUT);
				
			}
			
			if (blink.getState() == BlinkComponent.BLINK_OUT) {
				
				entity.add(ComponentFactory.getColorAlphaInterpolationComponent(engine, alpha.getAlpha(), 0, blink.getBlinkSpeed(), Interpolation.EASE_OUT));
				
				blink.setState(BlinkComponent.BLINK_IN);
				
			} else if (blink.getState() == BlinkComponent.BLINK_IN) {

				entity.add(ComponentFactory.getColorAlphaInterpolationComponent(engine, 0, 1f, blink.getBlinkSpeed(), Interpolation.EASE_OUT));
				
				blink.setState(BlinkComponent.BLINK_OUT);
				
			}
			
			
		}

	}

}
