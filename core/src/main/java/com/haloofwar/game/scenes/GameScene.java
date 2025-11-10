package com.haloofwar.game.scenes;

import com.haloofwar.common.enumerators.MusicTrack;
import com.haloofwar.game.world.World;
import com.haloofwar.interfaces.Scene;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.ui.HUD;

public abstract class GameScene implements Scene {
    protected final World world;
    protected final HUD hud;
    private final SceneDescriptor DESCRIPTOR;
	
    public GameScene(final SceneDescriptor DESCRIPTOR, final World world, final HUD hud) {
        this.world = world;
        this.hud = hud;
        this.DESCRIPTOR = DESCRIPTOR;
	}

	@Override
	public void render(float delta) {
		this.world.render();
		this.hud.render(delta);
	}
	
	@Override
	public void update(float delta) {
		this.world.update(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		this.world.getCamera().resize(width, height);
		this.hud.resize(width, height);
	}
	
	@Override
	public void reconfigureCamera() {
		this.world.reconfigureCamera();
	}
	
	@Override
	public void dispose() {
		this.hud.dispose();
	}
	
	@Override
	public void reset() {
		this.hud.reset();
	}
	
	@Override
	public World getWorld() {
		return this.world;
	}
	
    @Override
    public void show() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	
	@Override
	public MusicTrack getMusic() {
		return this.DESCRIPTOR.getMusic();
	}

}
