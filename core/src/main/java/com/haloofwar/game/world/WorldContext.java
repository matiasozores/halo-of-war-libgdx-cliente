package com.haloofwar.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.cameras.GameWorldCamera;

public class WorldContext {
	private GameplayContext gameplay;
	
	private final SpriteBatch batch;
	private final GameWorldCamera worldCamera;
	private final Entity player;
	private final MapRenderer map;
	
	public WorldContext(final MapRenderer map,
			final GameplayContext gameplay,
			final SpriteBatch batch,
			final GameWorldCamera worldCamera) {

		this.gameplay = gameplay;
		this.player = this.gameplay.getCurrentPlayer();
		this.batch = batch;
		this.worldCamera = worldCamera;
		this.worldCamera.configure(player, map.getMetaData());
		this.map = map;
	}
	
	public void reconfigureCamera() {
		this.worldCamera.configure(this.player, this.map.getMetaData());
	}
	
	public SpriteBatch getBatch() {
		return this.batch;
	}
	
	public GameWorldCamera getCamera() {
		return this.worldCamera;
	}
	
	public GameplayContext getGameplay() {
		return this.gameplay;
	}
}
