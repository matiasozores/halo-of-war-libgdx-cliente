package com.haloofwar.game.systems;

import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.interfaces.Disposable;

public abstract class EventSystem implements Disposable {
	protected final EventListenerManager listenerManager = new EventListenerManager();
    protected EventBus bus;
    
	public EventSystem(EventBus bus) {
		this.bus = bus;
	}
	
	@Override
	public void dispose() {
		this.listenerManager.clear();
	}
}
