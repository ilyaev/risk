package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ArrowComponent extends Component implements Poolable {

	Texture texture;
	int startX;
	int endX;
	int startY;
	int endY;

	
	public void init(int sX, int sY, int eX, int eY) {
		
		if (texture == null) {
			
			Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(1f, 1f, 1f, 0.5f);
			pixmap.fill();
			
			texture = new Texture(pixmap);			
			
		}
		
		startX = sX;
		startY = sY;
		
		endX = eX;
		endY = eY;
		
	}
	
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
	
	public Texture getTexture() {
		return texture;
	}
	
	
	
	

}
