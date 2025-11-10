package com.haloofwar.game.cutscenes.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.common.enumerators.MusicTrack;
import com.haloofwar.common.enumerators.SceneType;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.events.ChangeSceneEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.GameStateEvent;
import com.haloofwar.interfaces.Scene;

public abstract class AbstractLevelScene implements Scene {

    protected final SpriteBatch batch;
    protected final Texture texture;
    protected final EventBus bus;
    protected final SceneType nextScene;
    protected final GameStaticCamera camera;

    protected boolean finished = false;
    protected float timer = 0f;
    protected final float DISPLAY_TIME = 3f;

    protected AbstractLevelScene(GameStaticCamera camera, SpriteBatch batch, Texture texture, EventBus bus, SceneType nextScene) {
        this.camera = camera;
        this.batch = batch;
        this.texture = texture;
        this.bus = bus;
        this.nextScene = nextScene;
    }

    protected void finishScene() {
        this.finished = true;
        this.bus.publish(new GameStateEvent(GameState.PLAYING));
        this.bus.publish(new ChangeSceneEvent(this.nextScene));
    }

    @Override
    public void update(float delta) {
        if (this.finished) {
        	return;
        }

        this.timer += delta;
        if (this.timer >= this.DISPLAY_TIME) {
            this.finishScene();
        }
    }

    @Override
    public void render(float delta) {
        this.batch.setProjectionMatrix(this.camera.getOrthographic().combined);
        this.batch.begin();
        this.batch.draw(this.texture, 0, 0, this.camera.getViewportWidth(), this.camera.getViewportHeight());
        this.batch.end();
    }

    @Override
    public void show() {
        this.finished = false;
        this.timer = 0f;
    }

    @Override public void resize(int width, int height) {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void reconfigureCamera() {}
    @Override public void reset() {}

    @Override
    public boolean isLevel() {
        return true;
    }

    @Override
    public MusicTrack getMusic() {
        return MusicTrack.VICTORY;
    }
}
