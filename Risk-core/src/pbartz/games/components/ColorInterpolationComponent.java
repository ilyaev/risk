package pbartz.games.components;

import pbartz.games.utils.Interpolation;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ColorInterpolationComponent extends Component implements Poolable {
	
	Color startPaint;
	Color endPaint;
	
	Color currentPaint;
	
	float speed;
	int type;
	float time = 0;
	float t,d,b,c;
	float cA, cR, cG, cB;
	
	public void init(Color oldPaint, Color endPaint, float speed, int type) {
	
		startPaint = oldPaint;
		
		this.endPaint = endPaint;
		
		this.speed = speed;
		this.type = type;		
		
		
		this.currentPaint = new Color();
		this.time = 0;
	}
	
	
	public void increaseTime(float diff) {
		this.time += diff;
	}

	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	public float getFinalAlpha() {
		return endPaint.a;
	}
	
	public float getCurrentAlpha() {
		
		t = time;
		d = speed;
		
		b = startPaint.a;
		c = (endPaint.a - b);
		
		cA = Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		return cA;
	}
	
	public Color getCurrentColor() {
		
		t = time;
		d = speed;
		
		b = startPaint.a;
		c = (endPaint.a - b);
		
		cA = Interpolation.calculateCurrentValue(type, t, b, c, d);


		b = startPaint.r;
		c = (endPaint.r - b);
		
		cR = Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		b = startPaint.g;
		c = (endPaint.g - b);
		
		cG = Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		b = startPaint.b;
		c = (endPaint.b - b);
		
		cB = Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		currentPaint.set(Math.max(0, Math.min(cR, 1f)), Math.max(0, Math.min(cG, 1f)), Math.max(0, Math.min(cB, 1f)), Math.max(0, Math.min(cA, 1f)));
		
		return currentPaint;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		time = 0;
	}
	
}
