package pbartz.games.risk.commands;

import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.MapGenerator;
import pbartz.games.utils.Command;

public class EndTurnCommand extends Command {
	
	private int country;

	public EndTurnCommand(int country) {
		
		this.country = country;
		
	}

	@Override
	public void execute() {
		
		int toAdd = MapGenerator.getCountryAdjustedZonesCount(country);		
		
		if (toAdd > 0) {
			MapGenerator.distributeDicesToCountry(toAdd, country);
		}
		
		country += 1;
		
		if (country > MapGenerator.getCountriesCount()) {
			
			country = 1;
			
		}
		
		//if (country > 1) {
			
			EntityFactory.addCommand(new AITurnCommand(country), 0.5f);
			
		//}
		
	}

	@Override
	public String toString() {
		return "End Turn Command";
	}

}
