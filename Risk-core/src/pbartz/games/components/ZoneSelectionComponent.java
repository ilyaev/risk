package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ZoneSelectionComponent extends Component implements Poolable {
	
	int srcZone = -1;
	int targetZone = -1;
	
	public void init(int srcZone) {
		
		this.srcZone = srcZone;		
		
	}

	public int getSrcZone() {
		return srcZone;
	}

	public void setSrcZone(int srcZone) {
		this.srcZone = srcZone;
	}

	public int getTargetZone() {
		return targetZone;
	}

	public void setTargetZone(int targetZone) {
		this.targetZone = targetZone;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		targetZone = -1;
	}
	
	
	
}
