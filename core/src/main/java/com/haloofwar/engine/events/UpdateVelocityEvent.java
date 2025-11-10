package com.haloofwar.engine.events;

public class UpdateVelocityEvent {
	public final int IDENTIFIER;
	public final float CURRENT_SPEED;
	public final float CURRENT_DURATION;
	
	public UpdateVelocityEvent(int IDENTIFIER, float CURRENT_SPEED, float CURRENT_DURATION) {
		this.IDENTIFIER = IDENTIFIER;
		this.CURRENT_SPEED = CURRENT_SPEED;
		this.CURRENT_DURATION = CURRENT_DURATION;
	}
}
