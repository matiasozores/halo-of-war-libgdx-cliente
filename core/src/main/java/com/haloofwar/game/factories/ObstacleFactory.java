package com.haloofwar.game.factories;

import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.config.ComponentPresets;

public final class ObstacleFactory {

    public static Entity createObstacle(final Rectangle RECTANGLE) {
    	final Entity entity = new Entity();
    	entity.addComponent(ComponentPresets.createTransform(-1, RECTANGLE.x, RECTANGLE.y, RECTANGLE.width, RECTANGLE.height));    	
        return entity;
    }
}


