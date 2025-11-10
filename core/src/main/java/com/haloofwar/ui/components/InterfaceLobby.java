package com.haloofwar.ui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.game.components.InventoryComponent;

public class InterfaceLobby implements HUDComponent {

    private final SpriteBatch batch;

    // Íconos principales existentes
    private final Texture image1;
    private final Texture image2;
    private final Texture image3;

    // Nuevos íconos agregados
    private final Texture vestmentIcon;
    private final Texture achievementIcon;
    private final Texture minimapIcon;

    // Minimap grande de fondo
    private final Texture minimap;

    private final InventoryComponent inventory;
    private final Texture textureCoin;
    private final BitmapFont font;
    
    // Posiciones y tamaños por defecto
    private float yTop = 640, iconWidth = 64, iconHeight = 64;
    
    // Posiciones en X alineadas una al lado de otra
    private float x1 = 980;
    private float x2 = x1 + 100;
    private float x3 = x2 + 100;
    private float x4 = x1 - 100; 
    private float x5 = x4 - 100; 
    private float x6 = x5 - 100; 
    
    // Minimap grande en la esquina inferior izquierda
    private float minimapX = 20, minimapY = 20, minimapWidth = 320, minimapHeight = 208.66f;
    
    // Moneda
    private float coinX = 50, coinY = 650, coinSize = 48;
    
    private boolean visible = true;
    
    public InterfaceLobby(SpriteBatch batch, TextureManager texture, BitmapFont font, InventoryComponent inventory) {
        this.batch = batch;
        this.image1 = texture.get(Background.INVENTORY_ICON);
        this.image2 = texture.get(Background.SHOP_ICON);
        this.image3 = texture.get(Background.EQUIPMENT_ICON);
        
        // Nuevos íconos
        this.vestmentIcon = texture.get(Background.VESTMENT_ICON);
        this.achievementIcon = texture.get(Background.ACHIEVEMENT_ICON);
        this.minimapIcon = texture.get(Background.MINIMAP_ICON);
        
        // Minimap grande
        this.minimap = texture.get(Background.MINIMAP);
        
        this.inventory = inventory;
        this.textureCoin = texture.get(ObjectType.MONEDA_DE_ORO);
        this.font = font;
    }

    @Override
    public void render() {
        if (!this.visible) return;
        
        // Renderizar íconos del menú superior
        if (image1 != null) batch.draw(image1, x1, yTop, iconWidth, iconHeight);
        if (image2 != null) batch.draw(image2, x2, yTop, iconWidth, iconHeight);
        if (image3 != null) batch.draw(image3, x3, yTop, iconWidth, iconHeight);
        if (vestmentIcon != null) batch.draw(vestmentIcon, x4, yTop, iconWidth, iconHeight);
        if (achievementIcon != null) batch.draw(achievementIcon, x5, yTop, iconWidth, iconHeight);
        if (minimapIcon != null) batch.draw(minimapIcon, x6, yTop, iconWidth, iconHeight);
        
        // Renderizar minimapa grande
        if (this.minimap != null)
            batch.draw(this.minimap, this.minimapX, this.minimapY, this.minimapWidth, this.minimapHeight);
        
        // Dibujar moneda
        if (textureCoin != null)
            batch.draw(textureCoin, coinX, coinY, coinSize, coinSize);

        // Dibujar cantidad de monedas con borde
        if (font != null && inventory != null) {
            int coinCount = inventory.getItemCount(ObjectType.MONEDA_DE_ORO);
            String text = "x " + coinCount;
            
            // Borde negro (4 direcciones)
            font.setColor(0f, 0f, 0f, 1f);
            font.draw(batch, text, coinX + coinSize + 10 - 1, coinY + coinSize * 0.8f - 1);
            font.draw(batch, text, coinX + coinSize + 10 + 1, coinY + coinSize * 0.8f - 1);
            font.draw(batch, text, coinX + coinSize + 10 - 1, coinY + coinSize * 0.8f + 1);
            font.draw(batch, text, coinX + coinSize + 10 + 1, coinY + coinSize * 0.8f + 1);

            // Texto amarillo encima
            font.setColor(1f, 1f, 0f, 1f);
            font.draw(batch, text, coinX + coinSize + 10, coinY + coinSize * 0.8f);
        }
    }
    
    public void setVisible(final boolean VISIBLE) {
        this.visible = VISIBLE;
    }

    @Override
    public void dispose() { }

    @Override
    public void reset() { }
}
