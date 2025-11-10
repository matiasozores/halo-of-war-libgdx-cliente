package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.VestmentType;

public class BuyVestmentEvent {
	public final VestmentType vestment;

	public BuyVestmentEvent(VestmentType vestment) {
		this.vestment = vestment;
	}
}
