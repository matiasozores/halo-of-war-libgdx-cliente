package com.haloofwar.game.cutscenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.common.enumerators.MusicTrack;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.GameStateEvent;
import com.haloofwar.engine.events.NextEvent;
import com.haloofwar.engine.events.PeacefulEvent;
import com.haloofwar.engine.events.PlayMusicEvent;
import com.haloofwar.engine.events.StopMusicEvent;
import com.haloofwar.engine.events.StopSoundsEvent;
import com.haloofwar.engine.events.online.ConfirmationNextCutsceneEvent;
import com.haloofwar.engine.events.online.NextCutsceneEventOnline;

public class CutScene {
	private final Texture[] images;
	private final SpriteBatch batch;
	private final EventBus bus;
	private final GameStaticCamera camera;
	private int currentIndex = 0;
	private boolean finished = false;
	private boolean started = false;
	private MusicTrack previousMusic;
	
	public CutScene(final CutSceneData data, GameStaticCamera camera, MusicTrack previousMusic) {
		this.images = data.images;
		this.batch = data.batch;
		this.bus = data.bus;
		this.camera = camera;
		this.previousMusic = previousMusic;
		

		this.bus.publish(new PeacefulEvent(true));
		this.bus.publish(new GameStateEvent(GameState.CUTSCENE));
	}

	public void onNext(NextEvent event) {
		if(event.isPressed()) {
			this.bus.publish(new NextCutsceneEventOnline());
		}
	}
	
	public void onConfirmation(ConfirmationNextCutsceneEvent event) {
		if (this.currentIndex >= this.images.length - 1) {
			this.finished = true;
			this.bus.publish(new GameStateEvent(GameState.PLAYING));
			this.bus.publish(new PeacefulEvent(false));
			this.bus.publish(new StopSoundsEvent());
		} else {
			this.currentIndex++;
			this.bus.publish(new StopSoundsEvent());
		}
	}

	
	public void render(float delta) {
	    batch.setProjectionMatrix(camera.getOrthographic().combined);
	    batch.begin();

	    // 1. Fondo negro que cubre todo
	    float screenWidth = camera.getOrthographic().viewportWidth;
	    float screenHeight = camera.getOrthographic().viewportHeight;
	    batch.setColor(0, 0, 0, 1); // color negro
	    batch.draw(blackTexture, 0, 0, screenWidth, screenHeight);
	    batch.setColor(1, 1, 1, 1); // restauramos el color normal

	    // 2. Dibujamos la imagen centrada
	    if (currentIndex < images.length) {
	        Texture texture = images[currentIndex];
	        float imgWidth = texture.getWidth();
	        float imgHeight = texture.getHeight();
	        float scale = Math.min(screenWidth / imgWidth, screenHeight / imgHeight);
	        float drawWidth = imgWidth * scale;
	        float drawHeight = imgHeight * scale;
	        float x = (screenWidth - drawWidth) / 2f;
	        float y = (screenHeight - drawHeight) / 2f;

	        batch.draw(texture, x, y, drawWidth, drawHeight);
	    }

	    batch.end();
	}




	public void update(float delta) {
		if(!this.started) {
			this.started = true;
			this.bus.publish(new StopMusicEvent());
			this.bus.publish(new PlayMusicEvent(this.previousMusic));
		}
	}

	public boolean isFinished() {
		return this.finished;
	}
	
	public void reset() {
	    this.currentIndex = 0;
	    this.finished = false;
	    this.started = false;
	    
	    this.bus.publish(new PeacefulEvent(true));
	    this.bus.publish(new GameStateEvent(GameState.WAITING));
	}
	
	private static final Texture blackTexture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
	static {
	    com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
	    pixmap.setColor(0, 0, 0, 1);
	    pixmap.fill();
	    blackTexture.draw(pixmap, 0, 0);
	    pixmap.dispose();
	}

}
