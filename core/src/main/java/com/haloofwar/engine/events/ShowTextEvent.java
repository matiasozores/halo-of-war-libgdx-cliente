package com.haloofwar.engine.events;

public class ShowTextEvent {
	public final String message;
	public final int duration;

	public ShowTextEvent(String message, final int duration) {
		this.message = message;
		this.duration = duration;
	}
}

