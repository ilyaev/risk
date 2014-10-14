package pbartz.games.utils;

public class HexCell {

	public int r = 0;
	public int q = 0;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int aq = 0;
	public int ar = 0;
	
	public int zone = 0;
	
	public HexCell(int q, int r) {
		
		this.r = r;
		this.q = q;
		
		x = q - (r - (r&1)) / 2;
		z = r;
		y = -x - z;
		
		aq = x;
		ar = z;
		
		zone = 0;
		
	}
	
	public float getCoordX(int cellSize) {
		
		return (float) (cellSize * Math.sqrt(3) * (q + 0.5 * (r & 1)));
		
	}
	
	
	
	public int distanceTo(HexCell cell) {
		
		return (Math.abs(x - cell.x) + Math.abs(y - cell.y) + Math.abs(z - cell.z)) / 2;
		
	}

	public float getCoordY(int cellSize) {
		return cellSize * 3/2 * r + Metrics.controlPanelHeight - cellSize / 2f;
	}
	
	public static int getNeighborX(int direction) {
		
		int result = 1;
		
		if (direction == 1) {
			result = 1;
		} else if (direction == 2) {
			result = 0;
		} else if (direction == 3) {
			result = -1;
		} else if (direction == 4) {
			result = -1;
		} else if (direction == 5) {
			result = 0;
		}
		
		return result;
	}
	
	public static int getNeighborZ(int direction) {
		int result = 0;
		
		if (direction == 1) {
			result = -1;
		} else if (direction == 2) {
			result = -1;
		} else if (direction == 3) {
			result = 0;
		} else if (direction == 4) {
			result = 1;
		} else if (direction == 5) {
			result = 1;
		}
		
		return result;
	}
	
	public static int getQFromAxial(int x, int z) {
		return x + (z - (z&1)) / 2;
	}
	
	public static int getRFromAzial(int x, int z) {
		return z;
	}	
	
}