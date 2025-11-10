package com.haloofwar.game.components;

import com.haloofwar.common.enumerators.VestmentType;

public class VestmentComponent implements Component {
	public final VestmentType type;
	public boolean unlocked = false;
	
	public VestmentComponent(VestmentType type) {
		this.type = type;
	}
}
