package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.engine.events.AlreadyExistsGameEvent;
import com.haloofwar.engine.events.ConnectEvent;
import com.haloofwar.engine.events.DisconnectEvent;
import com.haloofwar.engine.events.StartGameEvent;
import com.haloofwar.game.managers.GameManager;
import com.haloofwar.launcher.GameInitializer;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class WaitingMenuScreen extends Menu {
	
    public WaitingMenuScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Intentando conectar a un servidor...",new String[] {
        		"Desconectarse y volver al menu"
        }, previousScreen, Background.LOADING);
        
        this.listenerManager.add(context.getGlobalBus(), ConnectEvent.class, this::onConnect);
        this.listenerManager.add(context.getGlobalBus(), AlreadyExistsGameEvent.class, this::onAlreadyExistsGame);
        this.listenerManager.add(context.getGlobalBus(), DisconnectEvent.class, this::onDisconnect);
        this.listenerManager.add(context.getGlobalBus(), StartGameEvent.class, this::onStartGameOnline);
    }
    
    public void setController(GameController controller) {
    	this.controller = controller;
    }
    
    private void onAlreadyExistsGame(AlreadyExistsGameEvent event) {
    	this.updateTitle("No se ha podido crear la partida porque ya existe una... \n Vuelve a intentarlo mas tarde");
    }
    
    private void onConnect(ConnectEvent event) {
    	this.updateTitle(event.text);
    }
    
    private void onDisconnect(DisconnectEvent event) {
    	this.controller.dispose();
        this.context.getGame().setScreen(new DisconnectionMenuScreen(this.context));
    }
    
    private void onStartGameOnline(StartGameEvent event) {
       	this.listenerManager.clear();
    	this.context.createGameplay();
		GameManager manager = GameInitializer.initializeOnlineGameManager(event.kratosId, event.masterchiefId, this.context, this.controller, event.playerType);
		
		this.context.getGame().setScreen(manager);
    }
    
    @Override
    protected void processOption(int optionIndex) {
    	switch (optionIndex) {
		case 0:
			this.dispose();
			this.goBack();
			break;

		default:
			break;
		}
    }
}
