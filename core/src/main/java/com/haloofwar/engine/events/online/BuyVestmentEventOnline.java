package com.haloofwar.engine.events.online;

import com.haloofwar.common.enumerators.VestmentType;

public class BuyVestmentEventOnline {
	public final VestmentType vestmentType;
	
	public BuyVestmentEventOnline(VestmentType vestmentType) {
		this.vestmentType = vestmentType;
	}
}
