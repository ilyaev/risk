package pbartz.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

public class ZoneComponent extends Component {

	int id = 0;
	int country = 0;
	int dices = 1;
	
	Array<Integer> neigbors = new Array<Integer>();
	
	
	public ZoneComponent(int id, int country) {
		this.id = id;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}
	
	public void addNeighbor(int neighbor) {
		if (neigbors.indexOf(neighbor, false) < 0) {
			neigbors.add(neighbor);
		}
	}

	public Array<Integer> getNeigbors() {
		return neigbors;
	}

	public int getDices() {
		return dices;
	}

	public void setDices(int dices) {
		this.dices = dices;
	}
	
}