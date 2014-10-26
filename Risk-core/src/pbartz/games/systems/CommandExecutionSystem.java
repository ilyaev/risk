package pbartz.games.systems;

import pbartz.games.components.CommandComponent;
import pbartz.games.factories.CommandFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;

public class CommandExecutionSystem extends DynamicIteratingSystem {

	private PooledEngine engine;
	
	private ComponentMapper<CommandComponent> cm = ComponentMapper.getFor(CommandComponent.class);
	
	CommandComponent command;
	
	@SuppressWarnings("unchecked")
	public CommandExecutionSystem(PooledEngine engine) {
		super(Family.getFor(CommandComponent.class));
		this.engine = engine;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		command = cm.get(entity);
		
		Gdx.app.log("CMD_EXEC", "#" + Integer.toString(command.getCmd().index) + " " +command.getCmd().toString());
		Gdx.app.log("MEM", "Total Memory:" + Runtime.getRuntime().totalMemory() / (1024*1024));
		Gdx.app.log("MEM", "Used Memory:" + Runtime.getRuntime().freeMemory() / (1024*1024));
		command.getCmd().execute();
		
		CommandFactory.freeCommand(command.getCmd());
		
		engine.removeEntity(entity);
		
		
	}

}
