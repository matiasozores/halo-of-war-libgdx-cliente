package com.haloofwar.game.systems;

import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;

public abstract class EventEntitySystem extends EntitySystem {
	protected final EventListenerManager listenerManager = new EventListenerManager();
    protected EventBus bus;
	
	public EventEntitySystem(EventBus bus) {
		this.bus = bus;
	}
	
	@Override
	public void dispose() {
		super.dispose(); 
		this.listenerManager.clear();
	}
}
