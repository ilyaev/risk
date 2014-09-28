package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ShapeComponent extends Component implements Poolable {

	public static int SHAPE_RECTANGLE = 0;
	public static int SHAPE_CIRCLE = 1;
	public static int SHAPE_TRIANGLE = 2;
	
	int type = SHAPE_RECTANGLE;
	
	float cRadius;
	float rWidth, rHeight;
	
	public void init(int type, float cR) {
		this.type = type;
		this.cRadius = cR;
	}
	
	public void init(int type, float rWidth, float rHeight) {
		
		this.type = type;
		this.rWidth = rWidth;
		this.rHeight = rHeight;
		
	}
	
	public boolean isCircle() {
		return type == SHAPE_CIRCLE;
	}
	
	public boolean isRectangle() {
		return type == SHAPE_RECTANGLE;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public float getcRadius() {
		return cRadius;
	}

	public void setcRadius(float cRadius) {
		this.cRadius = cRadius;
	}

	public float getrWidth() {
		return rWidth;
	}

	public void setrWidth(float rWidth) {
		this.rWidth = rWidth;
	}

	public float getrHeight() {
		return rHeight;
	}

	public void setrHeight(float rHeight) {
		this.rHeight = rHeight;
	}
	
	

}