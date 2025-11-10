package com.haloofwar.game.factories;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.NPCType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.config.ComponentPresets;

public final class NPCFactory {

    private final TextureManager texture;

    public NPCFactory(GameContext context) {
        this.texture = context.getTexture();
    }

    public Entity create(final int IDENTIFIER, final NPCType TYPE, final float X, final float Y) {
    	final Entity entity = new Entity();
    	entity.addComponent(ComponentPresets.createTransform(IDENTIFIER, X, Y));
    	entity.addComponent(ComponentPresets.createBasicAnimation(TYPE, this.texture));
    	entity.addComponent(ComponentPresets.createHealth());
    	entity.addComponent(ComponentPresets.createName(TYPE));
    	entity.addComponent(ComponentPresets.createVillager());
    	entity.addComponent(ComponentPresets.createDialogue(TYPE, this.texture.get(TYPE.getHead())));
    	
    	return entity;
    }
}

