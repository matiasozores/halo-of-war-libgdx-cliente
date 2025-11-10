package com.haloofwar.game.factories;

import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.enumerators.AnimatedObject;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.config.ComponentPresets;

public final class PortalFactory {
    public static Entity create(final Rectangle rectangle, final TextureManager texture, final String TELEPORTATION_TARGET, final boolean LAST_STATE, AnimatedObject animation) {
        final Entity entity = new Entity();
        
        entity.addComponent(ComponentPresets.createTransform(-1, rectangle.x, rectangle.y, 64, 64));
        entity.addComponent(ComponentPresets.createPortal(TELEPORTATION_TARGET));
        entity.addComponent(ComponentPresets.createObjectAnimation(animation, texture, LAST_STATE));
        
        return entity;
    }
}
