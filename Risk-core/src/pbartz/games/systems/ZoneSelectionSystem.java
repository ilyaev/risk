package pbartz.games.systems;

import pbartz.games.components.ArrowComponent;
import pbartz.games.components.ZoneComponent;
import pbartz.games.components.ZoneSelectionComponent;
import pbartz.games.risk.EntityFactory;
import pbartz.games.risk.GameInputProcessor;
import pbartz.games.risk.MapGenerator;
import pbartz.games.risk.commands.FinishZoneSelectionCommand;
import pbartz.games.utils.Command;
import pbartz.games.utils.EventBus;
import pbartz.games.utils.HexCell;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;

public class ZoneSelectionSystem extends DynamicIteratingSystem {
	
	private ComponentMapper<ZoneSelectionComponent> sm = ComponentMapper.getFor(ZoneSelectionComponent.class);
	private ComponentMapper<ArrowComponent> am = ComponentMapper.getFor(ArrowComponent.class);
	public ComponentMapper<ZoneComponent> zm = ComponentMapper.getFor(ZoneComponent.class);

	private ArrowComponent arrow;
	private ZoneSelectionComponent selection;
	private Entity selectionEntity;
	
	@SuppressWarnings("unchecked")
	public ZoneSelectionSystem() {
		super(Family.getFor(ZoneSelectionComponent.class, ArrowComponent.class));
	}

	public ZoneSelectionComponent getCurrentSelectionComponent() {
		return selection;
	}
	
	public Entity getCurrentSelectionEntity() {
		return selectionEntity;
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		arrow = am.get(entity);
		
		selection = sm.get(entity);
		
		selectionEntity = entity;
		
		HexCell srcHex = MapGenerator.getCapitalHex(selection.getSrcZone());
		
		float srcPointX = srcHex.getCoordX(MapGenerator.cSize);
		float srcPointY = srcHex.getCoordY(MapGenerator.cSize);
		
		int zoneHover = EventBus.popInt("zone_hover");
		
		int targetPointX = EventBus.popInt("hover_x");
		int targetPointY = EventBus.popInt("hover_y");		
		
		if (zoneHover > 0) {
		
			Gdx.app.log("ZSL", String.format("zone_hover: %d, hover_x: %d, hover_y: %d", zoneHover, targetPointX, targetPointY));
			
		}
		
		
		if (zoneHover > 0 && selection.getTargetZone() != zoneHover) {
			
			ZoneComponent srcZone = zm.get(EntityFactory.getZoneEntityById(selection.getSrcZone()));
			
			if (srcZone.getNeigbors().indexOf(zoneHover, true) != -1) {
			
				if (selection.getTargetZone() > 0) {

					EntityFactory.finishBlinkZone(selection.getTargetZone());
					
				}
				
				ZoneComponent hoverZone = zm.get(EntityFactory.getZoneEntityById(zoneHover));
				
				if (srcZone.getCountry() != hoverZone.getCountry()) {
				
					selection.setTargetZone(zoneHover); 				
					EntityFactory.startBlinkZone(selection.getTargetZone(), selection.getSrcZone());
					
				} else {
					
					selection.setTargetZone(-1);
					
				}
				
				
			} else {
				EntityFactory.finishBlinkZone(selection.getTargetZone()); 
				selection.setTargetZone(-1);
			}
			
		}

		// recalculate dimensions based on src/target
		
		if (targetPointX > 0) {
		
			//Gdx.app.log("D", String.format("%d,  %d", targetPointX, targetPointY));
			
			arrow.setStartX((int)srcPointX);
			arrow.setStartY((int)srcPointY);
			
			arrow.setEndX(targetPointX);
			arrow.setEndY(targetPointY);
			
		}
		
		if (GameInputProcessor.isTouchUp) {
			
			EntityFactory.addCommand(new FinishZoneSelectionCommand(selection, entity));				
			
			GameInputProcessor.clearTouch();
			
		}
		
	}

}
