package com.haloofwar.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haloofwar.common.enumerators.SpriteState;
import com.haloofwar.interfaces.AnimatedEntityDescriptor;
import com.haloofwar.interfaces.StateController;

public class ObjectAnimationStateController implements StateController {
    private AnimationSet animationSet;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;
    private boolean secondState = false;

    private final AnimatedEntityDescriptor descriptor;

    public ObjectAnimationStateController(AnimationSet animationSet, boolean isLastState, AnimatedEntityDescriptor descriptor) {
        this.animationSet = animationSet;
        this.currentAnimation = animationSet.getAnimation(SpriteState.IDLE);
        this.secondState = isLastState;
        this.descriptor = descriptor;
    }

    public void update(float delta, float dirX, float dirY, boolean canMove) {
        this.stateTime += delta;

        if (!this.secondState) {
            this.currentAnimation = this.animationSet.getAnimation(SpriteState.WALK);
        } else {
            this.currentAnimation = this.animationSet.getAnimation(SpriteState.IDLE);
        }
    }

    public TextureRegion getCurrentFrame() {
        return this.currentAnimation.getKeyFrame(stateTime);
    }
    
    @Override
    public boolean isFacingLeft() {
        return false;
    }

    @Override
    public void changeState() {
        this.secondState = true;
    }

    @Override
    public void changeSpritesheet(Texture newTexture) {
        // 🔁 Crea nuevas animaciones usando el descriptor guardado
        this.animationSet = new AnimationSet(newTexture, descriptor);
        this.currentAnimation = animationSet.getAnimation(SpriteState.IDLE);
        this.stateTime = 0f;
    }
}
