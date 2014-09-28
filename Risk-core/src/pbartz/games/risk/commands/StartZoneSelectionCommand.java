package pbartz.games.risk.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.MapGenerator;
import pbartz.games.utils.Command;
import pbartz.games.utils.Interpolation;

public class StartZoneSelectionCommand extends Command {

	int countryId;
	int srcZoneId;
	
	public StartZoneSelectionCommand(int countryId, int srcZoneId) {
		this.countryId = countryId;
		this.srcZoneId = srcZoneId;
	}
	
	@Override
	public void execute() {		
		
		if (EntityFactory.getZoneComponentById(srcZoneId).getDices() > 1) {
		
			EntityFactory.startBlinkZone(srcZoneId);
			EntityFactory.createZoneSelector(srcZoneId);
			
		}
	}

	@Override
	public String toString() {
		return String.format("CMD_StartZoneSelection: countryId: %d, srcZoneId: %d", countryId, srcZoneId);
	}

}
