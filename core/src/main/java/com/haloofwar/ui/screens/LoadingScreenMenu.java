package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.engine.events.SetScreenEvent;
import com.haloofwar.ui.Menu;

public class LoadingScreenMenu extends Menu {

	public LoadingScreenMenu(GameContext gameContext, Screen previousScene) {
		super(gameContext, "", new String[] {}, previousScene, Background.LOADING);
		this.listenerManager.add(this.context.getGameplay().getBus(), SetScreenEvent.class, this::onBack);
	}
	
	private void onBack(SetScreenEvent event) {
		this.goBack();
	}

	@Override
	protected void processOption(int optionIndex) {
	}
	
	
}
