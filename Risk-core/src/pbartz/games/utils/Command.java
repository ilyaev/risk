package pbartz.games.utils;

import com.badlogic.ashley.core.PooledEngine;

public abstract class Command {
	
	public PooledEngine engine;
	
	public abstract void execute();
	
	public abstract String toString();

}
