package pbartz.games.components.UI;

import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.commands.BtnPressedCommand;
import pbartz.games.risk.commands.EndTurnCommand;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ButtonComponent  extends Component implements Poolable {
	
	TextButton button;
	
	private String tag;

	private String label;

	public void init(String label, String otag, Skin skin) {
		this.tag = otag;	
		this.label = label;
		
		button = new TextButton(this.label, skin, "default");
		button.setPosition(-100, -100);
		button.setSize(1f, 1f);
		
		button.addListener(new ClickListener() {
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	EntityFactory.addCommand(new BtnPressedCommand(tag, button));
	            }
	    });
		 
	}
	
	public TextButton getButton() {
		return button;
	}
	
	@Override
	public void reset() {
		tag = "";
		if (button != null) {
			button.remove();
		}
	}

	public void setCaption(String caption) {
		button.setText(caption);
		
	}
	
}
