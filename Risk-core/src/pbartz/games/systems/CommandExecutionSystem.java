package pbartz.games.systems;

import pbartz.games.components.CommandComponent;

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
		
		//Gdx.app.log("CMD_EXEC", command.getCmd().toString());
		
		command.getCmd().execute();
		
		engine.removeEntity(entity);
		
		
	}

}
