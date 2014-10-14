package pbartz.games.factories;

import pbartz.games.utils.Command;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ReflectionPool;

public class CommandFactory {

	private static CommandPools commandPools = new CommandPools(100, 1000);
	
	public static <T extends Command> T createCommand(Class<T> componentType) {
		return commandPools.obtain(componentType);
	}
	
	public static void freeCommand(Command cmd) {
		
		commandPools.free(cmd);
		
	}
	
	private static class CommandPools {
		private ObjectMap<Class<?>, ReflectionPool> pools;
		private int initialSize;
		private int maxSize;
		
		public CommandPools(int initialSize, int maxSize) {
			this.pools = new ObjectMap<Class<?>, ReflectionPool>();
			this.initialSize = 0;
			this.maxSize = 0;
		}
		
		public <T> T obtain(Class<T> type) {
			ReflectionPool pool = pools.get(type);
			
			if (pool == null) {
				pool = new ReflectionPool(type, initialSize, maxSize);
				pools.put(type, pool);
			}
			
			return (T)pool.obtain();
		}
		
		public void free(Object object) {
			if (object == null) {
				throw new IllegalArgumentException("object cannot be null.");
			}
			
			ReflectionPool pool = pools.get(object.getClass());
			
			if (pool == null) {
				return; // Ignore freeing an object that was never retained.
			}
			
			pool.free(object);
		}

		public void freeAll(Array objects) {
			if (objects == null) throw new IllegalArgumentException("objects cannot be null.");
			
			for (int i = 0, n = objects.size; i < n; i++) {
				Object object = objects.get(i);
				if (object == null) continue;
				free(object);
			}
		}
	}
}
