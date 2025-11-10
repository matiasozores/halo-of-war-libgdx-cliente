package com.haloofwar.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haloofwar.common.enumerators.SpriteState;
import com.haloofwar.interfaces.ArmedEntityDescriptor;
import com.haloofwar.interfaces.StateController;

public class ArmedEntityAnimationStateController implements StateController {
    private AnimationSet animationSet;
    private Animation<TextureRegion> currentAnimation;
    private SpriteState currentState = SpriteState.IDLE;
    private float stateTime = 0f;
    private boolean facingLeft = false;
    private final PlayerComponent player;

    private boolean attackInProgress = false;

    // Guardamos el descriptor para poder regenerar el spritesheet
    private final ArmedEntityDescriptor descriptor;

    public ArmedEntityAnimationStateController(AnimationSet animationSet, PlayerComponent pc, ArmedEntityDescriptor descriptor) {
        this.animationSet = animationSet;
        this.currentAnimation = animationSet.getAnimation(SpriteState.IDLE);
        this.player = pc;
        this.descriptor = descriptor;
    }

    @Override
    public void update(float delta, float dirX, float dirY, boolean canMove) {
        this.stateTime += delta;
        
        if (player.isAttacking && !attackInProgress) {
            startAttack();
            player.isAttacking = false;
        }

        // 🗡️ Si hay un ataque en progreso, reproducirlo completo
        if (attackInProgress) {
            if (currentAnimation.isAnimationFinished(stateTime)) {
                attackInProgress = false;
                setState(SpriteState.IDLE);
            }
            return;
        }

        boolean moving = dirX != 0 || dirY != 0;

        if (canMove) {
            if (dirX < 0) facingLeft = true;
            else if (dirX > 0) facingLeft = false;

            if (moving) setState(SpriteState.WALK);
            else setState(SpriteState.IDLE);
        } else {
            setState(SpriteState.IDLE);
        }
    }

    private void startAttack() {
        attackInProgress = true;
        currentState = SpriteState.ATTACK;
        currentAnimation = animationSet.getAnimation(SpriteState.ATTACK);
        stateTime = 0f;
    }

    private void setState(SpriteState state) {
        if (this.currentState == state) return;
        this.currentState = state;
        this.currentAnimation = animationSet.getAnimation(state);
        this.stateTime = 0f;
    }

    public TextureRegion getCurrentFrame() {
        return currentAnimation.getKeyFrame(stateTime);
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    @Override
    public void changeState() {
        // No se usa aún
    }

    @Override
    public void changeSpritesheet(Texture newTexture) {
        // 🔁 Reemplaza las animaciones manteniendo el descriptor
        this.animationSet = new AnimationSet(newTexture, descriptor);
        this.currentAnimation = animationSet.getAnimation(currentState);
        this.stateTime = 0f;
    }
}
