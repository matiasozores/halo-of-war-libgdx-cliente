package com.haloofwar.game.managers;

import java.util.HashSet;
import java.util.Set;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.enumerators.SoundType;
import com.haloofwar.common.managers.SceneManager;
import com.haloofwar.engine.events.CanMoveEvent;
import com.haloofwar.engine.events.ChangeCurrentPlayerEvent;
import com.haloofwar.engine.events.ChangeSceneEvent;
import com.haloofwar.engine.events.DisconnectEvent;
import com.haloofwar.engine.events.EndGameEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.FinishGameEvent;
import com.haloofwar.engine.events.GameOverEvent;
import com.haloofwar.engine.events.GameStateEvent;
import com.haloofwar.engine.events.LevelCompletedEvent;
import com.haloofwar.engine.events.LevelEnterEvent;
import com.haloofwar.engine.events.PeacefulEvent;
import com.haloofwar.engine.events.PlayMusicEvent;
import com.haloofwar.engine.events.PlaySoundEvent;
import com.haloofwar.engine.events.PortalChangeStateEvent;
import com.haloofwar.engine.events.StopMusicEvent;
import com.haloofwar.engine.events.StopSoundsEvent;
import com.haloofwar.engine.events.UnlockLevelEvent;
import com.haloofwar.game.cutscenes.scenes.LevelCompletedScene;
import com.haloofwar.game.cutscenes.scenes.LevelGameOverScene;
import com.haloofwar.interfaces.Scene;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.screens.DisconnectionMenuScreen;
import com.haloofwar.ui.screens.FinalScreen;
import com.haloofwar.ui.screens.MainMenuScreen;
import com.haloofwar.ui.screens.PauseMenuScreen;

public class GameEventSubscriber {
	private final EventListenerManager listenerManager = new EventListenerManager();
    private final EventBus bus;
    private final GameFlowManager flow;
    private final LevelCompletedScene completedScene;
    private final LevelGameOverScene gameOverScene;
    private final SceneManager sceneManager;
    private final Set<LevelSceneType> completedLevels;
    private final Set<LevelSceneType> lockedLevels;
    private final GameContext context;
    private final GameController controller;
    private PauseMenuScreen pause;
    
    public GameEventSubscriber(
		final GameFlowManager flowManager, 
		final EventBus bus,
		final GameContext context, 
		final LevelCompletedScene completedScene, 
		final LevelGameOverScene gameOverScene, 
		final GameController controller
	) {
        this.flow = flowManager;
        this.bus = context.getGameplay().getBus();
        this.sceneManager = context.getScene();

        this.completedLevels = new HashSet<LevelSceneType>();
        this.lockedLevels = this.flow.getLockedLevels();
        this.completedScene = completedScene;
        this.gameOverScene = gameOverScene;
        this.subscribeEvents();
        
        this.context = context;
        this.controller = controller;
    }
   
    public void setPauseMenu(PauseMenuScreen pause) {
    	if(this.pause != null) {
    		return;
    	}
    	
    	this.pause = pause;
    }
    
    private void subscribeEvents() {
        this.listenerManager.add(this.bus, ChangeSceneEvent.class, this::onChangeScene);
        this.listenerManager.add(this.bus, LevelEnterEvent.class, this::onEnterLevel);
        this.listenerManager.add(this.bus, GameOverEvent.class, this::onPlayerDied);
        this.listenerManager.add(this.bus, LevelCompletedEvent.class, this::onLevelCompleted);
        this.listenerManager.add(this.bus, UnlockLevelEvent.class, this::unlockLevel);
        this.listenerManager.add(this.bus, GameStateEvent.class, this::onGameStateEvent);
        this.listenerManager.add(this.bus, EndGameEvent.class, this::onEndGame);
        this.listenerManager.add(this.bus, FinishGameEvent.class, this::onFinishGame);
        this.listenerManager.add(this.bus, DisconnectEvent.class, this::onDisconnect);
        this.bus.publish(new GameStateEvent(this.flow.getCurrentState()));
    }
    
    private void onGameStateEvent(GameStateEvent event) {
    	this.flow.currentState = event.getState();
    	this.bus.publish(new CanMoveEvent(!(event.getState().equals(GameState.PAUSED))));

        if (event.getState().equals(GameState.PAUSED)) {
            this.context.getGame().setScreen(this.pause);
        }
    }
    
    private void onDisconnect(DisconnectEvent event) {
    	this.controller.dispose();
        this.context.getGame().setScreen(new DisconnectionMenuScreen(this.context));    
    }
    
    private void onEndGame(EndGameEvent event) {
    	this.flow.dispose();
    	this.dispose();
    	this.context.disposeGameplay();
    	this.context.getGame().setScreen(new MainMenuScreen(this.context));
    }
    
    private void onFinishGame(FinishGameEvent event) {
		this.flow.dispose();
    	this.dispose();
		this.context.disposeGameplay();
		this.context.getGame().setScreen(new FinalScreen(this.context));
	}

    private void unlockLevel(final UnlockLevelEvent event) {
    	if(this.lockedLevels.contains(event.type)) {
    		this.lockedLevels.remove(event.type);
    	}
    }

    private void onEnterLevel(final LevelEnterEvent event) {
        if (event == null || event.type == null || this.completedLevels.contains(event.type)) {
        	return;
        }
        
        final Scene targetScene = this.sceneManager.get(event.type);
        this.bus.publish(new PeacefulEvent(!event.type.isLevel()));
        
		if (targetScene != null) {
        	this.bus.publish(new ChangeSceneEvent(event.type));
        }
    }

    private void onPlayerDied(final GameOverEvent event) {        
        this.bus.publish(new StopSoundsEvent());
        this.bus.publish(new StopMusicEvent());
        this.bus.publish(new PlaySoundEvent(SoundType.GAME_OVER));
        this.bus.publish(new PeacefulEvent(!this.gameOverScene.isLevel()));
        this.flow.changeScene(this.gameOverScene);
    }

    private void onLevelCompleted(final LevelCompletedEvent event) {
    	this.bus.publish(new StopMusicEvent());
        this.bus.publish(new PlayMusicEvent(this.completedScene.getMusic()));
        
        if (event.getLevelType() != null) {
        	this.completedLevels.add(event.getLevelType());
        }
        
    	this.bus.publish(new UnlockLevelEvent(event.getLevelType().getNextLevel()));
    	this.bus.publish(new PortalChangeStateEvent(event.getLevelType()));

        this.bus.publish(new PeacefulEvent(!this.completedScene.isLevel()));
        this.bus.publish(new PortalChangeStateEvent(event.getLevelType()));
        
        this.flow.changeScene(this.completedScene);
    }

    private void onChangeScene(final ChangeSceneEvent event) {
        this.bus.publish(new PeacefulEvent(!event.getNextScene().isLevel()));
        
        final Scene nextScene = this.sceneManager.get(event.getNextScene());
        
        this.flow.changeScene(nextScene);
        this.bus.publish(new ChangeCurrentPlayerEvent());
        this.bus.publish(new ChangeCurrentPlayerEvent());
        this.bus.publish(new StopMusicEvent());
        this.bus.publish(new PlayMusicEvent(event.getNextScene().getScene().getMusic()));
    }

    public Set<LevelSceneType> getCompletedLevels() {
        return this.completedLevels;
    }

	public void dispose() {
		this.listenerManager.clear();
		this.controller.dispose();
	}
	
	public EventBus getBus() {
		return this.bus;
	}
}
