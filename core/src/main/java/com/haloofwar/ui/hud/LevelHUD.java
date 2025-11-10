package com.haloofwar.ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.managers.FontManager;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.ShowTextEvent;
import com.haloofwar.engine.utils.Text;
import com.haloofwar.ui.HUD;
import com.haloofwar.ui.components.HUDComponent;

public class LevelHUD extends HUD {
    private Text text; 
    private boolean showText = false;
    private float showTimer = 0f;
    private float showDuration = 0f; // duración variable

    public LevelHUD(
        final HUDComponent[] components,
        final GameStaticCamera camera,
        final SpriteBatch batch,
        final EventBus bus,
        final FontManager font
    ) {
        super(components, camera, batch, bus);
        this.text = new Text("", font.getTitleFont());
    }

    private void onShowText(ShowTextEvent event) {
        this.text.message = event.message;
        this.showText = true;
        this.showTimer = 0f;
        this.showDuration = event.duration; 
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.setProjectionMatrix(this.camera.getOrthographic().combined);
        this.batch.begin();

        if (this.showText) {
            // ✅ Usar tamaño del viewport en lugar de Gdx.graphics
            float screenWidth = camera.getViewport().getWorldWidth();
            float screenHeight = camera.getViewport().getWorldHeight();

            // Calcular el tamaño del texto
            text.getLayout().setText(text.getFont(), text.message);
            float textWidth = text.getLayout().width;
            float textHeight = text.getLayout().height;

            // 📍 Centrar texto correctamente en el mundo de la cámara
            float x = (screenWidth - textWidth) / 2f;
            float y = (screenHeight + textHeight) / 2f - text.getFont().getDescent();

            // === Dibujo del texto con contorno ===
            float offset = 2f;
            Color outlineColor = Color.BLACK;

            text.getFont().setColor(outlineColor);
            text.getFont().draw(batch, text.message, x - offset, y + offset);
            text.getFont().draw(batch, text.message, x + offset, y + offset);
            text.getFont().draw(batch, text.message, x - offset, y - offset);
            text.getFont().draw(batch, text.message, x + offset, y - offset);

            // Texto principal
            text.getFont().setColor(Color.RED);
            text.getFont().draw(batch, text.message, x, y);
        }

        this.batch.end();
    }

    @Override
    public void update(float delta) {
        if (this.showText) {
            this.showTimer += delta;
            if (this.showTimer >= this.showDuration) {
                this.showText = false;
                this.text.message = "";
            }
        }
    }

    @Override
    public void reset() {
    	super.reset();
        this.listenerManager.clear();
        this.listenerManager.add(this.gameplayBus, ShowTextEvent.class, this::onShowText);
    }
}
