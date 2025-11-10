package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class ControlsScreen extends Menu {

    public ControlsScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Controles", new String[] {
            "Moverse: Flechas",
            "Atacar: Click izquierdo",
            "Interactuar: E",
            "Inventario: I",
            "Volver"
        }, previousScreen, Background.PORTAL_MENU);
    }
    
    public ControlsScreen(final GameContext context, final Screen previousScreen, GameController controller) {
        super(context, "Controles", new String[] {
            "Moverse: Flechas",
            "Atacar: Click izquierdo",
            "Interactuar: E",
            "Inventario: I",
            "Volver"
        }, Background.PORTAL_MENU, previousScreen, controller);
    }

    @Override
    protected void processOption(int optionIndex) {
        if (optionIndex == 4) {
            this.goBack();
        }
    }
}
