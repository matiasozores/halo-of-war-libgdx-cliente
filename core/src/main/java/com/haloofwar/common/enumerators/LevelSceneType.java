package com.haloofwar.common.enumerators;

import java.util.HashSet;
import java.util.Set;

import com.haloofwar.game.cutscenes.CutSceneDataType;
import com.haloofwar.game.dependences.LevelData;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.interfaces.SceneKey;

public enum LevelSceneType implements SceneDescriptor, SceneKey {

    TUTORIAL("Tutorial", SceneType.TUTORIAL, 1, 1, 1, 3, CutSceneDataType.INTRO, new EnemyType[] {EnemyType.ELITE_INOFENSIVO}, 50),
	
    LEVEL_1_1("1-1", SceneType.COSTA_PERDIDA, 1, 4, 1, 8, CutSceneDataType.LEVEL_1_1, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_1_2("1-2", SceneType.TIERRAS_HUNDIDAS, 1, 4, 1, 8, CutSceneDataType.LEVEL_1_2, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_1_3("1-3", SceneType.TEMPLO_ABISAL, 1, 1, 1, 1, CutSceneDataType.LEVEL_1_3, new EnemyType[] {EnemyType.POSEIDON}, 100),
    
    LEVEL_2_1("2-1", SceneType.ENTRADA_AL_TARTARO, 1, 3, 2, 6, CutSceneDataType.LEVEL_2_1, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_2_2("2-2", SceneType.FOSA_DEL_ECO, 1, 1, 1, 1, CutSceneDataType.LEVEL_2_2, new EnemyType[] {EnemyType.HADES}, 100),
    LEVEL_2_3("2-3", SceneType.BASE_OCULTA, 1, 1, 1, 1, CutSceneDataType.LEVEL_2_3, new EnemyType[] {EnemyType.ROBOT}, 100),
    
    LEVEL_3_1("3-1", SceneType.GRECIA_AMADA, 1, 4, 2, 8, CutSceneDataType.LEVEL_3_1, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_3_2("3-2", SceneType.EL_COLISEO, 1, 1, 1, 1, CutSceneDataType.LEVEL_3_2, new EnemyType[] {EnemyType.HERMES}, 100),
    
    LEVEL_4_1("4-1", SceneType.SENDERO_OSCURO, 1, 4, 3, 8, CutSceneDataType.LEVEL_4_1, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_4_2("4-2", SceneType.INFIERNO_INFINITO, 1, 1, 1, 1,  CutSceneDataType.LEVEL_4_2, new EnemyType[] {EnemyType.HELIOS}, 100),
    
    LEVEL_5_1("5-1", SceneType.GLACIAR_DE_LOS_SUSURROS_FRIOS, 1, 5, 3, 10, CutSceneDataType.LEVEL_5_1, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT}, 100),
    LEVEL_5_2("5-2", SceneType.EL_OLIMPO, 1, 1, 1, 1, CutSceneDataType.LEVEL_5_2, new EnemyType[] {EnemyType.ZEUS}, 100),

    
    CINCO_JEFES("Cinco Jefes", SceneType.CINCO_JEFES, 1, 1, 1, 5, new EnemyType[] {EnemyType.HADES, EnemyType.POSEIDON, EnemyType.ZEUS, EnemyType.HERMES, EnemyType.HELIOS}, 500),
    MIL_OLEADAS("Mil Oleadas", SceneType.EL_COLISEO, 1, 5, 1000, 5, new EnemyType[] {EnemyType.ELITE, EnemyType.GRUNT, EnemyType.ROBOT}, 9_999_999);
    
	static {
	    LevelSceneType[] levels = {
	    	TUTORIAL,
	        LEVEL_1_1,
	        LEVEL_1_2,
	        LEVEL_1_3,
	        
	        LEVEL_2_1,
	        LEVEL_2_2,
	        LEVEL_2_3,
	        
	        LEVEL_3_1,
	        LEVEL_3_2,
	        
	        LEVEL_4_1,
	        LEVEL_4_2,
	        
	        LEVEL_5_1,
	        LEVEL_5_2
	    };

	    for (int i = 0; i < levels.length - 1; i++) {
	        levels[i].nextLevel = levels[i + 1];
	    }
	}

	
    private final String name;
    private final SceneType baseScene;
    private final LevelData data;
    private final CutSceneDataType cutSceneType;
    private LevelSceneType nextLevel;
    private final EnemyType[] enemies;
    private final int goldCoins;
    
    private LevelSceneType(
    	final String name, 
    	final SceneType scene,
    	final int enemySpawnRate,
    	final int maxEnemies,
    	final int waveCount,
    	final int enemiesToDefeatPerWave,
    	final CutSceneDataType cutSceneType,
    	final EnemyType[] enemies,
    	final int goldCoins
    ) {
        this.name = name;
        this.baseScene = scene;
        this.cutSceneType = cutSceneType;
        this.enemies = enemies;
        this.goldCoins = goldCoins;
        this.data = new LevelData(this, enemySpawnRate, maxEnemies, waveCount, enemiesToDefeatPerWave);
    }
    
    private LevelSceneType(
    	final String name, 
    	final SceneType scene,
    	final int enemySpawnRate,
    	final int maxEnemies,
    	final int waveCount,
    	final int enemiesToDefeatPerWave,
    	final EnemyType[] enemies,
    	final int goldCoins
    ) {
		this(name, scene, enemySpawnRate, maxEnemies, waveCount, enemiesToDefeatPerWave, null, enemies, goldCoins);
    }
    
    @Override
    public String getName() { return this.name; }
    @Override
	public String getPath() { return this.baseScene.getPath(); }
	@Override
	public MusicTrack getMusic() { return this.baseScene.getMusic(); }
    
    public LevelData getLevelData() { return this.data.copy(); }
    @Override
    public CutSceneDataType getCutSceneType() { return this.cutSceneType; }
	@Override
	public boolean isLevel() { return true; }
	@Override
	public SceneType getScene() { return this.baseScene; }
	
    public static LevelSceneType getLevelByName(String name) {
        for (LevelSceneType lt : values()) {
            if (lt.getName().equals(name)) return lt;
        }
        System.out.println("Ha ocurrido un error al cargar el nivel: " + name);
        return null;
    }
    
    public LevelSceneType getNextLevel() {
		return this.nextLevel;
	}
    
    public static Set<LevelSceneType> getAllLockedLevels(){
    	Set<LevelSceneType> lockedLevels = new HashSet<LevelSceneType>();
    	
    	for (int i = 0; i < LevelSceneType.values().length; i++) {
    		if (
    			    !LevelSceneType.values()[i].equals(LevelSceneType.TUTORIAL) &&
    			    !LevelSceneType.values()[i].equals(LevelSceneType.MIL_OLEADAS) &&
    			    !LevelSceneType.values()[i].equals(LevelSceneType.CINCO_JEFES)
    			) {
    			    lockedLevels.add(LevelSceneType.values()[i]);	
    			}

		}
    	
    	return lockedLevels;
    }
    
    public EnemyType[] getEnemies() {
		return this.enemies;
	}
    
    public int getGoldCoins() {
		return this.goldCoins;
	}
}
