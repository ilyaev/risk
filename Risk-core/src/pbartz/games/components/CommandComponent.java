package pbartz.games.components;

import pbartz.games.utils.Command;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CommandComponent extends Component implements Poolable {
	
	Command cmd;
	
	public void init (Command cmd) {
		this.cmd = cmd;
	}

	public Command getCmd() {
		return cmd;
	}


	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public void reset() {
		this.cmd = null;
	}

}
