package pbartz.games.risk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class ResourceFactory {
	
	private static ObjectMap<String, Texture> cache = new ObjectMap<String, Texture>(); 
	
	
	public static void init() {
		cache.clear();
	}
	
	public static Texture getTexture(String fileName) {
		
		if (cache.get(fileName) == null) {
			
			cache.put(fileName, new Texture(Gdx.files.internal(fileName)));
			
		}
		
		return cache.get(fileName);		
	}
	
	public static Texture getFlatColorTexture(Color color) {
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		
		Texture texture = new Texture(pixmap);	
		
		return texture;
		
	}

}
