package com.haloofwar.game.factories;

import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PowerUpType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.config.ComponentPresets;

public final class ObjectFactory {
    private static final float WIDTH = 16, HEIGHT = 16;

    public static Entity createItemByName(final int IDENTIFIER, final String NAME, final TextureManager texture) {
    	final ObjectType TYPE = ObjectType.getByName(NAME);
    	
    	if(TYPE == null) {
    		System.out.println("No se puede crear el objeto porque el nombre que se ingreso no coincide... | ObjectFactory");
    		return null;
    	}
    	
    	return createItem(IDENTIFIER, new Rectangle(0,0,0,0), TYPE, texture);
    }
    
    public static Entity createItem(final int IDENTIFIER, final Rectangle rectangle, final ObjectType TYPE, final TextureManager texture) {
    	final Entity entity = new Entity();
    	entity.addComponent(ComponentPresets.createTransform(IDENTIFIER, rectangle.x, rectangle.y, WIDTH, HEIGHT));
    	entity.addComponent(ComponentPresets.createName(TYPE));
    	entity.addComponent(ComponentPresets.createRender(TYPE, texture));
    	entity.addComponent(ComponentPresets.createPickup(TYPE));
    	
    	return entity;
    }

    public static Entity createPowerUp(final int IDENTIFIER, final Rectangle rectangle, final PowerUpType TYPE, final TextureManager texture) {
    	final Entity entity = new Entity();
    	entity.addComponent(ComponentPresets.createTransform(IDENTIFIER, rectangle.x, rectangle.y, WIDTH, HEIGHT));
    	entity.addComponent(ComponentPresets.createName(TYPE));
    	entity.addComponent(ComponentPresets.createRender(TYPE, texture));
    	entity.addComponent(ComponentPresets.createShield());
       	entity.addComponent(ComponentPresets.createPowerUp(TYPE));
    	
    	return entity;
    }
}