package com.haloofwar.game.cutscenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.engine.events.EventBus;

public class CutSceneData {
    public final Texture[] images;
    public final SpriteBatch batch;
    public final EventBus bus;

    public CutSceneData(Texture[] images, SpriteBatch batch, EventBus bus) {
        this.images = images;
        this.batch = batch;
        this.bus = bus;
    }
}
