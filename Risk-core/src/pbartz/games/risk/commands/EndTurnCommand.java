package pbartz.games.risk.commands;

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
		
	}

	@Override
	public String toString() {
		return "End Turn Command";
	}

}
