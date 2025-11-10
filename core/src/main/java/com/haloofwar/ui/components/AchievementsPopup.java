package com.haloofwar.ui.components;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.UIState;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.components.AchievementComponent;
import com.haloofwar.game.components.NameComponent;

public class AchievementsPopup extends Popup {

    private final TextureManager TEXTURE;

    public AchievementsPopup(
        final EventBus GAMEPLAY_BUS,
        final TextureManager TEXTURE,
        final BitmapFont FONT,
        final ArrayList<Entity> ACHIEVEMENTS,
        final SpriteBatch batch
    ) {
        super(GAMEPLAY_BUS, FONT, TEXTURE.get(Background.ACHIEVEMENT), ACHIEVEMENTS, UIState.ACHIEVEMENT, batch);
        this.TEXTURE = TEXTURE;
    }

    @Override
    public void refresh(Entity player) {
        // Si más adelante querés actualizar logros dinámicamente, podés hacerlo acá.
        // Por ejemplo: recargar la lista desde el sistema de progreso del jugador.
    }

    @Override
    protected void drawEntity(final SpriteBatch BATCH, final Entity ITEM, final float X, final float Y, final boolean IS_SELECTED) {
        if (ITEM == null || !ITEM.hasComponent(AchievementComponent.class)) return;

        AchievementComponent ac = ITEM.getComponent(AchievementComponent.class);
        NameComponent nameC = ITEM.getComponent(NameComponent.class);
  
        Texture icon = TEXTURE.get(ac.type);
        String name = nameC.name;
        String description = ac.type.getDescription();
        boolean unlocked = ac.unlocked;

        // Fondo y borde del logro
        if (IS_SELECTED) {
            this.FONT.setColor(1f, 0.85f, 0.2f, 1f);
        } else {
            this.FONT.setColor(0.2f, 0.2f, 0.2f, 1f);
        }

        // Dibuja el ícono del logro
        BATCH.draw(icon, X - 7, Y - 15, 80, 80);

        float textX = X + 110;
        float textY = Y + 70;

        GlyphLayout nameLayout = new GlyphLayout(this.FONT, name);
        this.FONT.draw(BATCH, name, textX, textY);

        // Estado del logro
        String statusText = unlocked ? "DESBLOQUEADO" : "BLOQUEADO";
        this.FONT.getData().setScale(0.8f);
        
        if(unlocked) {
        	this.FONT.setColor(0.1f, 0.6f, 0.1f, 1f);
        } else {
        	this.FONT.setColor(0.5f, 0.1f, 0.1f, 1f);
        	
        }
        
        this.FONT.draw(BATCH, statusText, textX + nameLayout.width + 20, textY);

        // Descripción del logro
        this.FONT.setColor(0.25f, 0.25f, 0.25f, 1f);
        GlyphLayout descLayout = new GlyphLayout(this.FONT, description, this.FONT.getColor(), 800, Align.left, true);
        this.FONT.draw(BATCH, descLayout, textX, textY - 30);

        this.FONT.getData().setScale(1f);
    }

    @Override
    public void onSelectOption() {
        // Si querés mostrar detalles o reproducir un sonido al seleccionar un logro:
        // this.GAMEPLAY_BUS.publish(new PlaySoundEvent(SoundType.ACHIEVEMENT_SELECT));
    }
}
