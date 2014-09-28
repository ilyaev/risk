package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ColorAlphaComponent extends Component implements Poolable {

	float alpha;
	
	public void init(float alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		alpha = 1f;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	

}
