package pbartz.games.factories;

import pbartz.games.utils.Command;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ReflectionPool;

public class CommandFactory {

	private static CommandPools commandPools = new CommandPools(50, 50);
	
	public static <T extends Command> T createCommand(Class<T> componentType) {
		
		return commandPools.obtain(componentType);
		
	}
	
	public static void freeCommand(Command cmd) {
		
		commandPools.free(cmd);
		
	}
	
	private static class CommandPools {
		
		@SuppressWarnings("rawtypes")
		private ObjectMap<Class<?>, ReflectionPool> pools;
		private int initialSize;
		private int maxSize;
		
		@SuppressWarnings("rawtypes")
		public CommandPools(int initialSize, int maxSize) {
			this.pools = new ObjectMap<Class<?>, ReflectionPool>();
			this.initialSize = initialSize;
			this.maxSize = maxSize;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <T> T obtain(Class<T> type) {
			ReflectionPool pool = pools.get(type);
			
			if (pool == null) {
				pool = new ReflectionPool(type, initialSize, maxSize);
				pools.put(type, pool);
			}
			
			return (T)pool.obtain();
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void free(Object object) {
			if (object == null) {
				throw new IllegalArgumentException("object cannot be null.");
			}
			
			ReflectionPool pool = pools.get(object.getClass());
			
			if (pool == null) {
				return;
			}
			
			pool.free(object);
		}

		@SuppressWarnings({ "rawtypes", "unused" })
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
