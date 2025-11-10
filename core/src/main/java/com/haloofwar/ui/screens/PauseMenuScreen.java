package com.haloofwar.ui.screens;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.engine.events.GameStateEvent;
import com.haloofwar.game.managers.GameManager;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class PauseMenuScreen extends Menu {

    private GameManager manager;

    public PauseMenuScreen(final GameContext context, final GameManager manager, final GameController controller) {
        super(context, "Menu Pausa", new String[] {
                "Reanudar", "Configuracion", "Desconectarse de la partida y volver al menu principal"
        }, Background.MAIN_MENU, manager, controller);
        
        this.manager = manager;
    }
    
    public PauseMenuScreen(final GameContext context, final GameController controller) {
        this(context, null, controller);
    }
    
    public void setManager(final GameManager manager) {
    	if(this.manager != null) {
    		return;
    	}
    	
    	this.manager = manager;
    	this.previousScreen = manager;
    }

    @Override
    protected void processOption(int optionIndex) {
        switch(optionIndex) {
            case 0: 
                this.goBack();
                break;
            case 1: 
                this.context.getGame().setScreen(new SettingsScreen(this.context, this, this.controller));
                break;
            case 2: 
                this.context.getAudio().getMusic().stop();
                this.context.getAudio().getSound().stopAll();
            	this.manager.dispose();
                this.context.disposeGameplay();
                this.dispose();
                this.context.getGame().setScreen(new MainMenuScreen(this.context));
                break;
        }
    }
    
    @Override
    public void goBack() {
    	super.goBack();
    	this.context.getGameplayBus().publish(new GameStateEvent(GameState.PLAYING));
    }
}