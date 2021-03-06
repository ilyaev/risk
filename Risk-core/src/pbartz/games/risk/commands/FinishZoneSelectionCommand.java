package pbartz.games.risk.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;

import pbartz.games.components.ZoneSelectionComponent;
import pbartz.games.risk.EntityFactory;
import pbartz.games.utils.Command;
import pbartz.games.utils.EventBus;

public class FinishZoneSelectionCommand extends Command {

	ZoneSelectionComponent selection;
	Entity selectionEntity;
	
	String tag = "FINISH_ZONE_SELECTION";
	
	public FinishZoneSelectionCommand init(ZoneSelectionComponent selection, Entity selectionEntity) {
		
		this.selection = selection;
		this.selectionEntity = selectionEntity;
		
		return this;
		
	}
	
	@Override
	public void execute() {
		
		EntityFactory.finishBlinkZone(selection.getSrcZone());
		EntityFactory.finishBlinkZone(selection.getTargetZone());		
		
		
		if (selection.getTargetZone() > -1) {
			
			EventBus.setString("GAME_STATE", "START_ROLL");
			EventBus.setInt("SRC_ZONE", selection.getSrcZone());
			EventBus.setInt("TARGET_ZONE", selection.getTargetZone());

			
		}
		
		engine.removeEntity(this.selectionEntity);		

	}

	@Override
	public String toString() {
		return String.format("CMD_FinishZoneSelection: srcZone: %d, targetZone %d", selection.getSrcZone(), selection.getTargetZone());
	}

}
