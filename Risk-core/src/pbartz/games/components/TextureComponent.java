package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent extends Component implements Poolable {
	
	Texture texture;
	Rectangle rect;
	Pixmap pixmap;
	
	boolean flipped = false;

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
	
	public void init(Texture texture) {
		
		this.texture = texture;
		rect = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
		flipped = false;
		
	}
	
	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public Rectangle getRect() {
		return rect;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Pixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(Pixmap pixmap) {
		this.pixmap = pixmap;
	}

	
	
}
