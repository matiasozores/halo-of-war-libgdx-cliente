package com.haloofwar.game.factories;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.cameras.GameWorldCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.components.PlayerComponent;
import com.haloofwar.game.config.ComponentPresets;

public final class PlayerFactory {

    private final TextureManager texture;
    private final EventBus gameplayBus;
    private final GameWorldCamera worldCamera;
    
    public PlayerFactory(final GameContext context) {
        this.texture = context.getTexture();
        this.gameplayBus = context.getGameplay().getBus();
        this.worldCamera = context.getWorldCamera();
    }

    public Entity create(final int IDENTIFIER, final PlayerType TYPE, final float X, final float Y, final boolean IS_REMOTE) {   
    	final Entity ENTITY = new Entity();
    	ENTITY.addComponent(ComponentPresets.createTransform(IDENTIFIER, X, Y));
		ENTITY.addComponent(ComponentPresets.createPlayer(TYPE));
		PlayerComponent pc = ENTITY.getComponent(PlayerComponent.class);
		
		if(TYPE.equals(PlayerType.KRATOS)) {
			ENTITY.addComponent(ComponentPresets.createArmedAnimation(TYPE, this.texture, pc));
		} else {
			ENTITY.addComponent(ComponentPresets.createBasicAnimation(TYPE, this.texture));
		}

		ENTITY.addComponent(ComponentPresets.createHealth());
		ENTITY.addComponent(ComponentPresets.createCrosshair(TYPE, this.texture, this.worldCamera));
		ENTITY.addComponent(ComponentPresets.createName(TYPE));
		ENTITY.addComponent(ComponentPresets.createInventory());
		ENTITY.addComponent(ComponentPresets.createEquipment(TYPE.getWeapon()));

		ENTITY.addComponent(ComponentPresets.createVisibility());
		ENTITY.addComponent(ComponentPresets.createEquipment(TYPE.getWeapon()));
		ENTITY.addComponent(ComponentPresets.createAchievements());
		ENTITY.addComponent(ComponentPresets.createVestment(TYPE));
    
		if(IS_REMOTE) {
			ENTITY.addComponent(ComponentPresets.createRemotePlayerMovement(IDENTIFIER, this.gameplayBus));
		}  else {
			ENTITY.addComponent(ComponentPresets.createSimpleRemotePlayerMovement());
		}
		
		return ENTITY;
    }

    public Entity create(final int IDENTIFIER, final PlayerType TYPE, final boolean IS_REMOTE) {
    	return this.create(IDENTIFIER, TYPE, 0, 0, IS_REMOTE);
    }
}
