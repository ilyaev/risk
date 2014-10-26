package pbartz.games.systems;

import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.CommandFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.commands.AITurnCommand;
import pbartz.games.utils.EventBus;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

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
	
	ZoneComponent srcZoneCmp = null;
	ZoneComponent targetZoneCmp = null;
	
	Entity rollEntity = null;
	
	public GameFlowSystem() {
		
		super(1000);
		
	}
	
	public void update(float deltaTime) {
		
		processEvents();
	
		if (state == START_ROLL) {
			
			int srcDices = srcZoneCmp.getDices();
			int targetDices = targetZoneCmp.getDices();
			
			int srcRollNumber = 0;
			int targetRollNumber = 0;
			
			for(int i = 0 ; i < srcDices ; i++) {
				srcRollNumber += MathUtils.random(1, 6);
			}
			
			for(int i = 0 ; i < targetDices ; i++) {
				targetRollNumber += MathUtils.random(1, 6);
			}
			
			Gdx.app.log("ROLL", String.format("%d (%d) vs. %d (%d)", srcRollNumber, srcDices, targetRollNumber, targetDices));
			
			EntityFactory.setTurnInfoText(String.format("%d (%d) vs. %d (%d)", srcRollNumber, srcDices, targetRollNumber, targetDices));
			
			EntityFactory.decreaseZoneDices(srcZone, srcDices - 1);
			
			if (srcRollNumber > targetRollNumber) {

				EntityFactory.turnZoneToCountry(targetZone, srcZone);
				
				int diff = (srcDices - 1) - targetDices;

				if (diff > 0) {
					EntityFactory.increaseZoneDices(targetZone, diff);
				} else if (diff < 0) {
					EntityFactory.decreaseZoneDices(targetZone, (-1)*diff);
				}
				
			}
			
			if (srcZoneCmp.getCountry() > 1) {
			
				EntityFactory.addCommand(CommandFactory.createCommand(AITurnCommand.class).init(srcZoneCmp.getCountry()), 0.5f);
				
			}
			
			state = ROLLING;
			
		}
		
		if (state == ROLLING) {
			
			state = WAITING_FOR_SELECT;
			
		}
		
		
	}

	private void processEvents() {

		if (EventBus.popString("GAME_STATE").equalsIgnoreCase("START_ROLL")) {
			
			srcZone = EventBus.popInt("SRC_ZONE");
			targetZone = EventBus.popInt("TARGET_ZONE");
			
			srcEntity = EntityFactory.getZoneEntityById(srcZone);
			targetEntity = EntityFactory.getZoneEntityById(targetZone);
			
			srcZoneCmp = EntityFactory.getZoneComponentById(srcZone);
			targetZoneCmp = EntityFactory.getZoneComponentById(targetZone);
			
			Gdx.app.log("START", String.format("START_ROLL: %d to %d", srcZone, targetZone));
			

			state = START_ROLL;			
		}
		
	}

	public void reset() {

		state = WAITING_FOR_SELECT;
		
	}

}
