package pbartz.games.risk.commands;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.CommandFactory;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.MapGenerator;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.utils.Command;
import pbartz.games.utils.HexCell;
import pbartz.games.utils.Interpolation;

public class AITurnCommand extends Command {

	private int country;
	

	public AITurnCommand init(int country) {
		this.country = country;
		return this;
	}

	@Override
	public void execute() {
		
		int targetZoneId = 0;
		int srcZoneId = 0;
		
		for(int i = 1 ; i <= MapGenerator.getZonesCount() ; i++) {
			
			ZoneComponent zone = EntityFactory.getZoneComponentById(i);
			
			if (zone.getCountry() == country && zone.getDices() > 1) {
				
				srcZoneId = i;
				
				boolean flag = false;
				
				for(int j = 0 ; j < zone.getNeigbors().size ; j++) {
					
					ZoneComponent nbZone = EntityFactory.getZoneComponentById(zone.getNeigbors().get(j));
					if (nbZone.getDices() <= zone.getDices() && zone.getCountry() != nbZone.getCountry()) {
						flag = true;
						targetZoneId = zone.getNeigbors().get(j);
						break;
					}
					
				}
				
				if (flag) {
					break;
				}
				
			}
			
		}
		
		if (targetZoneId > 0 && srcZoneId > 0) {
			
			EntityFactory.addCommand(CommandFactory.createCommand(StartZoneSelectionCommand.class).init(country, srcZoneId), true);
			
			HexCell targetCapital = MapGenerator.getCapitalHex(targetZoneId);
			HexCell srcCapital = MapGenerator.getCapitalHex(srcZoneId);
			
			MoveZoneSelectionCommand cmd = CommandFactory.createCommand(MoveZoneSelectionCommand.class);
			cmd.init(
				targetZoneId, 
				(int)srcCapital.getCoordX(MapGenerator.cSize), 
				(int)srcCapital.getCoordY(MapGenerator.cSize)
			);
			
			EntityFactory.addCommand(cmd);
			
			PositionComponent arrowStartPosition = ComponentFactory.getPositionComponent(
					EntityFactory.getEngine(), 
					(int)srcCapital.getCoordX(MapGenerator.cSize), 
					(int)srcCapital.getCoordY(MapGenerator.cSize)
			);
			
			EntityFactory.currentZoneSelectionEntity.add(arrowStartPosition);
			
			EntityFactory.currentZoneSelectionEntity.add(ComponentFactory.getPositionInterpolationComponent(
					EntityFactory.getEngine(),  
					arrowStartPosition, 
					(int)targetCapital.getCoordX(MapGenerator.cSize), 
					(int)targetCapital.getCoordY(MapGenerator.cSize),
					0.5f, 
					Interpolation.EASE_OUT
			));
			
			EntityFactory.addCommand(CommandFactory.createCommand(FinishZoneSelectionCommand.class).init(
				EntityFactory.currentZoneSelectionComponent,
				EntityFactory.currentZoneSelectionEntity
			), 0.7f);
			
			
		} else {
			
			EntityFactory.addCommand(CommandFactory.createCommand(EndTurnCommand.class).init(country));
			
		}
		
	}

	@Override
	public String toString() {
		return String.format("CMD_AITurn for country %d", country);
	}

}
