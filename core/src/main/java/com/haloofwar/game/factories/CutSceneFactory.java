package com.haloofwar.game.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.MusicTrack;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.cutscenes.CutScene;
import com.haloofwar.game.cutscenes.CutSceneData;
import com.haloofwar.game.cutscenes.CutSceneDataType;

public final class CutSceneFactory {
	private final TextureManager texture;
	private final SpriteBatch batch;
	private final EventBus gameplayBus;
	private final GameStaticCamera staticCamera;

	public CutSceneFactory(final GameContext context) {
		this.texture = context.getTexture();
		this.batch = context.getRender().getBatch();
		this.gameplayBus = context.getGameplay().getBus();
		this.staticCamera = context.getStaticCamera();
	}
	
	public CutScene create(final CutSceneDataType TYPE, final MusicTrack previousTrack) {
		if(TYPE == null) {
			return null;
		}
		
		
		final Texture[] IMAGES = new Texture[TYPE.getImages().length];

		for (int i = 0; i < TYPE.getImages().length; i++) {
			IMAGES[i] = this.texture.get(TYPE.getImages()[i]);
		}
		
		final CutSceneData DATA =  new CutSceneData(IMAGES, this.batch, this.gameplayBus);
		
		return new CutScene(DATA, this.staticCamera, previousTrack);
	}
}