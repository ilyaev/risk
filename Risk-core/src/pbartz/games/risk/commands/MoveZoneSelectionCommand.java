package pbartz.games.risk.commands;

import com.badlogic.gdx.utils.Pool.Poolable;

import pbartz.games.utils.Command;
import pbartz.games.utils.EventBus;

public class MoveZoneSelectionCommand extends Command implements Poolable {

	int currentZoneId, screenX, screenY;
	
	
	
	public MoveZoneSelectionCommand init(int currentZoneId, int screenX, int screenY) {
		
		this.currentZoneId = currentZoneId;
		this.screenX = screenX;
		this.screenY = screenY;
		
		return this;
		
	}

	@Override
	public void execute() {
		
		EventBus.setInt("zone_hover", currentZoneId);
		EventBus.setInt("hover_x", screenX);
		EventBus.setInt("hover_y", screenY);
		
	}

	@Override
	public String toString() {
		return String.format("CMD_MoveZoneSelection: zoneId: %d, x: %d, y: %d", currentZoneId, screenX, screenY);
	}
	
	public void reset() {
		
	}

}
