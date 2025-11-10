package com.haloofwar.game.scenes;

import com.haloofwar.game.managers.LevelController;
import com.haloofwar.game.world.World;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.ui.HUD;

public class Level extends GameScene {
    private final LevelController controller;

    public Level(final SceneDescriptor DESCRIPTOR, final World world, final HUD hud, final LevelController controller) {
        super(DESCRIPTOR, world, hud);
        this.controller = controller;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.controller.update(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.controller.render(delta); 
    }

	@Override
	public boolean isLevel() {
		return true;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.controller.dispose();
	}
	
	@Override
	public void reset() {
		super.reset();
		this.controller.reset();
	}
}
