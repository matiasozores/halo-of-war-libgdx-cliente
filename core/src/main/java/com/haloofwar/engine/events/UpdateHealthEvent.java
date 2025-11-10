package com.haloofwar.engine.events;

public class UpdateHealthEvent {
	public final int identifier;
	public final int currentHealth;
	public final int currentShield;
	public final float visibilityTime;
	
	public UpdateHealthEvent(int identifier, final int currentHealth, final int currentShield, final float visibilityTime) {
		this.identifier = identifier;
		this.currentHealth = currentHealth;
		this.currentShield = currentShield;
		this.visibilityTime = visibilityTime;
	}
}
