package com.haloofwar.game.components;

import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.CanMoveEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.online.EntityMoveEventOnline;
import com.haloofwar.engine.events.online.MoveEventOnline;
import com.haloofwar.interfaces.MovementController;

public class RemotePlayerMovementController implements MovementController{
	
    private float dx = 0;
    private float dy = 0;
	private final int IDENTIFIER;
    private final EventBus bus;
    private final EventListenerManager listeners = new EventListenerManager();
    private boolean canMove = true;
	
    public RemotePlayerMovementController(final int IDENTIFIER, EventBus bus) {
    	this.IDENTIFIER = IDENTIFIER;
    	this.bus = bus;
    	this.listeners.add(bus, EntityMoveEventOnline.class, this::onMoveEvent);
    	this.listeners.add(bus, CanMoveEvent.class, this::onCanMove);
    }
    
    private void onCanMove(CanMoveEvent event) {
    	this.canMove = event.move;
    }
    
    private void onMoveEvent(EntityMoveEventOnline event) {    	
    	if(!this.canMove) {
    		return;
    	}
    	
    	this.bus.publish(new MoveEventOnline(this.IDENTIFIER, event.getDirection(), event.isPressed()));
    }

    @Override
    public float getDirectionX() {
        return this.dx;
    }

    @Override
    public float getDirectionY() {
        return this.dy;
    }

	@Override
	public void changeTarget(Entity entity) {
		
	}

	@Override
	public void setDirection(float x, float y) {
		this.dx = x;
		this.dy = y;
	}

	@Override
	public void dispose() {
		this.listeners.clear();
	}
}
