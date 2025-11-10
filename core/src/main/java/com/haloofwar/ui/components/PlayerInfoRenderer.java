package com.haloofwar.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.UpdateCurrentWeaponEvent;
import com.haloofwar.game.components.EquipmentComponent;
import com.haloofwar.game.components.HealthComponent;
import com.haloofwar.game.components.MovementComponent;
import com.haloofwar.game.components.NameComponent;

public class PlayerInfoRenderer implements HUDComponent {

    private final SpriteBatch batch;
    private final BitmapFont smallFont;
    private final BitmapFont titleFont;
    private Texture portrait;
    private Texture weapon;
    private final Texture background;

    public HealthComponent health;
    public NameComponent nameComponent;
    public MovementComponent movement;
    public EquipmentComponent equipment;
    private final TextureManager texture;

    private final float x = 20;
    private final float y = 620;

    private final float portraitWidth = 64;
    private final float portraitHeight = 64;
    private final float weaponWidth = 64;
    private final float weaponHeight = 64;
    private final float bgWidth = 112;
    private final float bgHeight = 112;
    private final float spacing = 10;

    private EventListenerManager listenerManager = new EventListenerManager();
    private final EventBus bus;

    public PlayerInfoRenderer(
        SpriteBatch batch,
        BitmapFont smallFont,
        BitmapFont titleFont,
        TextureManager texture,
        NameComponent nameComponent,
        HealthComponent health,
        MovementComponent movement,
        EquipmentComponent equipment,
        Texture background,
        Texture portrait,
        EventBus bus
    ) {
        this.batch = batch;
        this.smallFont = smallFont;
        this.titleFont = titleFont;
        this.equipment = equipment;
        this.nameComponent = nameComponent;
        this.health = health;
        this.movement = movement;
        this.background = background;
        this.texture = texture;

        this.portrait = portrait;
        this.weapon = texture.get(this.equipment.getCurrentWeapon());
        this.bus = bus;
    }

    private void onChangeWeapon(UpdateCurrentWeaponEvent event) {
        this.weapon = this.texture.get(event.weapon);
    }

    @Override
    public void render() {
        // Dibuja retrato del jugador
        if (portrait != null) {
            batch.draw(portrait, x, y, portraitWidth, portraitHeight);
        }

        // === Nombre del jugador con contorno ===
        float nameX = x + portraitWidth + 10;
        float nameY = y + portraitHeight - 10;
        float offset = 1.5f;

        Color outlineColor = Color.BLACK;
        titleFont.setColor(outlineColor);
        titleFont.draw(batch, nameComponent.name, nameX - offset, nameY + offset);
        titleFont.draw(batch, nameComponent.name, nameX + offset, nameY + offset);
        titleFont.draw(batch, nameComponent.name, nameX - offset, nameY - offset);
        titleFont.draw(batch, nameComponent.name, nameX + offset, nameY - offset);

        titleFont.setColor(Color.WHITE);
        titleFont.draw(batch, nameComponent.name, nameX, nameY);

        // === Texto de vida, escudo y velocidad extra con contorno ===
        String hpText = (int) health.getCurrentHealth() + "/" + health.getMaxHealth();
        String shieldText = "Escudo: " + health.shield;
        String speedText = "Velocidad extra tiempo activo: " + (int) this.movement.speedDuration;

        drawOutlinedText(smallFont, hpText, nameX, nameY - 75, Color.WHITE);
        drawOutlinedText(smallFont, shieldText, nameX, nameY - 90, Color.WHITE);
        drawOutlinedText(smallFont, speedText, nameX, nameY - 105, Color.WHITE);

        // === Dibuja el fondo y el arma ===
        GlyphLayout layout = new GlyphLayout(titleFont, nameComponent.name);
        float weaponX = nameX + layout.width + spacing;
        float weaponY = (nameY - bgHeight / 2f) - 30; // centrar verticalmente con el nombre

        if (background != null) {
            batch.draw(background, weaponX, weaponY, bgWidth, bgHeight);
        }

        if (weapon != null) {
            float weaponCenteredX = weaponX + (bgWidth - weaponWidth) / 2f;
            float weaponCenteredY = weaponY + (bgHeight - weaponHeight) / 2f;
            batch.draw(weapon, weaponCenteredX, weaponCenteredY, weaponWidth, weaponHeight);
        }
    }

    /**
     * Dibuja texto con contorno negro alrededor
     */
    private void drawOutlinedText(BitmapFont font, String text, float x, float y, Color mainColor) {
        float offset = 1.2f;

        font.setColor(Color.BLACK);
        font.draw(batch, text, x - offset, y + offset);
        font.draw(batch, text, x + offset, y + offset);
        font.draw(batch, text, x - offset, y - offset);
        font.draw(batch, text, x + offset, y - offset);

        font.setColor(mainColor);
        font.draw(batch, text, x, y);
    }

    @Override
    public void dispose() {
        this.listenerManager.clear();
    }

    @Override
    public void reset() {
        this.listenerManager.clear();
        this.listenerManager.add(this.bus, UpdateCurrentWeaponEvent.class, this::onChangeWeapon);
    }
}
