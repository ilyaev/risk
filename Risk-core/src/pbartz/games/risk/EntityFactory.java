package pbartz.games.risk;

import pbartz.games.components.BlinkComponent;
import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.ColorComponent;
import pbartz.games.components.ColorInterpolationComponent;
import pbartz.games.components.DelayedComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.PositionInterpolationComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.TextureComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.components.ZoneSelectionComponent;
import pbartz.games.components.UI.ButtonComponent;
import pbartz.games.components.UI.LabelComponent;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.systems.BlinkSystem;
import pbartz.games.systems.ColorInterpolationSystem;
import pbartz.games.systems.TextureRenderingSystem;
import pbartz.games.systems.UIButtonSystem;
import pbartz.games.systems.UILabelSystem;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

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
	private static Stage stage;
	private static Skin skin;
	public static ZoneSelectionComponent currentZoneSelectionComponent;
	public static Entity currentZoneSelectionEntity;
	private static ColorPalletes palleteMap;
	
	public static void init(PooledEngine engine) {
		
		EntityFactory.engine = engine;
		EntityFactory.isPaintInited = false;
		
		Json json = new Json();
		
		palleteMap = json.fromJson(ColorPalletes.class, Gdx.files.internal("colors.json"));
	
	}
	
	public static void initPallete() {
		
			int colorId = MathUtils.random(palleteMap.colors.size() - 1);
			
			String hash = palleteMap.colors.get(colorId).c;
			String[] colors = hash.split("\\|");
			
			paints[0] = new Color();
			paints[0].set(0, 0, 0, 0);
			
			for(int i = 0 ; i < colors.length ; i++) {
				
				paints[i + 1] = new Color();
				
				String[] components = colors[i].split(",");
				int r = Integer.parseInt(components[0]);
				int g = Integer.parseInt(components[1]);
				int b = Integer.parseInt(components[2]);
				
				paints[i + 1].set(r/255f, g/255f, b/255f, 1f);
				
			}
			
		
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
			
			EntityFactory.getPositionComponent(entity).x = MathUtils.random(Gdx.graphics.getWidth());
			EntityFactory.getPositionComponent(entity).y = MathUtils.random(Gdx.graphics.getHeight());
			
			Vector2 diceXY = MapGenerator.getZoneDicePosition(zoneId, i);
			
			EntityFactory.setAlpha(entity, 0f);
			
			entity.add(ComponentFactory.getColorAlphaInterpolationComponent(engine, 0f, 1f, 0.5f, Interpolation.EASE_IN));
			
			PositionInterpolationComponent pInterpolation = ComponentFactory.getPositionInterpolationComponent(
					engine, 
					EntityFactory.getPositionComponent(entity), 
					diceXY.x, 
					diceXY.y, 
					0.5f, 
					Interpolation.EASE_IN
			);
			
			EntityFactory.addDelayedComponent(entity, pInterpolation, MathUtils.random() * 0.2f);
			
			//entity.add(pInterpolation);
			
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
	
	public static void addCommand(Command cmd, float delay) {
		
		Entity entity = engine.createEntity();
		cmd.engine = engine;
		entity.add(ComponentFactory.getCommandComponent(engine, cmd));
		
		EntityFactory.addDelayedComponent(entity, null, delay);
		
	}
	
	public static void addCommand(Command cmd, boolean execute) {
		
		cmd.execute();
		
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
		
		currentZoneSelectionComponent = ComponentFactory.getZoneSelectionComponent(engine, srcZone);
		
		entity.add(currentZoneSelectionComponent);
		entity.add(ComponentFactory.getArrowComponent(engine, -100, -100, -100, -100));
		
		engine.addEntity(entity);
		
		currentZoneSelectionEntity = entity;
				
		
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
	
	public static Entity createUILabel(String caption, float x, float y, float r, float g, float b, float a, String tag) {
		
		Entity entity = engine.createEntity();
		
		ColorComponent color = ComponentFactory.getColorComponent(engine, r, g, b, a);
		PositionComponent position = ComponentFactory.getPositionComponent(engine, x, y);
		LabelComponent label = engine.createComponent(LabelComponent.class);
		
		label.init(caption, tag, skin);
		
		entity.add(position).add(color).add(label);
		engine.getSystem(UILabelSystem.class).addLabel(entity, tag);
		
		engine.addEntity(entity);		
		
		stage.addActor(label.getLabel());
		
		return entity;
		
	}
	
	public static Entity createUIButton(String label, float x, float y, float width, float height, String tag) {
		
		Entity entity = engine.createEntity();
		
		PositionComponent position = engine.createComponent(PositionComponent.class);
		ButtonComponent button = engine.createComponent(ButtonComponent.class);
		ShapeComponent shape = engine.createComponent(ShapeComponent.class);
		
		position.init(x, y);
		shape.init(ShapeComponent.SHAPE_RECTANGLE, width, height);		
		button.init(label, tag, skin);
		
		entity.add(position).add(shape).add(button);
		
		engine.addEntity(entity);
		
		stage.addActor(button.getButton());		
		
		engine.getSystem(UIButtonSystem.class).addButton(entity, tag);
		
		return entity;
		
	}
	
	public static Entity getUIButton(String tag) {
		return engine.getSystem(UIButtonSystem.class).getButton(tag);
	}

	public static void setStage(Stage stage) {
		EntityFactory.stage = stage;		
	}

	public static void setSkin(Skin skin) {
		EntityFactory.skin = skin;		
	}
	
	public static Skin getSkin() {
		return EntityFactory.skin;
	}
	

}
