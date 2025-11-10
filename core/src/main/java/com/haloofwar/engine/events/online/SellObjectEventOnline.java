package com.haloofwar.engine.events.online;

import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PlayerType;

public class SellObjectEventOnline {
	public final PlayerType player;
	public final ObjectType object;

	public SellObjectEventOnline(final PlayerType player, final ObjectType object) {
		this.player = player;
		this.object = object;
	}
}
