package pbartz.games.systems;

import pbartz.games.risk.EntityFactory;
import pbartz.games.utils.EventBus;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

public class GameFlowSystem extends EntitySystem {
	
	public static final int WAITING_FOR_SELECT = 1;
	public static final int SELECT_TARGET = 2;
	public static final int START_ROLL = 3;
	public static final int ROLLING = 4;
	
	public static int state = WAITING_FOR_SELECT;
	
	int srcZone = -1;
	int targetZone = -1;
	
	int srcCountry = -1;
	int targetCountry = -1;
	
	Entity srcEntity = null;
	Entity targetEntity = null;
	
	Entity rollEntity = null;
	
	public GameFlowSystem() {
		
		super(1000);
		
	}
	
	public void update(float deltaTime) {
		processEvents();
	
		if (state == START_ROLL) {
			
			//EntityFactory.turnZoneToCountry(targetZone, srcCountry);
			//rollEntity = EntityFactory.createRollEntity(srcEntity, targetEntity);
			state = ROLLING;
			
		}
		
		if (state == ROLLING) {
			
			
			
		}
		
		
	}

	private void processEvents() {
		if (EventBus.popString("GAME_STATE") == "START_ROLL") {
			
			srcZone = EventBus.popInt("SRC_ZONE");
			targetZone = EventBus.popInt("TARGET_ZONE");
			
			srcEntity = EntityFactory.getZoneEntityById(srcZone);
			targetEntity = EntityFactory.getZoneEntityById(targetZone);
			
			//srcCountry = srcEntity.getComponent(ZoneComponent.class).getCountry();
			//targetCountry = targetEntity.getComponent(ZoneComponent.class).getCountry();
			

			state = START_ROLL;			
		}
		
	}

}
