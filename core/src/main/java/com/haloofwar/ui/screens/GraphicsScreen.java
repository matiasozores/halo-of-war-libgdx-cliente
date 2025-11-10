package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class GraphicsScreen extends Menu {
	
    public GraphicsScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Graficos", new String[] {
            "Resolucion",
            "Volver"
        }, previousScreen, Background.PORTAL_MENU);
    }
    
    public GraphicsScreen(final GameContext context, final Screen previousScreen, final GameController controller) {
        super(context, "Graficos", new String[] {
                "Resolucion",
                "Volver"
            }, Background.PORTAL_MENU, previousScreen, controller);
    }
    
    public GraphicsScreen(final GameContext context) {
        this(context, null);
    }

    @Override
    protected void processOption(int optionIndex) {
        switch (optionIndex) {
            case 0:
                this.context.getGame().setScreen(new ResolutionScreen(this.context, this, this.controller));
                break;
            case 1:
                this.goBack();
                break;
            default:
                break;
        }
    }
}
