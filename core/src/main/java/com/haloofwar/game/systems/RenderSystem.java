package com.haloofwar.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.components.AnimationComponent;
import com.haloofwar.game.components.RenderComponent;
import com.haloofwar.game.components.TransformComponent;
import com.haloofwar.interfaces.Renderable;

public class RenderSystem extends EntitySystem implements Renderable {

    private final SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void register(Entity e) {
        if (e.hasComponent(TransformComponent.class) &&
            (e.hasComponent(AnimationComponent.class) || e.hasComponent(RenderComponent.class))) {
            super.register(e);
        }
    }

    @Override
    public void render() {
        for (Entity e : this.entities) {
            TransformComponent transform = e.getComponent(TransformComponent.class);

            if (transform != null) {
                float x = transform.x;
                float y = transform.y;
                float width = transform.width;
                float height = transform.height;

                RenderComponent render = e.getComponent(RenderComponent.class);

                if (render != null && render.flashTime > 0) {
                    render.flashTime -= Gdx.graphics.getDeltaTime();
                    if (render.flashTime < 0) render.flashTime = 0;
                }

                if (render != null && render.flashTime > 0) {
                    batch.setColor(1, 0.3f, 0.3f, 1);
                } else {
                    batch.setColor(1, 1, 1, 1);
                }

                if (render != null && render.texture != null) {
                    this.batch.draw(
                        render.texture,
                        transform.x, transform.y,
                        transform.width / 2f, transform.height / 2f,
                        transform.width, transform.height,
                        1f, 1f,
                        render.angle,
                        0, 0,
                        render.texture.getWidth(),
                        render.texture.getHeight(),
                        false, false
                    );
                }

                AnimationComponent anim = e.getComponent(AnimationComponent.class);
                if (anim != null) {

                    if (anim.flashTime > 0) {
                        anim.flashTime -= Gdx.graphics.getDeltaTime();
                        if (anim.flashTime < 0) anim.flashTime = 0;
                    }

                    if (anim.flashTime > 0) {
                        batch.setColor(1f, 0.3f, 0.3f, 1f); 
                    } else {
                        batch.setColor(1f, 1f, 1f, 1f); 
                    }

                    TextureRegion frame = anim.getCurrentFrame();
                    if (frame != null) {
                        boolean facingLeft = anim.isFacingLeft();
                        if (facingLeft) {
                            this.batch.draw(frame, x + width, y, -width, height);
                        } else {
                            this.batch.draw(frame, x, y, width, height);
                        }
                    }

                    this.batch.setColor(1f, 1f, 1f, 1f);
                }

                this.batch.setColor(1, 1, 1, 1);
            }
        }
    }
}
