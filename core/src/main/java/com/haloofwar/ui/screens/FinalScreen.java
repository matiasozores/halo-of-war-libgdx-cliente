package com.haloofwar.ui.screens;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.ui.Menu;

public class FinalScreen extends Menu {

	public FinalScreen(GameContext gameContext) {
		super(gameContext, "¡Muchas gracias por jugar a nuestro juego!", new String[] {"Volver al menu principal"}, Background.VICTORY);
	}

	@Override
	protected void processOption(int optionIndex) {
		switch (optionIndex) {
		case 0:
			this.context.disposeGameplay();
			this.dispose();
			this.context.getGame().setScreen(new MainMenuScreen(this.context));
			break;
		}
	}

}
