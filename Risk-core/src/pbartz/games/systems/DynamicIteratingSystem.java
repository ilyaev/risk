package pbartz.games.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public abstract class DynamicIteratingSystem extends EntitySystem {
	/** The family describing this systems entities */
	private Family family;
	/** The entities used by this system */
	private ImmutableArray<Entity> entities;
	
	/**
	 * Instantiates a system that will iterate over the entities described by the Family.
	 * @param family The family of entities iterated over in this System
	 */
	public DynamicIteratingSystem(Family family){
		this(family, 0);
	}
	
	/**
	 * Instantiates a system that will iterate over the entities described by the Family, with a 
	 * specific priority.
	 * @param family The family of entities iterated over in this System
	 * @param priority The priority to execute this system with (lower means higher priority)
	 */
	public DynamicIteratingSystem(Family family, int priority){
		super(priority);
		
		this.family = family;
	}
		
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		entities = null;
	}

	@Override
	public void update(float deltaTime) {		
		for (int i = 0; i < entities.size(); ++i) {
			processEntity(entities.get(i), deltaTime);
		}
	}

	/**
	 * This method is called on every entity on every update call of the EntitySystem. Override this to implement
	 * your system's specific processing.
	 * @param entity The current Entity being processed
	 * @param deltaTime The delta time between the last and current frame
	 */
	public abstract void processEntity(Entity entity, float deltaTime);
}
