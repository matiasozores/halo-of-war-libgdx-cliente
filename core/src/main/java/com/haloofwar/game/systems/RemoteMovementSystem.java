package com.haloofwar.game.systems;

import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.online.RemotePositionUpdateEventOnline;
import com.haloofwar.game.components.MovementComponent;
import com.haloofwar.game.components.TransformComponent;

public class RemoteMovementSystem extends EventEntitySystem {
	
    public RemoteMovementSystem(EventBus bus) {
    	super(bus);
        this.listenerManager.add(bus, RemotePositionUpdateEventOnline.class, this::onRemotePositionUpdate);
    }

    @Override
    public void register(Entity e) {
        if (e.hasComponent(TransformComponent.class) && e.hasComponent(MovementComponent.class)) {
            super.register(e);
        }
    }

    private void onRemotePositionUpdate(RemotePositionUpdateEventOnline event) {
        Entity entity = this.getEntityByIdentifier(event.identifier);
        
        if(entity == null) {
        	return;
        }
        
        TransformComponent transform = entity.getComponent(TransformComponent.class);
        transform.x = event.x;
        transform.y = event.y;
        
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        movement.controller.setDirection(event.dirX, event.dirY);
    }
    
    private Entity getEntityByIdentifier(final int IDENTIFIER) {
    	boolean found = false;
    	int i = 0;
    	Entity entity = null;
    	
    	while(!found && i < this.entities.size()) {
    		Entity current = this.entities.get(i);
    		
    		TransformComponent tc = current.getComponent(TransformComponent.class);
    		if(tc.identifier == IDENTIFIER) {
    			found = true;
    			entity = current;
    		} else {
    			i++;
    		}
    	}
    	
    	return entity;
    }
    
    @Override
    public void dispose() {
    	for (Entity entity : this.entities) {
			final MovementComponent mc = entity.getComponent(MovementComponent.class);
			mc.dispose();
		}
    	
    	super.dispose();
    }
}
