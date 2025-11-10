package com.haloofwar.game.factories;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.common.enumerators.EnemyType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.components.TargetComponent;
import com.haloofwar.game.components.TransformComponent;
import com.haloofwar.game.components.VisibilityComponent;
import com.haloofwar.game.config.ComponentPresets;

public final class EnemyFactory {
    private final TextureManager texture;
    private final GameplayContext gameplay;
    
    public EnemyFactory(final GameContext context) {
        this.texture = context.getTexture();
        this.gameplay = context.getGameplay();
    }

    public Entity create(final int IDENTIFIER, final EnemyType TYPE, final float X, final float Y) {
        if(this.gameplay.getCurrentPlayer() == null) {
        	System.out.println("No se puede crear al enemigo, el jugador es nulo y no tiene trackeo... | EnemyFactory");
        	return null;
        }
    	
    	final Entity entity = new Entity();
        TransformComponent transform = ComponentPresets.createTransform(IDENTIFIER, X, Y, TYPE.getWidth(), TYPE.getHeight());
        entity.addComponent(transform);
        entity.addComponent(ComponentPresets.createBasicAnimation(TYPE, this.texture));
        entity.addComponent(ComponentPresets.createHealth());
        entity.addComponent(ComponentPresets.createName(TYPE));
        entity.addComponent(ComponentPresets.createEnemy(TYPE));
        entity.addComponent(ComponentPresets.createEnemyMovement(transform, this.gameplay.getCurrentPlayer().getComponent(TransformComponent.class), this.gameplay.getCurrentPlayer().getComponent(VisibilityComponent.class)));
        entity.addComponent(new TargetComponent(this.gameplay.getCurrentPlayer().getComponent(TransformComponent.class))); 
        
        if(TYPE.getWeapon() != null) {
            entity.addComponent(ComponentPresets.createEquipment(TYPE.getWeapon()));
            entity.addComponent(ComponentPresets.createEnemyWeaponAI());
            
        }
        
        return entity;
    }

}