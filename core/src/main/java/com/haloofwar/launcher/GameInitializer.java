package com.haloofwar.launcher;

import java.util.Set;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.SceneType;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.cutscenes.scenes.LevelCompletedScene;
import com.haloofwar.game.cutscenes.scenes.LevelGameOverScene;
import com.haloofwar.game.managers.GameEventSubscriber;
import com.haloofwar.game.managers.GameFlowManager;
import com.haloofwar.game.managers.GameManager;
import com.haloofwar.network.ClientGameController;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.screens.JoinOnlineMenuScreen;
import com.haloofwar.ui.screens.MainMenuScreen;
import com.haloofwar.ui.screens.PauseMenuScreen;
import com.haloofwar.ui.screens.WaitingMenuScreen;

public class GameInitializer {

    public static void initializeGameplayCreateGame(final GameContext context, PlayerType chosenType) {   	
    	final WaitingMenuScreen screen = new WaitingMenuScreen(context, new MainMenuScreen(context));
    	GameController controller = new ClientGameController(context.getGameplayBus(), context.getGlobalBus(), context.getTexture(), true, chosenType);
    	screen.setController(controller);
    	context.getGame().setScreen(screen);
    }
    
    public static void initializeGameplayJoinGame(final GameContext context) {   	
    	final JoinOnlineMenuScreen screen = new JoinOnlineMenuScreen(context, new MainMenuScreen(context));
    	GameController controller = new ClientGameController(context.getGameplayBus(), context.getGlobalBus(), context.getTexture(), false);
    	screen.setController(controller);
    	context.getGame().setScreen(screen);
    }
    
    public static Entity intializePlayer(final int IDENTIFIER, final PlayerType CURRENT_TYPE, final GameContext context, boolean isRemote) {
       	Entity player = context.getFactories().getPlayerFactory().create(IDENTIFIER, CURRENT_TYPE, isRemote);
       	return player;
    }

    public static GameManager initializeOnlineGameManager(final int kratosId, final int masterchiefId, final GameContext context, final GameController controller, final PlayerType CURRENT_TYPE) {
    	final Set<LevelSceneType> lockedLevels = LevelSceneType.getAllLockedLevels();
    	
       	final Entity player1;
       	final Entity player2;
       	
       	if(CURRENT_TYPE.equals(PlayerType.KRATOS)) {
       		player1 = context.getFactories().getPlayerFactory().create(kratosId, CURRENT_TYPE, true);
	   		player2 = context.getFactories().getPlayerFactory().create(masterchiefId, PlayerType.MASTER_CHIEF, false);
	   		
	   	} else if(CURRENT_TYPE.equals(PlayerType.MASTER_CHIEF)) {
	   		player1 = context.getFactories().getPlayerFactory().create(masterchiefId, CURRENT_TYPE, true);
	   		player2 = context.getFactories().getPlayerFactory().create(kratosId, PlayerType.KRATOS, false);
	   	} else {
	   		System.out.println("Ha ocurrido un error al crear los jugadores en el juego online");
	   		return null;
	   	}
    	
    	GameplayContext gameplay = context.getGameplay();
    	gameplay.initializePlayers(player1, player2);

    	
    	final GameFlowManager flow = new GameFlowManager(context.getTexture(), context.getGameplay(), lockedLevels);
    	
    	final LevelCompletedScene completedScene = new LevelCompletedScene(context,SceneType.MAIN);
    	final LevelGameOverScene gameOverScene = new LevelGameOverScene(context, SceneType.MAIN);
    	
    	final GameEventSubscriber subscriber = new GameEventSubscriber(flow, context.getGameplay().getBus(), context, completedScene, gameOverScene, controller);
    	return new GameManager(flow, subscriber, new PauseMenuScreen(context, controller));
    }
}
