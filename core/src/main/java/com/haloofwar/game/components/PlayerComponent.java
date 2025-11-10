package com.haloofwar.game.components;

import com.haloofwar.common.enumerators.PlayerType;

public class PlayerComponent implements Component{
	public PlayerType type;
	public boolean isAttacking = false;
	
	public PlayerComponent(PlayerType type) {
		this.type = type;
	}
}
