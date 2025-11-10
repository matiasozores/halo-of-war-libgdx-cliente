package com.haloofwar.engine.events;

public class AttackEvent {
	public final boolean pressed;
	public final int x, y;
	
	public AttackEvent(boolean pressed, final int x, final int y) {
		this.pressed = pressed;
		this.x = x;
		this.y = y;
	}
}
