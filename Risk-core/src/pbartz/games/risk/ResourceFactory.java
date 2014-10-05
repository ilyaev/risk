package pbartz.games.risk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class ResourceFactory {
	
	private static ObjectMap<String, Texture> cache = new ObjectMap<String, Texture>(); 
	
	
	public static Texture getTexture(String fileName) {
		
		if (cache.get(fileName) == null) {
			
			cache.put(fileName, new Texture(Gdx.files.internal(fileName)));
			
		}
		
		return cache.get(fileName);		
	}

}
