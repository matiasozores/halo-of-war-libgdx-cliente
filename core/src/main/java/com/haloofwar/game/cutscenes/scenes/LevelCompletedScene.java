package com.haloofwar.game.cutscenes.scenes;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.SceneType;

public class LevelCompletedScene extends AbstractLevelScene {

    public LevelCompletedScene(GameContext context, SceneType nextScene) {
        super(
        	context.getStaticCamera(), 
        	context.getRender().getBatch(), 
        	context.getTexture().get(Background.VICTORY), 
        	context.getGameplay().getBus(),
        	nextScene
        	);
    }
}
