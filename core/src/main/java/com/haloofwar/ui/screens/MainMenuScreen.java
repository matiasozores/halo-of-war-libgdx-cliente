package com.haloofwar.ui.screens;

import com.badlogic.gdx.Gdx;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.MusicTrack;
import com.haloofwar.engine.events.PlayMusicEvent;
import com.haloofwar.ui.Menu;

public class MainMenuScreen extends Menu{

	public MainMenuScreen(final GameContext context) {
		super(context, "Menu Principal",new String[] {
				"Jugar online",
				"Configuracion",
				"Salir"
			}, null, Background.MAIN_MENU);
		
        this.context.getAudio().getMusic().stop();
        this.context.getAudio().getSound().stopAll();
        this.context.getGlobalBus().publish(new PlayMusicEvent(MusicTrack.MENU));
	}
	
	@Override
	protected void processOption(int optionIndex) {
	    switch (optionIndex) {
	    	case 0: 
            this.context.getGame().setScreen(new PlayOnlineMenuScreen(this.context, this));
            break;
            
	        case 1: 
	            this.context.getGame().setScreen(new SettingsScreen(this.context, this));
	            break;
	        case 2: 
	            Gdx.app.exit();
	            break;
	        default:
	            break;
	    }
	}
}
