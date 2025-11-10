package com.haloofwar.game.cutscenes.scenes;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.SceneType;

public class LevelGameOverScene extends AbstractLevelScene {

    public LevelGameOverScene(GameContext context, SceneType nextScene) {
        super(
            context.getStaticCamera(),
            context.getRender().getBatch(),
            context.getTexture().get(Background.GAME_OVER),
            context.getGameplay().getBus(),
            nextScene
        );
    }
}
