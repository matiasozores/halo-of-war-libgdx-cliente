package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.PlayerType;

public class SuccessfulAttackEvent {
	public final PlayerType type;
	public final boolean isPressed;
	
	public SuccessfulAttackEvent(PlayerType type, boolean isPressed) {
		this.type = type;
		this.isPressed = isPressed;
	}
	
}
