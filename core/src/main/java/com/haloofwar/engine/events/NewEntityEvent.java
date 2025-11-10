package com.haloofwar.engine.events;

import com.haloofwar.engine.Entity;

public class NewEntityEvent {
	public final Entity entity;

	public NewEntityEvent(Entity entity) {
		this.entity = entity;
	}
}
