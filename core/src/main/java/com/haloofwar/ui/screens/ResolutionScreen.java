package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class ResolutionScreen extends Menu {

    public ResolutionScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Resoluciones",new String[]{
                "800x600",
                "1280x720",
                "1600x900",
                "Pantalla completa",
                "Volver"
        }, previousScreen, Background.PORTAL_MENU);
    }
    
    public ResolutionScreen(final GameContext CONTEXT, final Screen previousScreen, GameController controller) {
        super(CONTEXT, "Resoluciones",new String[]{
                "800x600",
                "1280x720",
                "1600x900",
                "Pantalla completa",
                "Volver"
        }, Background.PORTAL_MENU, previousScreen, controller);
    }
    
    

    @Override
    protected void processOption(int optionIndex) {
        switch (optionIndex) {
            case 0:
                this.context.getGame().setResolution(800, 600);
                break;
            case 1:
                this.context.getGame().setResolution(1280, 720);
                break;
            case 2:
                this.context.getGame().setResolution(1600, 900);
                break;
            case 3:
                this.context.getGame().setFullscreen();
                break;
            case 4:
                this.goBack(); 
                break;
            default:
                break;
        }
    }
}
