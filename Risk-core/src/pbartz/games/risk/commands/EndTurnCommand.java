package pbartz.games.risk.commands;

import com.badlogic.ashley.core.Entity;

import pbartz.games.factories.CommandFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.MapGenerator;
import pbartz.games.systems.UIButtonSystem;
import pbartz.games.utils.Command;

public class EndTurnCommand extends Command {
	
	private int country;
	String tag = "END_TURN";

	public EndTurnCommand init(int country) {
		
		this.country = country;
		return this;
		
	}

	@Override
	public void execute() {
		
		int toAdd = MapGenerator.getCountryAdjustedZonesCount(country);		
		
		if (toAdd > 0) {
			MapGenerator.distributeDicesToCountry(toAdd, country);
		}
		
		int prevCountry = country;
		
		country += 1;
		
		
		
		if (country > MapGenerator.getCountriesCount()) {
			
			country = 1;
			
		}
		
		EntityFactory.UISwitchPlayer(prevCountry, country);
		
		if (country > 1) {
		
			EntityFactory.addCommand(CommandFactory.createCommand(AITurnCommand.class).init(country), 0.5f);
			
		}
		
	}

	@Override
	public String toString() {
		return "End Turn Command";
	}

}
