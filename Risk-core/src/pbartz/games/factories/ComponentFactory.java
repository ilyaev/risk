package pbartz.games.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import pbartz.games.components.ArrowComponent;
import pbartz.games.components.BlinkComponent;
import pbartz.games.components.ColorAlphaComponent;
import pbartz.games.components.ColorComponent;
import pbartz.games.components.ColorInterpolationComponent;
import pbartz.games.components.CommandComponent;
import pbartz.games.components.PositionComponent;
import pbartz.games.components.PositionInterpolationComponent;
import pbartz.games.components.ShapeComponent;
import pbartz.games.components.TextureComponent;
import pbartz.games.components.TouchComponent;
import pbartz.games.components.ZoneSelectionComponent;
import pbartz.games.utils.Command;

public class ComponentFactory {
	
	public static ColorComponent getColorComponent(PooledEngine engine, float r, float g, float b, float a) {
		
		ColorComponent color = engine.createComponent(ColorComponent.class);
		
		color.init(r, g, b, a);
		
		return color;
		
	}
	
	public static PositionInterpolationComponent getPositionInterpolationComponent(PooledEngine engine, PositionComponent position, float newX, float newY, float speed, int easing) {
		
		PositionInterpolationComponent interpolation = engine.createComponent(PositionInterpolationComponent.class);
		interpolation.init(position, newX, newY, speed, easing);
		
		return interpolation;
		
	}
	
	public static PositionComponent getPositionComponent(PooledEngine engine, int pX, int pY) {
		
		PositionComponent position = engine.createComponent(PositionComponent.class);
		position.init(pX, pY);
		
		return position;
		
	}
	
	public static ShapeComponent getCircleShapeComponent(PooledEngine engine, float cR) {
		
		ShapeComponent shape = engine.createComponent(ShapeComponent.class);
		shape.init(ShapeComponent.SHAPE_CIRCLE, cR);
		
		return shape;
		
	}


	public static ColorInterpolationComponent getColorInterpolationComponent(PooledEngine engine, Color oldPaint, Color endPaint, float speed, int type) {
		
		ColorInterpolationComponent interpolation = engine.createComponent(ColorInterpolationComponent.class);
		interpolation.init(oldPaint, endPaint, speed, type);
		
		return interpolation;
		
	}

	public static ShapeComponent getRectShapeComponent(PooledEngine engine, float cellWidth, float cellHeight) {
		ShapeComponent shape = engine.createComponent(ShapeComponent.class);
		shape.init(ShapeComponent.SHAPE_RECTANGLE, cellWidth, cellHeight);
		
		return shape;
	}

	public static TextureComponent getTextureComponent(PooledEngine engine, Texture textureObj) {
		
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.init(textureObj);

		return texture;
	}
	
	public static TouchComponent getTouchComponent(PooledEngine engine) {
		
		TouchComponent touch = engine.createComponent(TouchComponent.class);
		
		return touch;
		
	}
	
	public static ColorAlphaComponent getColorAlphaComponent(PooledEngine engine, float cAlpha) {
		
		ColorAlphaComponent alpha = engine.createComponent(ColorAlphaComponent.class);
		alpha.init(cAlpha);
		
		return alpha;
		
	}

	public static ColorInterpolationComponent getColorAlphaInterpolationComponent(PooledEngine engine, float alphaStart, float alphaEnd, float speed, int type) {
		
		ColorInterpolationComponent interpolation = engine.createComponent(ColorInterpolationComponent.class);
		
		Color oldPaint = new Color(1f, 1f, 1f, alphaStart);
		Color endPaint = new Color(1f, 1f, 1f, alphaEnd);
		
		interpolation.init(oldPaint, endPaint, speed, type);
		
		return interpolation;
	}
	
	public static BlinkComponent getBlinkComponent(PooledEngine engine) {
		
		return engine.createComponent(BlinkComponent.class);
		
	}

	public static ArrowComponent getArrowComponent(PooledEngine engine, int startX, int startY, int endX, int endY) {
		
		ArrowComponent arrow = engine.createComponent(ArrowComponent.class);
		arrow.init(startX, startY, endX, endY);
		return arrow;
		
	}
	
	public static ZoneSelectionComponent getZoneSelectionComponent(PooledEngine engine, int srcZone) {
		
		ZoneSelectionComponent selection = engine.createComponent(ZoneSelectionComponent.class);
		
		selection.init(srcZone);
		
		return selection;
	}

	public static CommandComponent getCommandComponent(PooledEngine engine, Command cmd) {

		CommandComponent command = engine.createComponent(CommandComponent.class);
		command.setCmd(cmd);
		
		return command;
	}
	

}
