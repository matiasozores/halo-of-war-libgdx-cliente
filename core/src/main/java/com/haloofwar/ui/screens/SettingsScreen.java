package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class SettingsScreen extends Menu {

	
    public SettingsScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Configuracion",new String[] {
            "Graficos",
            "Musica y sonidos",
            "Controles",
            "Volver"
        }, previousScreen, Background.PORTAL_MENU);
    }

    public SettingsScreen(final GameContext context, final Screen previousScreen, GameController controller) {
    	super(context, "Configuracion",new String[] {
                "Graficos",
                "Musica y sonidos",
                "Controles",
                "Volver"
            },  Background.PORTAL_MENU, previousScreen, controller);
    }
    
    public SettingsScreen(GameContext gameContext) {
        this(gameContext, null);
    }
    
    @Override
    protected void processOption(int optionIndex) {
        switch (optionIndex) {
            case 0:
                this.context.getGame().setScreen(new GraphicsScreen(this.context, this, this.controller));
                break;
            case 1:
                this.context.getGame().setScreen(new AudioScreen(this.context, this, this.controller));
                break;
            case 2:
                this.context.getGame().setScreen(new ControlsScreen(this.context, this, this.controller));
                break;
            case 3:
                this.goBack();
                break;
            default:
                break;
        }
    }
}
