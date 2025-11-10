package com.haloofwar.game.managers;

import java.util.Set;

import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.game.scenes.ExplorationScene;
import com.haloofwar.game.world.WorldCollisionInitializer;
import com.haloofwar.interfaces.Scene;

public class GameFlowManager {

    private Scene currentScene;
    public GameState currentState;
    private final GameplayContext gameplayContext;
    private final TextureManager texture;
    
    private final Set<LevelSceneType> lockedLevels;
    
    public GameFlowManager(final TextureManager texture, final GameplayContext gameplayContext, final Set<LevelSceneType> lockedLevels) {
    	this.gameplayContext = gameplayContext;
        this.currentState = GameState.PLAYING;
        this.texture = texture;
        this.lockedLevels = lockedLevels;
    }

    public void update(float delta) {
        if (this.currentScene != null) {
            this.currentScene.update(delta);
        }
    }

    public void render(float delta) {
        if (this.currentScene != null) {
        	this.currentScene.render(delta);
        }
    }

    public void changeScene(final Scene newScene) {
        if (this.currentScene != null) {
            this.currentScene.dispose();
            this.currentScene.hide();
        }
        this.currentScene = newScene;
        if (this.currentScene != null) {
            this.currentScene.show();
            this.currentScene.reset();
            this.currentScene.reconfigureCamera();
            if (this.currentScene.getWorld() != null) {
            	WorldCollisionInitializer.initializeMapColliders(this.lockedLevels, this.currentScene.getWorld().getMap(), this.texture, this.gameplayContext.getBus());
            	
            	if(newScene instanceof ExplorationScene exploration) {
                	exploration.respawn();
            	}
            }
        }

        this.currentState = GameState.PLAYING;
    }
    
    public void resetCurrentScene() {
		if(this.currentScene != null) {
			this.currentScene.reset();
		}
	}
    
    public Set<LevelSceneType> getLockedLevels() {
		return this.lockedLevels;
	}
    
    public void setGameState(final GameState STATE) {
    	this.currentState = STATE;
    }

    public GameState getCurrentState() {
        return this.currentState;
    }
    
    public Scene getCurrentScene() {
		return this.currentScene;
	}
    
    public void dispose() {
		this.currentScene.dispose();
    }
}
