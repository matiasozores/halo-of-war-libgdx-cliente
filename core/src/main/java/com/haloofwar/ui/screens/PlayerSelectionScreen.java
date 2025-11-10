package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.launcher.GameInitializer;
import com.haloofwar.ui.Menu;

public class PlayerSelectionScreen extends Menu {

    private final PlayerType[] PLAYER_OPTIONS = {
        PlayerType.KRATOS,      
        PlayerType.MASTER_CHIEF  
    };

    private final int BACK_OPTION = 2;

    public PlayerSelectionScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Seleccion de jugador", new String[] {
            "Kratos",
            "Master Chief",
            "Volver"
        }, previousScreen, Background.PLAYER_SELECTION);
    }

    @Override
    protected void processOption(int optionIndex) {
        if (optionIndex == this.BACK_OPTION) {
            this.goBack();
            return;
        }

        if (optionIndex < 0 || optionIndex >= this.PLAYER_OPTIONS.length) {
            return;
        }


       GameInitializer.initializeGameplayCreateGame(this.context, this.PLAYER_OPTIONS[optionIndex]);
    }    
}
