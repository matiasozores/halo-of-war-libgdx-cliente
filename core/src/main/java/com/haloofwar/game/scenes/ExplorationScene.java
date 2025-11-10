package com.haloofwar.game.scenes;

import com.haloofwar.game.world.NPCSpawner;
import com.haloofwar.game.world.World;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.ui.HUD;

public class ExplorationScene extends GameScene {
    private final NPCSpawner spawner;

	public ExplorationScene(final SceneDescriptor DESCRIPTOR, final World world, final HUD hud, final NPCSpawner npcSpawner) {
        super(DESCRIPTOR, world, hud);
        this.spawner = npcSpawner;
    }

    @Override
    public boolean isLevel() {
        return false;
    }
    
    public void respawn() {
    	this.spawner.spawn();
    }
}
