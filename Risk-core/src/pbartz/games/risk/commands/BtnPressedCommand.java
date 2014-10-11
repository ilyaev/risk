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
			
			entity.add(ComponentFactory.getPositionInterpolationComponent(
				EntityFactory.getEngine(), 
				EntityFactory.getEntityPositionComponent(entity), 
				MathUtils.random() * Gdx.graphics.getWidth(), 
				MathUtils.random() * Gdx.graphics.getHeight(),
				MathUtils.random() * 2, 
				Interpolation.EASE_OUT
			));
			
		}
		
	}

	@Override
	public String toString() {
		return "CMD_BtnPressed: " + this.tag;
	}

}