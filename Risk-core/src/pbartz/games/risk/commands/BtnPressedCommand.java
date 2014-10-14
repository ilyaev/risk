package pbartz.games.risk.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pbartz.games.factories.CommandFactory;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.utils.Command;
import pbartz.games.utils.Interpolation;

public class BtnPressedCommand extends Command {

	String tag = "BTN_PRESSED";
	
	private String btnTag;
	private TextButton button;

	public BtnPressedCommand init(String tag, TextButton button) {
		this.btnTag = tag;
		this.button = button;
		return this;
	}
	
	@Override
	public void execute() {
		Entity entity = EntityFactory.getUIButton(btnTag);
		
		if (entity != null) {
			
			if (btnTag.equalsIgnoreCase("btnEndTurn")) {
				
				EntityFactory.addCommand(CommandFactory.createCommand(EndTurnCommand.class).init(1));
				
			}

			
		}
		
	}

	@Override
	public String toString() {
		return "CMD_BtnPressed: " + this.btnTag;
	}

}