package com.haloofwar.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.game.dependences.LevelData;

public class InterfaceLevel implements HUDComponent {
    private final LevelData data;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final float waveX = 1080, waveY = 680;
    private final float enemiesX = 1080, enemiesY = 640;

    public InterfaceLevel(final LevelData data, final SpriteBatch batch, final BitmapFont font) {
        this.data = data;
        this.batch = batch;
        this.font = font;
    }

    @Override
    public void render() {
        String waveText = "Oleada: " + (this.data.getWavesPassed() + 1) + "/" + this.data.getWaveCount();
        String enemiesText = "Enemigos: " + this.data.getEnemiesDefeated() + "/" + this.data.getEnemiesToDefeat();

        float offset = 2f;

        this.font.setColor(Color.BLACK);
        this.font.draw(this.batch, waveText, waveX - offset, waveY + offset);
        this.font.draw(this.batch, waveText, waveX + offset, waveY + offset);
        this.font.draw(this.batch, waveText, waveX - offset, waveY - offset);
        this.font.draw(this.batch, waveText, waveX + offset, waveY - offset);

        this.font.draw(this.batch, enemiesText, enemiesX - offset, enemiesY + offset);
        this.font.draw(this.batch, enemiesText, enemiesX + offset, enemiesY + offset);
        this.font.draw(this.batch, enemiesText, enemiesX - offset, enemiesY - offset);
        this.font.draw(this.batch, enemiesText, enemiesX + offset, enemiesY - offset);

        this.font.setColor(Color.WHITE);
        this.font.draw(this.batch, waveText, waveX, waveY);
        this.font.draw(this.batch, enemiesText, enemiesX, enemiesY);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
    }
}
