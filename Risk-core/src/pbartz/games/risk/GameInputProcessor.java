package pbartz.games.risk;

import pbartz.games.utils.Metrics;

import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {
	
	public static final int TOUCH_DOWN = 0;
	public static final int TOUCH_UP = 1;
	public static final int TOUCH_MOVE = 2;
	public static final int TOUCH_CLEAR = -1;
	
	public static boolean isTouchDown = false;
	public static boolean isTouchUp = false;
	public static boolean isTouchMove = false;
	public static int touchMode = TOUCH_CLEAR;
	public static int screenX = 0;
	public static int screenY = 0;

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		GameInputProcessor.setTouchMode(TOUCH_DOWN, screenX, screenY);
		return false;
	}

	private static void setTouchMode(int touchMode, int screenX, int screenY) {
		GameInputProcessor.touchMode = touchMode;
		GameInputProcessor.screenX = screenX;
		GameInputProcessor.screenY = (int) (Metrics.heightPx - screenY);
		
		isTouchDown = false;
		isTouchUp = false;
		isTouchMove = false;
		
		if (touchMode == TOUCH_DOWN) {
			GameInputProcessor.isTouchDown = true;
		} else if (touchMode == TOUCH_UP) {
			GameInputProcessor.isTouchUp = true;
		} else if (touchMode == TOUCH_MOVE) {
			GameInputProcessor.isTouchMove = true;
		}
	}
	
	public static boolean isTouched() {
		return GameInputProcessor.touchMode != TOUCH_CLEAR;
	}
	
	public static void clearTouch() {
		GameInputProcessor.setTouchMode(TOUCH_CLEAR, -1, -1);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		GameInputProcessor.setTouchMode(TOUCH_UP, screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		GameInputProcessor.setTouchMode(TOUCH_MOVE, screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}