package com.haloofwar.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.UIState;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.online.BuyVestmentEventOnline;
import com.haloofwar.engine.events.online.EquipVestmentEventOnline;
import com.haloofwar.game.components.VestmentComponent;
import com.haloofwar.game.components.VestmentInventoryComponent;

public class VestmentPopup extends Popup {

    private final Texture BUY_BUTTON;
    private final Texture GOLD_COIN;
    private final TextureManager TEXTURE;
    private final VestmentInventoryComponent vestmentComponent;
    
    public VestmentPopup(
            final EventBus GAMEPLAY_BUS,
            TextureManager textureManager,
            BitmapFont font,
            VestmentInventoryComponent vestmentComponent,
            final SpriteBatch batch
    ) {
        super(GAMEPLAY_BUS, font, textureManager.get(Background.VESTMENT), vestmentComponent.getVestments(), UIState.VESTMENT, batch);
        this.BUY_BUTTON = textureManager.get(Background.BUY);
        this.GOLD_COIN = textureManager.get(ObjectType.MONEDA_DE_ORO);
        this.TEXTURE = textureManager;
        this.vestmentComponent = vestmentComponent;
    }

    @Override
    protected void drawEntity(SpriteBatch batch, Entity entity, float x, float y, boolean isSelected) {
        if (entity == null) return;

        Texture icon = null;
        String name = "";
        String description = "";
        int price = 0;
        boolean unlocked = false;
        boolean equipped = false;

        if (entity.hasComponent(VestmentComponent.class)) {
            VestmentComponent vest = entity.getComponent(VestmentComponent.class);
            name = vest.type.getName();
            icon = TEXTURE.get(vest.type);
            description = vest.type.getDescription();
            price = vest.type.getPrice();
            unlocked = vest.unlocked;

            // Verificar si esta vestimenta es la actualmente equipada
            if (entity == vestmentComponent.getCurrentVestment()) {
                equipped = true;
            }
        }

        // Ícono
        if (icon != null) {
            batch.draw(icon, x - 15, y - 20, 96, 96);
        }

        float nameX = x + 120;
        float nameY = y + 80;

        // Nombre
        this.FONT.setColor(isSelected ? new Color(245 / 255f, 73 / 255f, 39 / 255f, 1f)
                : new Color(0.15f, 0.15f, 0.15f, 1f));
        GlyphLayout nameLayout = new GlyphLayout(FONT, name);
        FONT.draw(batch, name, nameX, nameY);

        // Si está bloqueada, mostrar precio
        if (!unlocked) {
            float priceX = nameX + nameLayout.width + 15;
            if (GOLD_COIN != null) {
                batch.draw(GOLD_COIN, priceX, nameY - 25, 24, 24);
                FONT.setColor(1f, 1f, 0f, 1f);
                FONT.draw(batch, String.valueOf(price), priceX + 30, nameY);
            }
        }

        // Descripción
        this.FONT.getData().setScale(0.8f);
        this.FONT.setColor(0.25f, 0.25f, 0.25f, 1f);
        GlyphLayout descLayout = new GlyphLayout(FONT, description, FONT.getColor(), 700, Align.left, true);
        this.FONT.draw(batch, descLayout, nameX, nameY - 35);
        this.FONT.getData().setScale(1f);

        // Estado
        if (unlocked) {
            if (equipped) {
                FONT.setColor(new Color(0.3f, 0.5f, 1f, 1f)); // Azul brillante para "Equipada"
                FONT.draw(batch, "Equipada", nameX + 650, nameY);
            } else {
                FONT.setColor(new Color(0.2f, 0.6f, 0.2f, 1f)); // Verde para "Desbloqueada"
                FONT.draw(batch, "Desbloqueada", nameX + 650, nameY);
            }
        }

        // Botón comprar si está seleccionada y bloqueada
        if (isSelected && !unlocked) {
            batch.draw(BUY_BUTTON, nameX + 650, y - 80, 128, 128);
        }
    }


    @Override
    public void refresh(Entity player) {
        // Aquí se actualizaría la lista de vestimentas del jugador
    }

    @Override
    public void onSelectOption() {
    	Entity selected = this.getSelectedEntity();
    	VestmentComponent vestmentComp = selected.getComponent(VestmentComponent.class);
    	
    	if(vestmentComp.unlocked) {
    		this.equipCurrent(vestmentComp);
    	} else {
    		this.buyCurrent(vestmentComp);
    	}
    }
    
    private void buyCurrent(VestmentComponent vestmentComp) {
    	this.GAMEPLAY_BUS.publish(new BuyVestmentEventOnline(vestmentComp.type));
    }
    
    private void equipCurrent(VestmentComponent vestmentComp) {
    	this.GAMEPLAY_BUS.publish(new EquipVestmentEventOnline(vestmentComp.type));
    }
}
