package pbartz.games.risk;

import pbartz.games.components.BlinkComponent;
import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.ColorInterpolationComponent;
import pbartz.games.components.DelayedComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.TextureComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.systems.BlinkSystem;
import pbartz.games.systems.ColorInterpolationSystem;
import pbartz.games.systems.TextureRenderingSystem;
import pbartz.games.systems.ZoneSelectionSystem;
import pbartz.games.utils.Command;
import pbartz.games.utils.Interpolation;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class EntityFactory {
	
	
	static PooledEngine engine = null;
	private static boolean isPaintInited;
	private static Color[] paints = new Color[300];
	private static Entity targetEntity;
	private static Entity srcEntity;
	private static int country;
	private static TextureComponent texture;
	private static Pixmap pixmap;
	private static int color;
	private static Entity tmpEntity;
	
	public static void init(PooledEngine engine) {
		
		EntityFactory.engine = engine;
		EntityFactory.isPaintInited = false;
		
	}
	
	public static PooledEngine getEngine() {
		return engine;
	}
	
	public static PositionComponent getEntityPositionComponent(Entity entity) {
		
		return engine.getSystem(TextureRenderingSystem.class).pm.get(entity);
		
	}
	
	public static Entity createTextureEntity(Texture texture, int pX, int pY) {
		
		TextureComponent t = ComponentFactory.getTextureComponent(engine, texture);
		PositionComponent p = ComponentFactory.getPositionComponent(engine, pX, pY);
		ShapeComponent s = ComponentFactory.getRectShapeComponent(engine, texture.getWidth(), texture.getHeight());
		
		Entity entity = engine.createEntity();
		
		entity.add(t);
		entity.add(p);
		entity.add(s);
		entity.add(ComponentFactory.getColorAlphaComponent(engine, 1f));
		
		engine.addEntity(entity);
		
		return entity;
	}
	
	public static void increaseZoneDices(int zoneId, int toAdd) {
		
		ZoneComponent zone = EntityFactory.getZoneComponentById(zoneId);
		
		int dices = zone.getDices();
		int newDices = Math.min(8, dices + toAdd);
		
		//Gdx.app.log("TAG", String.format("currDices: %d, toAdd: %d, newDices: %d", dices, toAdd, newDices));
		
		if (dices == newDices) return;
		
		
		
		Entity entity;
		
		for(int i = dices ; i < newDices ; i++) {

			entity = MapGenerator.getDiceEntity(zoneId, i);
			
			if (entity == null) {
				
				entity = EntityFactory.createTextureEntity(
					ResourceFactory.getTexture("dice.png"), 
					100,
					100
				);
				
				MapGenerator.setDiceEntity(zoneId, i, entity);
				
			}
			
			EntityFactory.getPositionComponent(entity).x = MathUtils.random(480);
			EntityFactory.getPositionComponent(entity).y = MathUtils.random(800);
			
			Vector2 diceXY = MapGenerator.getZoneDicePosition(zoneId, i);
			
			EntityFactory.setAlpha(entity, 0f);
			
			entity.add(ComponentFactory.getColorAlphaInterpolationComponent(engine, 0f, 1f, 0.5f, Interpolation.EASE_IN));
			
			entity.add(ComponentFactory.getPositionInterpolationComponent(
					engine, 
					EntityFactory.getPositionComponent(entity), 
					diceXY.x, 
					diceXY.y, 
					0.5f, 
					Interpolation.EASE_IN
			));
			
		}
		
		MapGenerator.increaseZoneDices(zoneId, newDices);
		
		
	}
	
	private static PositionComponent getPositionComponent(Entity entity) {
		
		return engine.getSystem(TextureRenderingSystem.class).pm.get(entity);
		
	}
	
	private static void setAlpha(Entity entity, float f) {
		
		ColorAlphaComponent alpha = engine.getSystem(TextureRenderingSystem.class).am.get(entity);
		
		if (alpha != null) {
			
			alpha.setAlpha(f);
			
		}
		
	}

	public static void decreaseZoneDices(int zoneId, int toRemove) {
		
		int i = zoneId;
		
		ZoneComponent zone = EntityFactory.getZoneComponentById(i);
		
		if (toRemove >= zone.getDices()) {
			toRemove = zone.getDices() - 1;
		}
		
		PositionComponent prevPosition = null; 
		
		for(int j = 0 ; j < zone.getDices() ; j++) {
			
			Entity entity = EntityFactory.getZoneDiceEntity(i, j);
			
			if (j < toRemove) {

				entity.add(ComponentFactory.getPositionInterpolationComponent(
						EntityFactory.getEngine(), 
						EntityFactory.getEntityPositionComponent(entity),
						MathUtils.random(480), 
						MathUtils.random(800), 
						MathUtils.random() * 2, 
						Interpolation.EASE_IN
				));
				
				entity.add(ComponentFactory.getColorAlphaInterpolationComponent(engine, 1f, 0, 0.5f, Interpolation.EASE_IN_OUT));
				
				continue;
				
			}
			
			prevPosition = EntityFactory.getEntityPositionComponent(EntityFactory.getZoneDiceEntity(i,  j - toRemove));
			
			EntityFactory.addDelayedComponent(entity, ComponentFactory.getPositionInterpolationComponent(
					EntityFactory.getEngine(), 
					EntityFactory.getEntityPositionComponent(entity),
					prevPosition.x, 
					prevPosition.y, 
					0.3f, 
					Interpolation.EASE_IN
			), 0.05f * j);
			
		}
		
		MapGenerator.decreaseZoneDices(i, toRemove);
		
	}
	
	public static Color getColorByZone(int zone) {
		
		if (!isPaintInited ) {
			
			paints[0] = new Color();
			paints[0].set(0, 0, 0, 0);
			
			paints[1] = new Color();
			paints[1].set(183f/255f, 209f/255f, 245f/255f, 1f);
			
			paints[2] = new Color();
			paints[2].set(117f / 255f, 101f / 255f, 169f / 255f, 1f);
			
			paints[3] = new Color();
			paints[3].set(55f / 255f, 48f / 255f, 107f / 255f, 1f);
			
			for(int i = 4 ; i < 300 ; i++) {
				
				paints[i] = new Color();
				paints[i].set(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f);
				
			}
			
			isPaintInited = true;
			
		}
		
		return paints[zone];
		
	}

	public static Entity getZoneEntityById(int zone) {
		
		return MapGenerator.getZoneEntity(zone);
		
	}
	
	public static ZoneComponent getZoneComponentById(int zone) {
		
		tmpEntity = getZoneEntityById(zone);
		
		return engine.getSystem(ZoneSelectionSystem.class).zm.get(tmpEntity);
		
	}
	
	public static Entity getZoneDiceEntity(int zoneId, int diceNum) {
		
		return MapGenerator.getDiceEntity(zoneId, diceNum);
		
	}
	
	public static void addCommand(Command cmd) {
		
		Entity entity = engine.createEntity();
		cmd.engine = engine;
		entity.add(ComponentFactory.getCommandComponent(engine, cmd));
		
		engine.addEntity(entity);	
		
	}
	
	public static void startBlinkZone(int zoneId, int syncZone) {
		
		Entity entity = EntityFactory.getZoneEntityById(zoneId);
		
		if (entity != null) {
			
			entity.add(ComponentFactory.getBlinkComponent(engine));
			
			Entity sync = EntityFactory.getZoneEntityById(syncZone);
			
			if (sync != null) {
				
				BlinkComponent srcBlink = engine.getSystem(BlinkSystem.class).bm.get(sync);
				
				if (srcBlink != null) {
				
					BlinkComponent targetBlink = engine.getSystem(BlinkSystem.class).bm.get(entity);
					
					targetBlink.setState(srcBlink.getState());
					targetBlink.setTimeToNextState(srcBlink.getTimeToNextState());
					
				}
				
				
			}
			
			
		}
	
	}

	public static void startBlinkZone(int zoneId) {
		EntityFactory.getZoneEntityById(zoneId).add(ComponentFactory.getBlinkComponent(engine));		
	}

	public static void createZoneSelector(int srcZone) {		

		Entity entity = engine.createEntity();
		
		entity.add(ComponentFactory.getZoneSelectionComponent(engine, srcZone));
		entity.add(ComponentFactory.getArrowComponent(engine, -100, -100, -100, -100));
		
		engine.addEntity(entity);
		
	}

	public static void finishBlinkZone(int targetZone) {
		if (targetZone <= 0) return;
		
		EntityFactory.getZoneEntityById(targetZone).remove(BlinkComponent.class);
		
		if (engine.getSystem(ColorInterpolationSystem.class).im.get(EntityFactory.getZoneEntityById(targetZone)) != null) {
		
			EntityFactory.getZoneEntityById(targetZone).remove(ColorInterpolationComponent.class);
			
		}
		
		engine.getSystem(
				TextureRenderingSystem.class
				).am.get(
						EntityFactory.getZoneEntityById(targetZone)
					).setAlpha(1f);
		
	}

	public static int getCountryByZoneId(int srcZone) {
		
		return engine.getSystem(ZoneSelectionSystem.class).zm.get(EntityFactory.getZoneEntityById(srcZone)).getCountry();
		
	}
	
	public static void addDelayedComponent(Entity dEntity, Component dComponent, float delay) {
		
		if (delay <= 0) {
			
			dEntity.add(dComponent);
			
		} else {
			
			DelayedComponent component = engine.createComponent(DelayedComponent.class);
			Entity entity = engine.createEntity();
			
			component.init(dEntity, dComponent, delay);
			
			entity.add(component);
			
			engine.addEntity(entity);
			
		}
		
	}
	
	public static void turnZoneToCountry(int targetZone, int srcZone) {
		if (targetZone <= 0 || srcZone <= 0) return;
		
		targetEntity = EntityFactory.getZoneEntityById(targetZone);
		srcEntity = EntityFactory.getZoneEntityById(srcZone);
		
		country = engine.getSystem(ZoneSelectionSystem.class).zm.get(srcEntity).getCountry();
		
		texture = engine.getSystem(TextureRenderingSystem.class).tm.get(targetEntity);
		pixmap = texture.getPixmap();
		
		pixmap.setColor(EntityFactory.getColorByZone(country));
		
		engine.getSystem(ZoneSelectionSystem.class).zm.get(targetEntity).setCountry(country);
		
		for(int x = 0  ; x < pixmap.getWidth() ; x++) {
			for(int y = 0 ; y < pixmap.getHeight() ; y++) {

				color = pixmap.getPixel(x, y);
				
				if (((color & 0x000000ff)) == 255) {
					
					if( ( ((color & 0xff000000) >>> 24) + ((color & 0x00ff0000) >>> 16) + ((color & 0x0000ff00) >>> 8)  ) != 0){
					
						pixmap.drawPixel(x, y);
						
					}
					
				}
				
			}
		}
		
		texture.getTexture().draw(pixmap, 0, 0);
		
		
	}

	public static void applyPositionToEntity(Entity to, Entity from) {
		
		PositionComponent posTo = EntityFactory.getPositionComponent(to);
		PositionComponent posFrom = EntityFactory.getPositionComponent(from);
		
		posTo.setPosition(posFrom.x, posFrom.y);
		
	}

	public static void disposeEntity(Entity entity) {
		
		engine.removeEntity(entity);
		
	}

}
