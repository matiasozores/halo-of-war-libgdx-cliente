package com.haloofwar.ui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.UIState;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.online.SellObjectEventOnline;
import com.haloofwar.game.components.InventoryComponent;
import com.haloofwar.game.components.NameComponent;
import com.haloofwar.game.components.StockComponent;

public class InventoryPopup extends Popup {
    private final TextureManager TEXTURE;
    private final PlayerType type;
    private final Texture BUY_BUTTON;
    private final Texture GOLD_COIN;

    public InventoryPopup(
        final EventBus GAMEPLAY_BUS, 
        final TextureManager TEXTURE, 
        final BitmapFont FONT, 
        final InventoryComponent INVENTORY,
        final SpriteBatch batch,
        final PlayerType type
    ) {
        super(GAMEPLAY_BUS, FONT, TEXTURE.get(Background.INVENTORY), INVENTORY.getObjects(), UIState.INVENTORY, batch);	
        this.TEXTURE = TEXTURE;
        this.type = type;

        this.BUY_BUTTON = TEXTURE.get(Background.BUY);
        this.GOLD_COIN = TEXTURE.get(ObjectType.MONEDA_DE_ORO);
    }
    
    @Override
    public void refresh(Entity player) {
        if (player == null) return;

        if (player.hasComponent(InventoryComponent.class)) {
            InventoryComponent inv = player.getComponent(InventoryComponent.class);
            this.ENTITIES = inv.getObjects();
        }
    }

    @Override
    protected void drawEntity(final SpriteBatch BATCH, final Entity ITEM, final float X, final float Y, final boolean IS_SELECTED) {
        if (ITEM == null) return;

        String name = ITEM.getComponent(NameComponent.class).name;
        StockComponent stockComponent = ITEM.getComponent(StockComponent.class);
        int stock = stockComponent.getStock();
        ObjectType type = stockComponent.getType();
        Texture icon = this.TEXTURE.get(type);
        String description = type.getDescription();
        int price = 10;

        // --- Icono ---
        BATCH.draw(icon, X, Y, 64, 64);

        float nameX = X + 108;
        float nameY = Y + 80;

        GlyphLayout nameLayout = new GlyphLayout(this.FONT, name);
        
        if (IS_SELECTED) {
            this.FONT.setColor(1f, 0.72f, 0.12f, 1f);
        } else {
            this.FONT.setColor(0.15f, 0.15f, 0.15f, 1f);
        }

        this.FONT.draw(BATCH, name, nameX + 1, nameY + 1);
        this.FONT.draw(BATCH, name, nameX, nameY);

        // --- Precio + Monedas ---
        boolean isGoldCoin = (type == ObjectType.MONEDA_DE_ORO);
        float stockXOffset;

        if (this.GOLD_COIN != null && !isGoldCoin) {
            BATCH.draw(GOLD_COIN, nameX + nameLayout.width + 10, nameY - 25, 24, 24);
            this.FONT.setColor(1f, 1f, 0f, 1f);
            this.FONT.draw(BATCH, String.valueOf(price), nameX + nameLayout.width + 40, nameY);
            stockXOffset = nameLayout.width + 90; // posición normal con monedas
        } else {
            stockXOffset = nameLayout.width + 30; // más cerca del nombre si no hay monedas
        }

        // --- Stock ---
        this.FONT.setColor(0.92f, 0.8f, 0.16f, 1f);
        this.FONT.draw(BATCH, "x" + stock, nameX + stockXOffset, nameY);

        // --- Descripción ---
        this.FONT.getData().setScale(0.8f);
        this.FONT.setColor(0.23f, 0.23f, 0.23f, 1f);
        GlyphLayout descLayout = new GlyphLayout(this.FONT, description, this.FONT.getColor(), 800, Align.left, true);
        this.FONT.draw(BATCH, descLayout, nameX, nameY - 30);
        this.FONT.getData().setScale(1f);

        // --- Botón Comprar ---
        // 🔥 No se muestra si el tipo de objeto es MONEDA_DE_ORO
        if (IS_SELECTED && !isGoldCoin) {
            BATCH.draw(BUY_BUTTON, nameX + 700, Y - 100, 128, 128);
        }
    }


    @Override
    public void onSelectOption() {
        Entity selected = getSelectedEntity();
        if (selected == null) {
            return;
        }

        NameComponent nameComp = selected.getComponent(NameComponent.class);
        String name = nameComp != null ? nameComp.name : null;
        
        if (name == null) {
            return;
        }

        this.GAMEPLAY_BUS.publish(new SellObjectEventOnline(this.type, ObjectType.getByName(name)));
    }
}
