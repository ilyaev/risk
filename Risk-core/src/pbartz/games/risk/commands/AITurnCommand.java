package pbartz.games.risk.commands;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.MapGenerator;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.utils.Command;
import pbartz.games.utils.HexCell;
import pbartz.games.utils.Interpolation;

public class AITurnCommand extends Command {
	
	private int country;

	public AITurnCommand(int country) {
		this.country = country;
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
			
			EntityFactory.addCommand(new StartZoneSelectionCommand(country, srcZoneId), true);
			
			HexCell targetCapital = MapGenerator.getCapitalHex(targetZoneId);
			HexCell srcCapital = MapGenerator.getCapitalHex(srcZoneId);
			
			EntityFactory.addCommand(new MoveZoneSelectionCommand(
					targetZoneId, 
					(int)srcCapital.getCoordX(MapGenerator.cSize), 
					(int)srcCapital.getCoordY(MapGenerator.cSize)
			), true);
			
			
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
			
			EntityFactory.addCommand(new FinishZoneSelectionCommand(
				EntityFactory.currentZoneSelectionComponent,
				EntityFactory.currentZoneSelectionEntity
			), 0.7f);
			
			
		} else {
			
			EntityFactory.addCommand(new EndTurnCommand(country));
			
		}
		
	}

	@Override
	public String toString() {
		return String.format("CMD_AITurn for country %d", country);
	}

}
