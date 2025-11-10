package com.haloofwar.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haloofwar.interfaces.StateController;

public class AnimationComponent implements Component {
    private final StateController stateController;
    public float flashTime;

    public AnimationComponent(StateController controller) {
    	this.stateController = controller;
    }

    public void update(float delta, float dirX, float dirY, boolean canMove) {
        this.stateController.update(delta, dirX, dirY, canMove);
    }
    
    public TextureRegion getCurrentFrame() {
        return this.stateController.getCurrentFrame();
    }

    public boolean isFacingLeft() {
        return this.stateController.isFacingLeft();
    }
    
    public StateController getStateController() {
		return this.stateController;
	}
    
    public void changeSpritesheet(Texture newTexture) {
		this.stateController.changeSpritesheet(newTexture);	
    }
}
