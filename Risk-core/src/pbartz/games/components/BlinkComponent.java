package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BlinkComponent extends Component implements Poolable {
	
	public static final int BLINK_NOT_INITED = 0;
	public static final int BLINK_IN = 1;
	public static final int BLINK_OUT = 2;
	
	int state = BLINK_NOT_INITED;
	
	float timeToNextState = 0;
	
	float blinkSpeed = 0.2f;
	
	float newAlpha;
	float oldAlpha;

	
	public boolean isNextEvent(float diff) {

		timeToNextState = Math.max(0, timeToNextState - diff);
		
		return timeToNextState == 0;
		
	}
	
	public float getTimeToNextState() {
		return timeToNextState;
	}

	public void setTimeToNextState(float timeToNextState) {
		this.timeToNextState = timeToNextState;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		setTimeToNextState(blinkSpeed);
	}

	public float getBlinkSpeed() {
		return blinkSpeed;
	}

	public void setBlinkSpeed(float blinkSpeed) {
		this.blinkSpeed = blinkSpeed;
	}

	@Override
	public void reset() {
		state = BLINK_NOT_INITED;
		timeToNextState = 0;
	}

	public float getNewAlpha() {
		return newAlpha;
	}

	public void setNewAlpha(float newAlpha) {
		this.newAlpha = newAlpha;
	}

	public float getOldAlpha() {
		return oldAlpha;
	}

	public void setOldAlpha(float oldAlpha) {
		this.oldAlpha = oldAlpha;
	}	
	
	
	
	

}
