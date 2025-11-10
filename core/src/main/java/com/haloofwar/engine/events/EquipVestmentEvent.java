package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.VestmentType;

public class EquipVestmentEvent {
	public final VestmentType vestment;

	public EquipVestmentEvent(VestmentType vestment) {
		this.vestment = vestment;
	}
}
