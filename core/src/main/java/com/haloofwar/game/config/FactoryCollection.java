package com.haloofwar.game.config;

import com.haloofwar.common.context.GameContext;
import com.haloofwar.game.factories.CutSceneFactory;
import com.haloofwar.game.factories.EnemyFactory;
import com.haloofwar.game.factories.HUDFactory;
import com.haloofwar.game.factories.NPCFactory;
import com.haloofwar.game.factories.PlayerFactory;
import com.haloofwar.game.factories.SceneFactory;

public class FactoryCollection {
	private final HUDFactory hudFactory;
	private final PlayerFactory playerFactory;
	private final SceneFactory sceneFactory;
	private final NPCFactory npcFactory;
	private final EnemyFactory enemyFactory;
	private final CutSceneFactory cutsceneFactory;	
	
	public FactoryCollection(final GameContext context) {
		this.hudFactory = new HUDFactory(context);
		this.enemyFactory = new EnemyFactory(context);
		this.playerFactory = new PlayerFactory(context);
		this.cutsceneFactory = new CutSceneFactory(context);
		this.npcFactory = new NPCFactory(context);
		this.sceneFactory = new SceneFactory(context, this.hudFactory, this.enemyFactory, this.npcFactory, this.cutsceneFactory);
	}
	
	public HUDFactory getHudFactory() {
		return this.hudFactory;
	}
	
	public PlayerFactory getPlayerFactory() {
		return this.playerFactory;
	}
	
	public SceneFactory getSceneFactory() {
		return this.sceneFactory;
	}
	
	public NPCFactory getNpcFactory() {
		return this.npcFactory;
	}
	
	public EnemyFactory getEnemyFactory() {
		return this.enemyFactory;
	}
	
	public CutSceneFactory getCutsceneFactory() {
		return this.cutsceneFactory;
	}
}
