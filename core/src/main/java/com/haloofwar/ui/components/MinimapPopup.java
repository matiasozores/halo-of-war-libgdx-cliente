package com.haloofwar.ui.components;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.UIState;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;

public class MinimapPopup extends Popup {

    public MinimapPopup(
        final EventBus GAMEPLAY_BUS,
        final TextureManager TEXTURE,
        final BitmapFont FONT,
        final SpriteBatch batch
    ) {
        super(GAMEPLAY_BUS, FONT, TEXTURE.get(Background.MAP), new ArrayList<Entity>(0) , UIState.MINIMAP, batch);
    }

    @Override
    public void render() {
        if (!this.visible) return;

        super.render();
    }

    @Override
    protected void drawEntity(SpriteBatch BATCH, Entity ENTITY, float X, float Y, boolean IS_SELECTED) {
        // Implementa tus iconos / textos del minimapa aquí si los necesitás
    }

    @Override
    public void refresh(Entity player) {
        // Si no necesitas refrescar contenido, dejar vacío
    }

    @Override
    public void onSelectOption() {
        // Acción al seleccionar
    }
}
