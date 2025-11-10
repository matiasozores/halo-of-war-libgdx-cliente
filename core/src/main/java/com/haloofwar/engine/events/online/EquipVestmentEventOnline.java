package com.haloofwar.engine.events.online;

import com.haloofwar.common.enumerators.VestmentType;

public class EquipVestmentEventOnline {
	public final VestmentType vestmentType;
	
	public EquipVestmentEventOnline(VestmentType vestmentType) {
		this.vestmentType = vestmentType;
	}
}
