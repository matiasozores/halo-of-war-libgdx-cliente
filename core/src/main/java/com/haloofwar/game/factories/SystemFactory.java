package com.haloofwar.game.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.systems.AnimationSystem;
import com.haloofwar.game.systems.BulletSystem;
import com.haloofwar.game.systems.CrosshairSystem;
import com.haloofwar.game.systems.DamageSystem;
import com.haloofwar.game.systems.DialogueSystem;
import com.haloofwar.game.systems.RemoteMovementSystem;
import com.haloofwar.game.systems.RenderSystem;
import com.haloofwar.game.systems.SystemCollection;

public final class SystemFactory {
	private SystemFactory() {}
	
    public static SystemCollection createGameplaySystems(
        SpriteBatch batch, TextureManager texture, EventBus bus, GameplayContext context) {

        SystemCollection systems = new SystemCollection();
        
        Object[] allSystems = {
            new RemoteMovementSystem(bus), 
            new BulletSystem(texture, bus),
            new AnimationSystem(),
            new RenderSystem(batch),
            new CrosshairSystem(bus, batch, context),
            new DialogueSystem(bus, texture),
            new DamageSystem(bus)
        };

        for (Object system : allSystems) {
            registerSystem(systems, system);
        }

        return systems;
    }

    public static void registerSystem(SystemCollection collection, Object system) {
    	collection.addSystem(system);
    }
}
