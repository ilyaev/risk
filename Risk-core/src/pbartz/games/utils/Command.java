package pbartz.games.utils;

import com.badlogic.ashley.core.PooledEngine;

public abstract class Command {
	
	public int index;
	public static int lastIndex = 0; 
	
	public String tag = "DEFAULT";
	
	public boolean free = false;
	
	public PooledEngine engine;
	
	public abstract void execute();
	
	public abstract String toString();
	
	public Command() {
		Command.lastIndex += 1;
		index = Command.lastIndex;
		free = false;
	}
	
	public void reset() {
		free = true;
	}

}