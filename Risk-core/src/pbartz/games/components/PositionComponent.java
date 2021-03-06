package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PositionComponent extends Component implements Poolable {

	public float x, y;
	public float originalX, originalY;

	public void init(float x, float y) {
		this.x = x;
		this.y = y;
		
		originalX = x;
		originalY = y;
	}

	public void setPosition(float x2, float y2) {
		this.x = x2;
		this.y = y2;		
	}

	public float getOriginalX() {
		return originalX;
	}

	public float getOriginalY() {
		return originalY;
	}

	@Override
	public void reset() {
				
	}


}
