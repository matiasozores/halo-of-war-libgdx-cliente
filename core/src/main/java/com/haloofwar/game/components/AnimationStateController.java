package com.haloofwar.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haloofwar.common.enumerators.SpriteState;
import com.haloofwar.interfaces.AnimatedEntityDescriptor;
import com.haloofwar.interfaces.ArmedEntityDescriptor;
import com.haloofwar.interfaces.EntityDescriptor;
import com.haloofwar.interfaces.StateController;

public class AnimationStateController implements StateController {
    private AnimationSet animationSet;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;
    private boolean facingLeft = false;

    // Guardamos referencia al descriptor (puede ser armado o animado)
    private final EntityDescriptor descriptor;

    public AnimationStateController(AnimationSet animationSet, EntityDescriptor descriptor) {
        this.animationSet = animationSet;
        this.descriptor = descriptor;
        this.currentAnimation = animationSet.getAnimation(SpriteState.IDLE);
    }

    @Override
    public void update(float delta, float dirX, float dirY, boolean canMove) {
        this.stateTime += delta;

        boolean moving = dirX != 0 || dirY != 0;

        if (canMove) {
            if (dirX < 0) this.facingLeft = true;
            else if (dirX > 0) this.facingLeft = false;

            if (moving) {
                this.currentAnimation = this.animationSet.getAnimation(SpriteState.WALK);
            } else {
                this.currentAnimation = this.animationSet.getAnimation(SpriteState.IDLE);
            }
        } else {
            this.currentAnimation = this.animationSet.getAnimation(SpriteState.IDLE);
        }
    }

    @Override
    public TextureRegion getCurrentFrame() {
        return this.currentAnimation.getKeyFrame(stateTime);
    }

    @Override
    public boolean isFacingLeft() {
        return this.facingLeft;
    }

    @Override
    public void changeState() {
        // (no necesario de momento)
    }

    @Override
    public void changeSpritesheet(Texture newTexture) {
        // Reconstruimos el AnimationSet usando el descriptor original
        if (descriptor instanceof ArmedEntityDescriptor) {
            this.animationSet = new AnimationSet(newTexture, (ArmedEntityDescriptor) descriptor);
        } else if (descriptor instanceof AnimatedEntityDescriptor) {
            this.animationSet = new AnimationSet(newTexture, (AnimatedEntityDescriptor) descriptor);
        } else {
            System.out.println("No se pudo cambiar el spritesheet: descriptor desconocido.");
            return;
        }

        // Reiniciamos el tiempo de animación y volvemos a la animación IDLE
        this.currentAnimation = this.animationSet.getAnimation(SpriteState.IDLE);
        this.stateTime = 0f;
    }
}
