package com.haloofwar.common.context;

import com.haloofwar.common.managers.AudioManager;
import com.haloofwar.common.managers.InputManager;
import com.haloofwar.common.managers.RenderManager;
import com.haloofwar.common.managers.SceneManager;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.cameras.GameWorldCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.config.FactoryCollection;
import com.haloofwar.game.systems.AudioSystem;
import com.haloofwar.launcher.HaloOfWarPrincipal;

public class GameContext {
    private final HaloOfWarPrincipal game;

    private final TextureManager texture;
    private final AudioManager audio;
    private final RenderManager render;

    private final InputManager input;

    private final GameStaticCamera staticCamera;
    private final GameWorldCamera worldCamera;

    private GameplayContext gameplay;
    private FactoryCollection factories;
    private SceneManager scene;

    private final AudioSystem audioSystem; 

    private final EventBus globalBus;
    private final EventBus gameplayBus;
    
    public GameContext(final HaloOfWarPrincipal game) {
        this.game = game;

        this.texture = new TextureManager();
        this.audio = new AudioManager();
        this.render = new RenderManager();
        
        this.globalBus = new EventBus();
        this.gameplayBus = new EventBus();
        
        this.input = new InputManager(this.globalBus, this.gameplayBus);
        
        this.staticCamera = new GameStaticCamera();
        this.worldCamera = new GameWorldCamera();
        this.audioSystem = new AudioSystem(this.audio, this.globalBus, this.gameplayBus);
    }
    
    public void createGameplay() {
    	this.input.subscribeEvents();
    	this.audioSystem.subscribeGameplayEvents();
        this.gameplay = new GameplayContext(this.render.getBatch(), this.texture, this.gameplayBus, this.worldCamera);
        this.factories = new FactoryCollection(this);
        this.scene = new SceneManager(this.factories.getSceneFactory());
    }


    public HaloOfWarPrincipal getGame() {
        return this.game;
    }

    public TextureManager getTexture() {
        return this.texture;
    }

    public AudioManager getAudio() {
        return this.audio;
    }

    public RenderManager getRender() {
        return this.render;
    }

    public InputManager getInput() {
        return this.input;
    }

    public GameStaticCamera getStaticCamera() {
        return this.staticCamera;
    }

    public GameWorldCamera getWorldCamera() {
        return this.worldCamera;
    }

    public GameplayContext getGameplay() {
        return this.gameplay;
    }

    public FactoryCollection getFactories() {
        return this.factories;
    }

    public SceneManager getScene() {
        return this.scene;
    }

    public EventBus getGlobalBus() {
        return this.globalBus;
    }
    
    public EventBus getGameplayBus() {
		return this.gameplayBus;
	}

    public AudioSystem getAudioSystem() {
        return this.audioSystem;
    }
    
    public void disposeGameplay() {
    	this.gameplay.dispose();
    	this.audioSystem.disposeGameplay();
    	this.input.dispose();
    }
    
    public void dispose() {
        if(this.gameplay != null) {
        	this.gameplay.dispose();	
        }
    	
        if(this.scene != null) {
        	this.scene.clear();	
        }
        
        this.render.dispose();
        this.texture.dispose();
        this.audio.dispose();
        this.audioSystem.dispose();
        this.gameplayBus.clear();
        this.globalBus.clear();
    }
}
