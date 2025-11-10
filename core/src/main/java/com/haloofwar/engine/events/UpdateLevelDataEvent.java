package com.haloofwar.engine.events;

public class UpdateLevelDataEvent {
	public final int enemiesDefeated;
	public final int wavesPassed;
	
	public UpdateLevelDataEvent(int enemiesDefeated, int wavesPassed) {
		this.enemiesDefeated = enemiesDefeated;
		this.wavesPassed = wavesPassed;
	}
}
