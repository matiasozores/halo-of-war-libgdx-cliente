package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.PlayerType;

public class AttackEventOnline {
	
    public final boolean pressed;
    public final PlayerType type;
    public final int x, y;

    public AttackEventOnline(PlayerType type, boolean pressed, final int x, final int y) {
        this.pressed = pressed;
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
