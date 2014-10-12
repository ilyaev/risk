package pbartz.games.risk;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Pixmap;

import pbartz.games.components.PositionComponent;
import pbartz.games.components.TextureComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.utils.HexCell;

public class MapGenerator {
	
	int cellsH = 0;
	int cellsV = 0;
	int cellSize = 0;
	int cellHeight = 0;
	int cellWidth = 0;
	int mapWidth = 0;
	int mapHeight = 0;
	static int zonesCount = 40;
	static int countryCount = 5;
	
	public static int cSize = 0;
	
	HexCell cells[][];
	
	static Array<HexCell> capitals = new Array<HexCell>();
	private Array<Integer> zoneCountryPool = new Array<Integer>();
	private Texture diceTexture;	
	private static Array<Entity> zones = new Array<Entity>();
	private static Entity dices[][];
	private static Vector2 tmpVector2 = new Vector2();
	
	private static Array<Integer> tmpZones = new Array<Integer>();
	
	public MapGenerator(int cellsH, int cellsV, int cellSize) {
		
		this.cellsH = cellsH;
		this.cellsV = cellsV;
		this.cellSize = cellSize;
		
		MapGenerator.cSize = cellSize;
		
		cellHeight = cellSize * 2;
		cellWidth = (int) Math.round((Math.sqrt(3) / 2) * cellHeight);
		
		mapWidth = cellsH * cellWidth + cellWidth;
		mapHeight = cellsV * (cellHeight / 4 * 3) + cellHeight / 4 ;
		
		cells = new HexCell[cellsH][cellsV];
		
		dices = new Entity[zonesCount + 1][16];
		
		createWorld();
		
	}
	
	public static int getCountriesCount() {
		
		return countryCount;
		
	}
	
	public void generate(PooledEngine engine) {
		
		zones.clear();
		
		for(int zone = 1 ; zone <= zonesCount ; zone++) {
			
			
			int leftX = 10000;
			int leftY = 10000;
			int rightX = -1000;
			int rightY = -1000;
			
			float xleft = 10000;
			float yleft = 10000;
			float xright = -10000;
			float yright = -10000;
			
			HexCell leftCell = null;
			HexCell rightCell = null;
			
			for(int q = 0 ; q < cellsH ; q++) {
				for(int r = 0 ; r < cellsV ; r++) {					
					
					HexCell cell = cells[q][r];

					if (cell.zone != zone) continue;
					
					float nxleft = cell.getCoordX(cellSize);
					float nyleft = cell.getCoordY(cellSize);
					
					if (nxleft < xleft) {
						xleft = nxleft;
						leftX = cell.q;
					}
					
					if (nyleft < yleft) {
						yleft = nyleft;
						leftY = cell.r;
					}
					
					if (nxleft > xright) {
						xright = nxleft;
						rightX = cell.q;						
					}
					
					if (nyleft > yright) {
						yright = nyleft;
						rightY = cell.r;
 					}
					
				}
			}
			
			if (leftY % 2 != 0) leftY -= 1;
			if (rightY %2 == 0) rightY += 1;
			
			leftCell = cells[leftX][leftY];
			rightCell = cells[rightX][rightY];
			
			xleft = leftCell.getCoordX(cellSize);
			yleft = leftCell.getCoordY(cellSize);
			
			xright = rightCell.getCoordX(cellSize) + cellWidth + 1;
			yright = rightCell.getCoordY(cellSize) + cellHeight + 1;
			
			Pixmap pixmap = new Pixmap(Math.round(xright) - Math.round(xleft), Math.round(yright - yleft), Pixmap.Format.RGBA8888);
			
			pixmap.setColor(0, 0, 0, 0);
			pixmap.fill();
			
			ZoneComponent zoneComponent = new ZoneComponent(zone, zoneCountryPool.get(zone - 1) + 1);
			
			Color pColor = EntityFactory.getColorByZone(zoneComponent.getCountry());
			
			pixmap.setColor(pColor);
			
			for(int q = 0 ; q < cellsH ; q++) {
				for(int r = 0 ; r < cellsV ; r++) {					
					
					HexCell cell = cells[q][r];

					if (cell.zone != zone) continue;					
					
					for(int direction = 0 ; direction < 6 ; direction ++ ) {
						
						int dX = cell.x + HexCell.getNeighborX(direction);
						int dZ = cell.z + HexCell.getNeighborZ(direction);
						
						int dQ = HexCell.getQFromAxial(dX, dZ);
						int dR = HexCell.getRFromAzial(dX, dZ);
						
						if (dQ >= 0 && dQ < cellsH && dR >= 0 && dR < cellsV) {
						
							HexCell neighbor = cells[dQ][dR];
							
							if (neighbor.zone != zone) {
								zoneComponent.addNeighbor(neighbor.zone);
							}
						}
						
					}
					

					float x = cell.getCoordX(cellSize);
					float y = cell.getCoordY(cellSize);
					
					float topX = x - xleft;
					float topY = y - yleft;
					
					float centerX = topX + cellWidth / 2;
					float centerY = topY + cellHeight / 2;
					
					float old_x_i = 0;
					float old_y_i = 0;
					
					float prev_x_i = 0;
					float prev_y_i = 0;
					
					float first_x_i = 0;
					float first_y_i = 0;
					
					for(int i = 0 ; i < 6 ; i++) {
						
						float angle = (float) (2 * Math.PI / 6 * (i + 0.5));
					    float x_i = (float) (centerX + cellSize * Math.cos(angle));
					    float y_i = (float) (centerY + cellSize * Math.sin(angle));
					    
					    if (i == 0) {					   
					    	first_x_i = x_i;
					    	first_y_i = y_i;
					    } else {
					    	//pixmap.fillTriangle((int)centerX, (int)centerY, (int)x_i, (int)y_i, (int)prev_x_i, (int)prev_y_i);
					    	pixmap.fillTriangle(MathUtils.round(centerX), MathUtils.round(centerY), MathUtils.round(x_i), MathUtils.round(y_i), MathUtils.round(prev_x_i), MathUtils.round(prev_y_i));
						}
					    
					    prev_x_i = x_i;
				    	prev_y_i = y_i;
						
					}
					
					pixmap.fillTriangle((int)centerX, (int)centerY, (int)first_x_i, (int)first_y_i, (int)prev_x_i, (int)prev_y_i);
					
					
					pixmap.setColor(0, 0, 0, 1f);
					
					for(int i = 0 ; i < 6 ; i++) {
						
						
						
						float angle = (float) (2 * Math.PI / 6 * (i + 0.5));
					    float x_i = (float) (centerX + cellSize * Math.cos(angle));
					    float y_i = (float) (centerY + cellSize * Math.sin(angle));
					    
					    for(int direction = 0 ; direction < 6 ; direction ++ ) {
							
							int dX = cell.x + HexCell.getNeighborX(direction);
							int dZ = cell.z + HexCell.getNeighborZ(direction);
							
							int dQ = HexCell.getQFromAxial(dX, dZ);
							int dR = HexCell.getRFromAzial(dX, dZ);
							
							if (dQ >= 0 && dQ < cellsH && dR >= 0 && dR < cellsV) {
							
								HexCell neighbor = cells[dQ][dR];
								
								if (neighbor.zone != zone && direction == 6 - i) {
									
									drawThickLine(pixmap,old_x_i, old_y_i, x_i, y_i, 1);
									
									zoneComponent.addNeighbor(neighbor.zone);
								}
							}
							
						}					    
					    
					    old_x_i = x_i;
					    old_y_i = y_i;
						
					}
					
					pixmap.setColor(pColor);
					
				}
			}
			
			pixmap.setColor(1f, 0, 0, 1f);

			//pixmap.fillRectangle(pixmap.getWidth() / 2 - 10, pixmap.getHeight() / 2 - 10, 20, 20);			
			
			Entity entity = engine.createEntity();
			
			PositionComponent position = ComponentFactory.getPositionComponent(engine, 
					Math.round(xleft + pixmap.getWidth() / 2), 
					Math.round(yleft + pixmap.getHeight() / 2)
				);

			entity.add(position);
			
			entity.add(ComponentFactory.getRectShapeComponent(engine, 
				pixmap.getWidth(),
				pixmap.getHeight()
			));
			
			TextureComponent tc = ComponentFactory.getTextureComponent(engine, new Texture(pixmap));
			tc.setFlipped(true);
			
			tc.setPixmap(pixmap);
			
			entity.add(tc);

			entity.add(zoneComponent);
			
			entity.add(ComponentFactory.getTouchComponent(engine));

			entity.add(ComponentFactory.getColorAlphaComponent(engine, 1f));
			
			engine.addEntity(entity);
			
			zones.add(entity);
			
		}		
		
		distributeDices();
		
		drawStacks();
		
	}
	
	private void distributeDices() {
		ZoneComponent zone;
		
		for (int i = 1 ; i <= zonesCount ; i++) {
			
			zone = EntityFactory.getZoneComponentById(i);
			zone.setDices(MathUtils.random(1,7));
			
		}
	}

	private void drawStacks() {
		HexCell srcHex = null;
		
		float srcPointX;
		float srcPointY;
		
		ZoneComponent zone;
		
		for(int i = 1 ; i <= zonesCount ; i++) {
				
			srcHex = MapGenerator.getCapitalHex(i);
			zone = EntityFactory.getZoneComponentById(i);
			
			srcPointX = srcHex.getCoordX(MapGenerator.cSize);
			srcPointY = srcHex.getCoordY(MapGenerator.cSize);
			
			createStacks((int)srcPointX, (int)srcPointY, zone.getDices(), i);
			
			//EntityFactory.createUILabel(Integer.toString(i), srcPointX - 15f, srcPointY, 1f, 1f, 1f, 1f, "lbZone_" + Integer.toString(i));
			
		}
		
	}
	
	public static Vector2 getZoneDicePosition(int zoneId, int dice) {
		
		tmpVector2 = getZoneCenterPosition(zoneId);
		
		float x = tmpVector2.x;
		float y = tmpVector2.y;
		
		int step = dice;
		
		if (step >= 4) {
			
			x += 12;
			y -= 10;
			step = dice - 4;		
			
		}
		
		y += (step * 12);	
		
		tmpVector2.set(x, y);
		
		return tmpVector2;
		
	}
	
	public static Vector2 getZoneCenterPosition(int zoneId) {
		
		HexCell srcHex = MapGenerator.getCapitalHex(zoneId);
		
		tmpVector2.set(srcHex.getCoordX(MapGenerator.cSize), srcHex.getCoordY(MapGenerator.cSize));
		
		return tmpVector2;
		
	}
	

	private void createStacks(int x, int y, int dices, int zoneId) {
		
		if (diceTexture == null) {
			
			diceTexture = ResourceFactory.getTexture("dice.png"); //new Texture(Gdx.files.internal("dice.png"));
			
		}
		
		int len = dices;
		
		int step = 0;
		
		for(int i = 0 ; i < len ; i++) {
			
			if (i == 4) {
				x += 12;
				y -= 10;
				step = 0;
			}
		
			MapGenerator.dices[zoneId][i] = EntityFactory.createTextureEntity(diceTexture, x, y + (step * 12));
			
			step += 1;
			
		}
		
	}

	private void drawThickLine(Pixmap pixmap, float x1, float y1, float x2, float y2, float thickness) {
		
		float width = x2 - x1;
		float height = y2 - y1;
		
		double length = Math.sqrt(width * width + height * height);
		
		double xS = (thickness * height  / length);
		double yS = (thickness * width / length);
		
		pixmap.fillTriangle(
			MathUtils.round((float) (x1 - xS)), 
			MathUtils.round((float) (y1 + yS)), 
			MathUtils.round((float) (x1 + xS)), 
			MathUtils.round((float) (y1 - yS)), 
			MathUtils.round((float) (x2 + xS)), 
			MathUtils.round((float) (y2 - yS))
		);
		
		pixmap.fillTriangle(
			MathUtils.round((float) (x1 - xS)), 
			MathUtils.round((float) (y1 + yS)), 
			MathUtils.round((float) (x2 + xS)), 
			MathUtils.round((float) (y2 - yS)),
			MathUtils.round((float) (x2 - xS)), 
			MathUtils.round((float) (y2 + yS))
		);
		
	}
	
	private void createWorld() {
		
		createBlankWorld();
		
		putCapitalsToWorld();
		
		createWorldZones();
		
		generateZoneCountryPool();
		
	}
	
	private void generateZoneCountryPool() {
		
		int zonesPerCountry = (int) Math.floor(zonesCount / countryCount);
		
		for(int i = 0 ; i < countryCount ; i++) {
			
			for(int j = 0 ; j < zonesPerCountry ; j++) {
				zoneCountryPool.add(i);
			}
			
		}
		
		zoneCountryPool.shuffle();
		
	}
	
	private void createWorldZones() {
		for(int q = 0 ; q < cellsH ; q++) {
			for(int r = 0 ; r < cellsV ; r++) {
				
				HexCell cell = cells[q][r];
				
				Integer distances[] = new Integer[capitals.size];

				for(int i = 0 ; i < capitals.size ; i++) {
					
					distances[i] = cell.distanceTo(capitals.get(i));
					
				}
				
				int maxDistance = 100000;
				int currentZone = -1;
				
				for(int i = 0 ; i < capitals.size ; i++) {
					if (distances[i] < maxDistance) {
						maxDistance = distances[i];
						currentZone = i;
					}
				}
				
				if (currentZone >= 0) {
					cell.zone = capitals.get(currentZone).zone;
				}
				
			}
		}		
	}
	
	private void putCapitalsToWorld() {
		capitals.clear();
		
		HexCell capitalCell = null;
		
		int margin = 1;
		
		for(int i = 0 ; i < zonesCount ; i++) {
			
			boolean flag = false;
			
			while (!flag) {
				
				int cellQ = MathUtils.random(cellsH);
				int cellR = MathUtils.random(cellsV);
				
				if (cellQ > margin && cellQ < (cellsH - margin) && cellR > margin && cellR < (cellsV - margin)) {
				
					capitalCell = cells[cellQ][cellR];
					
					int minDistance = 10000;

					for(int j = 0 ; j < capitals.size ; j++) {
						
						if (capitals.get(j).distanceTo(capitalCell) < minDistance) {
							minDistance = capitals.get(j).distanceTo(capitalCell);
						}
						
					}
					
					if (minDistance > 4) flag = true;
					
				}
				
			}
			
			
			capitalCell.zone = i + 1;
			
			capitals.add(capitalCell);
			
		}
		
	}
	
	private void createBlankWorld() {
		for(int q = 0 ; q < cellsH ; q++) {
			for(int r = 0 ; r < cellsV ; r++) {
				
				HexCell cell = new HexCell(q, r);
				cell.zone = 0;
				cells[q][r] = cell;
				
			}
		}	
	}
	
	public static HexCell getCapitalHex(int zone) {
		return capitals.get(zone - 1);
	}
	
	public static Entity getZoneEntity(int zone) {
		if (zone < 0) return null;
		return zones.get(zone - 1);
	}
	
	public static Entity getDiceEntity(int zoneId, int diceNum) {
		
		return dices[zoneId][diceNum];
		
	}
	
	public static int getZonesCount() {
		return zonesCount;
	}

	public static void decreaseZoneDices(int zoneId, int toDecrease) {
		
		ZoneComponent zone = EntityFactory.getZoneComponentById(zoneId);
		
		int start = 0;
		
		for(int i = toDecrease ; i < 12 ; i++) {
			
			dices[zoneId][start] = dices[zoneId][i];
			start += 1;
			
		}
		
		zone.setDices(zone.getDices() - toDecrease);
		
		
	}

	public static void setDiceEntity(int zoneId, int diceNum, Entity entity) {
		dices[zoneId][diceNum] = entity;		
	}

	public static void increaseZoneDices(int zoneId, int newDices) {
		
		EntityFactory.getZoneComponentById(zoneId).setDices(newDices);
		
	}
	
	public static int getSameZones(int zoneId, int country) {
		int res = 0;
		
		if (!tmpZones.contains(zoneId, true)) {
		
			tmpZones.add(zoneId);
			res += 1;
		}
		
		ZoneComponent zone = EntityFactory.getZoneComponentById(zoneId);
		
		for(int i = 0 ; i < zone.getNeigbors().size ; i++) {
			
			int nbZoneId = zone.getNeigbors().get(i);
			ZoneComponent nbZone = EntityFactory.getZoneComponentById(nbZoneId);
			
			if (nbZone.getCountry() != country) continue;
			
			
			
			if (!tmpZones.contains(nbZoneId, true)) {

				res += getSameZones(nbZoneId, country);				
			}
			
		}
		
		return res;
	}

	public static int getCountryAdjustedZonesCount(int country) {
		
		ZoneComponent zone;
		
		int maxTotalNbs = 0;
		
		for(int zoneId = 1 ; zoneId <= getZonesCount() ; zoneId++) {
			
			zone = EntityFactory.getZoneComponentById(zoneId);
			
			if (zone.getCountry() != country) continue;
			
			tmpZones.clear();
			
			int totalNbs = getSameZones(zoneId, country);
			
			if (totalNbs > maxTotalNbs) {
				maxTotalNbs = totalNbs;
			}
			
			Gdx.app.log("ZC", String.format("ZoneID: %d, adjustents: %d", zoneId, totalNbs));
			
			
		}
		
		return maxTotalNbs;
	}

	public static void distributeDicesToCountry(int toAdd, int country) {
		
		tmpZones.clear();
		
		for(int i = 1 ; i <= getZonesCount() ; i++) {
			
			if (EntityFactory.getZoneComponentById(i).getCountry() == country) {
				if (EntityFactory.getZoneComponentById(i).getDices() < 8) {
					tmpZones.add(i);
				}
				
			}
			
		}
		
		if (tmpZones.size == 0) return;
		
		for(int i = 0 ; i < toAdd ; i++) {
			
			int rndZone = MathUtils.random(0, tmpZones.size - 1);
			
			Gdx.app.log("IZ", String.format("Increase zone: %d", tmpZones.get(rndZone)));
			
			EntityFactory.increaseZoneDices(tmpZones.get(rndZone), 1);
			
		}
		
	}

}
