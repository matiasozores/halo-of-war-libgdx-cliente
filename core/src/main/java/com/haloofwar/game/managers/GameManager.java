package com.haloofwar.game.managers;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.engine.events.ChangeSceneEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.ui.screens.PauseMenuScreen;

public class GameManager implements Screen {

    private final GameFlowManager flow;
    private final GameEventSubscriber subscriber;
    
    public GameManager(final GameFlowManager flow, final GameEventSubscriber subscriber, final PauseMenuScreen pause) {
        this.flow = flow;
        this.subscriber = subscriber;
        this.initializePause(pause);
        this.initializeScene(subscriber.getBus());
    }

    private void initializePause(PauseMenuScreen pause) {
    	pause.setManager(this);
    	this.subscriber.setPauseMenu(pause);
    }
    
    private void initializeScene(final EventBus bus) {
        bus.publish(new ChangeSceneEvent(LevelSceneType.TUTORIAL));
    }
    
    @Override
    public void show() { 
       	if(this.flow.getCurrentScene() != null) {
    		this.flow.getCurrentScene().show(); 
    	}
    }
    
    @Override
    public void render(float delta) {
    	if(this.flow != null) {
            this.flow.update(delta);
            this.flow.render(delta);
    	} else {
    		System.out.println("Ha ocurrido un error al renderizar... el GameFlowManager es nulo.");
    		return;
    	}
    }


    @Override
    public void resize(int width, int height) { 
    	if(this.flow.getCurrentScene() != null) {
    		this.flow.getCurrentScene().resize(width, height);
    	}
    }
    
    @Override
    public void pause() { 
    	if(this.flow.getCurrentScene() != null) {
    		this.flow.getCurrentScene().pause();
    	}
    }
    
    @Override
    public void resume() {
    	if(this.flow.getCurrentScene() != null) {
    		this.flow.getCurrentScene().resume(); 
    	}
    }
    
    @Override
    public void hide() { 
    	if(this.flow.getCurrentScene() != null) {
    		this.flow.getCurrentScene().hide(); 
    	}
    }
    
    @Override
    public void dispose() { 	
    	this.flow.dispose();
    	this.subscriber.dispose();
    }
}
