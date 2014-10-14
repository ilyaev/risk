package pbartz.games.risk.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.utils.Command;
import pbartz.games.utils.Interpolation;

public class BtnPressedCommand extends Command {

	private String tag;
	private TextButton button;

	public BtnPressedCommand(String tag, TextButton button) {
		this.tag = tag;
		this.button = button;
	}
	
	@Override
	public void execute() {
		Entity entity = EntityFactory.getUIButton(tag);
		
		if (entity != null) {
			
			if (tag.equalsIgnoreCase("btnEndTurn")) {
				
				EntityFactory.addCommand(new EndTurnCommand(1));
				
			}

			
		}
		
	}

	@Override
	public String toString() {
		return "CMD_BtnPressed: " + this.tag;
	}

}