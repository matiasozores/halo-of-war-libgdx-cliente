package com.haloofwar.ui.screens;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.ui.Menu;

public class DisconnectionMenuScreen extends Menu {
	
    public DisconnectionMenuScreen(final GameContext context) {
        super(context, "Ha ocurrido un problema con el servidor...",new String[] {
        		"Volver al menu"
        }, null, Background.LOADING);
        
        this.context.getAudio().getMusic().stop();
        this.context.getAudio().getSound().stopAll();
    }
    
    
    @Override
    protected void processOption(int optionIndex) {
    	switch (optionIndex) {
		case 0:
			this.context.disposeGameplay();
			this.dispose();
			this.context.getGame().setScreen(new MainMenuScreen(context));
			break;

		default:
			break;
		}
    }
}
