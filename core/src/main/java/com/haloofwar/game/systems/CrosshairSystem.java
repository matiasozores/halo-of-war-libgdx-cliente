package com.haloofwar.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.PeacefulEvent;
import com.haloofwar.engine.events.UpdateMouseEvent;
import com.haloofwar.game.components.CrosshairComponent;
import com.haloofwar.interfaces.Renderable;
import com.haloofwar.interfaces.Updatable;

public class CrosshairSystem extends EventEntitySystem implements Updatable, Renderable {
	
    private final SpriteBatch batch;
    private boolean peaceful = false;
    private final GameplayContext context;
    
    public CrosshairSystem(EventBus bus, SpriteBatch batch, GameplayContext context) {
        super(bus);
        this.batch = batch;
        this.listenerManager.add(bus, PeacefulEvent.class, this::onPeace);
        this.context = context;
    }

    private void onPeace(PeacefulEvent event) {
        this.peaceful = event.isPeaceful;
    }
    
    @Override
    public void register(Entity e) {
        if (e.hasComponent(CrosshairComponent.class)) {
            super.register(e);
        }
    }

    @Override
    public void update(float delta) {
        if (this.peaceful) return;

        for (Entity entity : this.entities) {
            CrosshairComponent crosshair = entity.getComponent(CrosshairComponent.class);

            if (crosshair != null) {
                int screenWidth = Gdx.graphics.getWidth();
                int screenHeight = Gdx.graphics.getHeight();

                float screenX = Gdx.input.getX();
                float screenY = Gdx.input.getY();

                screenX = Math.max(0, Math.min(screenX, screenWidth));
                screenY = Math.max(0, Math.min(screenY, screenHeight));

                Vector3 worldPos = new Vector3(screenX, screenY, 0);
                crosshair.camera.getOrthographic().unproject(worldPos);

                float camX = crosshair.camera.getOrthographic().position.x;
                float camY = crosshair.camera.getOrthographic().position.y;
                float halfW = crosshair.camera.getOrthographic().viewportWidth / 2f;
                float halfH = crosshair.camera.getOrthographic().viewportHeight / 2f;

                float padding = 200f;

                float minX = camX - halfW + padding;
                float maxX = camX + halfW - padding;
                float minY = camY - halfH + padding;
                float maxY = camY + halfH - padding;

                float clampedX = Math.max(minX, Math.min(worldPos.x, maxX));
                float clampedY = Math.max(minY, Math.min(worldPos.y, maxY));

                if (crosshair.mouseX != (int) clampedX || crosshair.mouseY != (int) clampedY) {
                    crosshair.mouseX = (int) clampedX;
                    crosshair.mouseY = (int) clampedY;

                    if (this.context.getCurrentPlayer().equals(entity)) {
                        this.bus.publish(new UpdateMouseEvent(crosshair.mouseX, crosshair.mouseY));
                    }
                }
                
                if (worldPos.x != clampedX || worldPos.y != clampedY) {
                    Vector3 screenPos = new Vector3(clampedX, clampedY, 0);
                    crosshair.camera.getOrthographic().project(screenPos);

                    float clampedScreenX = Math.max(0, Math.min(screenPos.x, Gdx.graphics.getWidth() - 1));
                    float clampedScreenY = Math.max(0, Math.min(screenPos.y, Gdx.graphics.getHeight() - 1));

                    Gdx.input.setCursorPosition((int) clampedScreenX, Gdx.graphics.getHeight() - (int) clampedScreenY);
                }
            }
        }
    }

    @Override
    public void render() {
        for (Entity entity : this.entities) {
            CrosshairComponent crosshair = entity.getComponent(CrosshairComponent.class);

            if (crosshair != null) {
                this.batch.draw(
                    crosshair.texture,
                    crosshair.mouseX,
                    crosshair.mouseY,
                    crosshair.width,
                    crosshair.height
                );
            }
        }
    }
}
