package com.haloofwar.game.factories;

import com.haloofwar.common.enumerators.VestmentType;
import com.haloofwar.engine.Entity;

public class VestmentFactory {
    public static Entity create(VestmentType type) {
        final Entity entity = new Entity();
        entity.addComponent(type.getComponent());
        return entity;
    }
}
