package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ColorComponent extends Component implements Poolable {
	
	Color color;
	
	float r,g,b,a;
	
	public void init(float r, float g, float b, float a) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		
		color = new Color(r, g, b, a);
		
	}
	
	private void updatePaint() {
		this.color.set(this.r, this.g, this.b, this.a);
	}
	
	public void setARGB(float a, float r, float g, float b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
		this.updatePaint();
	}
	
	public void setAlpha(int a) {
		this.a = a;
		this.updatePaint();
	}

	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color.set(color);
		this.a = color.a;
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
	}

	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}