package pbartz.games.components.UI;

import pbartz.games.risk.EntityFactory;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LabelComponent extends Component implements Poolable {
	
	String caption;
	Label label;
	private String tag;
	
	public void init(String caption, String tag, Skin skin) {
		this.caption = caption;
		this.tag = tag;
		this.label = new Label(caption, skin);
		this.label.setPosition(-100, -100);
	}

	

	public String getCaption() {
		return caption;
	}



	public void setCaption(String caption) {
		this.caption = caption;
	}



	public Label getLabel() {
		return label;
	}



	public void setLabel(Label label) {
		this.label = label;
	}



	@Override
	public void reset() {
				
	}

}