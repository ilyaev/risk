package pbartz.games.systems;

import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.TextureComponent;
import pbartz.games.components.TouchComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.factories.CommandFactory;
import pbartz.games.factories.ComponentFactory;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.GameInputProcessor;
import pbartz.games.risk.MapGenerator;
import pbartz.games.risk.commands.MoveZoneSelectionCommand;
import pbartz.games.risk.commands.StartZoneSelectionCommand;
import pbartz.games.utils.Command;
import pbartz.games.utils.EventBus;
import pbartz.games.utils.Interpolation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class ZoneTouchSystem extends DynamicIteratingSystem {

	private ComponentMapper<TouchComponent> tm = ComponentMapper.getFor(TouchComponent.class);
	private ComponentMapper<ZoneComponent> zm = ComponentMapper.getFor(ZoneComponent.class);
	private ComponentMapper<TextureComponent> tem = ComponentMapper.getFor(TextureComponent.class);
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<ColorAlphaComponent> am = ComponentMapper.getFor(ColorAlphaComponent.class);
	
	private ZoneComponent zone;
	private TouchComponent touch;
	private TextureComponent texture;
	private PositionComponent position;
	private Color color;
	private ColorAlphaComponent alpha;
	
	private Rectangle rect;
	
	@SuppressWarnings("unchecked")
	public ZoneTouchSystem(PooledEngine engine) {
		super(Family.getFor(ZoneComponent.class, TouchComponent.class));
		this.color = new Color();
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		if (!GameInputProcessor.isTouched()) return;
		
		if (GameFlowSystem.state != GameFlowSystem.WAITING_FOR_SELECT) {
			return;
		}

		
		zone = zm.get(entity);
		touch = tm.get(entity);
		texture = tem.get(entity);
		position = pm.get(entity);
		alpha = am.get(entity);
		
		rect = texture.getRect();
		rect.setCenter(position.x, position.y);

		if (GameInputProcessor.isTouched() && isIntersect() && isZoneHit()) {	
			
			if (GameInputProcessor.isTouchDown) {

				Command cmd = CommandFactory.createCommand(StartZoneSelectionCommand.class).init(
					zone.getCountry(), 
					zone.getId()
				);				
				
				EntityFactory.addCommand(cmd);
				
				GameInputProcessor.clearTouch();	
				
			} else if (GameInputProcessor.isTouchMove) {
				
				MoveZoneSelectionCommand cmd = CommandFactory.createCommand(MoveZoneSelectionCommand.class);
				cmd.init(
					zone.getId(), 
					GameInputProcessor.screenX, 
					GameInputProcessor.screenY
				);
				
				EntityFactory.addCommand(cmd);

				GameInputProcessor.clearTouch();
			}
			
		}


	}
	
	public int getZoneIdByCoords(int x, int y) {
		
		int oldX = GameInputProcessor.screenX;
		int oldY = GameInputProcessor.screenY;
		int res = 0;
		
		GameInputProcessor.screenX = x;
		GameInputProcessor.screenY = y;
		
		for(int zoneId = 1 ; zoneId <= MapGenerator.getZonesCount() ; zoneId ++) {
			
			Entity entity = MapGenerator.getZoneEntity(zoneId);
			
			zone = zm.get(entity);
			touch = tm.get(entity);
			texture = tem.get(entity);
			position = pm.get(entity);
			alpha = am.get(entity);
			
			rect = texture.getRect();
			rect.setCenter(position.x, position.y);
			
			if (isIntersect() && isZoneHit()) {
				res = zoneId;
				break;
			}
			
		}
		
		GameInputProcessor.screenX = oldX;
		GameInputProcessor.screenY = oldY;
		
		return res;
	}
	
	private boolean isZoneHit() {
		
		color.set(texture.getPixmap().getPixel(
			(int)(GameInputProcessor.screenX - rect.getX()), 
			(int)(GameInputProcessor.screenY - rect.getY())
		));

		return (color.a == 1f);
	}
	
	public Boolean isIntersect() {
		
		
		return rect.contains(
			GameInputProcessor.screenX, 
			GameInputProcessor.screenY
		);
		
	}

}
