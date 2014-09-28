package pbartz.games.utils;

import com.badlogic.gdx.utils.ObjectMap;

public class EventBus {

	
	static ObjectMap<String, String> bus = new ObjectMap<String, String>();
	
	public static void setString(String event, String params) {
		
		bus.put(event, params);
		
	}
	
	public static String getString(String event) {
		
		return bus.get(event, "");
		
	}
	
	public static String popString(String event) {
		
		String result = bus.get(event, "");
		bus.remove(event);
		return result;
		
	}
	
	public static void setInt(String event, int params) {
		
		bus.put(event, Integer.toString(params));
		
	}
	
	public static int getInt(String event) {
		
		return Integer.valueOf(bus.get(event, "0"));
		
	}
	
	public static int popInt(String event) {
		
		int result = Integer.valueOf(bus.get(event, "0"));
		bus.remove(event);
		return result;
	}
	
	public static void clear() {
		
		bus.clear();
		
	}
	
}