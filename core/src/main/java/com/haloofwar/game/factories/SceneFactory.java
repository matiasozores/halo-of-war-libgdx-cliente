package com.haloofwar.game.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.engine.cameras.GameWorldCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.dependences.LevelData;
import com.haloofwar.game.managers.LevelController;
import com.haloofwar.game.scenes.ExplorationScene;
import com.haloofwar.game.scenes.GameScene;
import com.haloofwar.game.scenes.Level;
import com.haloofwar.game.world.MapRenderer;
import com.haloofwar.game.world.NPCSpawner;
import com.haloofwar.game.world.World;
import com.haloofwar.game.world.WorldContext;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.ui.HUD;

public final class SceneFactory {
	private final HUDFactory hudFactory;
	private final EnemyFactory enemyFactory;
	private final NPCFactory npcFactory;
	private final CutSceneFactory cutsceneFactory;
	private final EventBus gameplayBus;
	private final GameplayContext gameplay;
	private final SpriteBatch batch;
	private final GameWorldCamera worldCamera;
	
	public SceneFactory(final GameContext context, final HUDFactory hudFactory, final EnemyFactory enemyFactory,  final NPCFactory npcFactory, final CutSceneFactory cutsceneFactory) {
		this.hudFactory = hudFactory;
		this.enemyFactory = enemyFactory;
		this.npcFactory = npcFactory;
		this.cutsceneFactory = cutsceneFactory;
		this.gameplayBus = context.getGameplay().getBus();
		this.gameplay = context.getGameplay();
		this.batch = context.getRender().getBatch();
		this.worldCamera = context.getWorldCamera();
	}
	
    public GameScene create(final SceneDescriptor DESCRIPTOR) {
    	final LevelData data = DESCRIPTOR.getLevelData();
    	
        final World world = this.build(DESCRIPTOR);
        final HUD hud = DESCRIPTOR.isLevel()
            ? this.hudFactory.createLevelHUD(data)
            : this.hudFactory.createLobbyHUD();
        
        if (DESCRIPTOR.isLevel()) {
            final LevelController controller = new LevelController(
                data,
                this.enemyFactory,
                this.gameplayBus,
                this.cutsceneFactory.create(DESCRIPTOR.getCutSceneType(), DESCRIPTOR.getMusic())
            );
            
            final Level level = new Level(DESCRIPTOR, world, hud, controller);
            return level;
            
        } else {
        	final TiledMap map = world.getTiled();
        	final NPCSpawner npcSpawner = new NPCSpawner(this.npcFactory, this.gameplayBus, map);
        	
            return new ExplorationScene(DESCRIPTOR, world, hud, npcSpawner);
        }
    }


	private World build(final SceneDescriptor DESCRIPTOR) {
		final MapRenderer MAP = new MapRenderer(DESCRIPTOR);
		final WorldContext worldContext = new WorldContext(MAP, this.gameplay, this.batch, this.worldCamera);
		return new World(MAP, worldContext);
	}
}
