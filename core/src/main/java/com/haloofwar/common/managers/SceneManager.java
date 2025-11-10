package com.haloofwar.common.managers;

import java.util.HashMap;
import java.util.Map;

import com.haloofwar.game.factories.SceneFactory;
import com.haloofwar.game.scenes.GameScene;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.interfaces.SceneKey;

public class SceneManager {
    private final Map<SceneDescriptor, GameScene> scenes = new HashMap<>();
    private final SceneFactory factory;

    public SceneManager(final SceneFactory factory) {
        this.factory = factory;
    }
    
//    public void initialLoad() {
//    	//this.load(SceneType.MAIN);
//    	/*
//    	this.load(LevelSceneType.LEVEL_1_1);
//    	this.load(LevelSceneType.LEVEL_1_2);
//    	this.load(LevelSceneType.LEVEL_1_3);
//    	
//    	this.load(LevelSceneType.LEVEL_2_1);
//    	this.load(LevelSceneType.LEVEL_2_2);
//    	this.load(LevelSceneType.LEVEL_2_3);
//    	
//    	this.load(LevelSceneType.LEVEL_3_1);
//    	this.load(LevelSceneType.LEVEL_3_2);
//    	
//    	this.load(LevelSceneType.LEVEL_4_1);
//    	this.load(LevelSceneType.LEVEL_4_2);
//    	
//    	this.load(LevelSceneType.LEVEL_5_1);
//    	this.load(LevelSceneType.LEVEL_5_2);*/
//    }

    public void load(SceneKey key) {
        SceneDescriptor descriptor = (SceneDescriptor) key;

        if (this.scenes.containsKey(descriptor)) {
            return;
        }

        GameScene scene = this.factory.create(descriptor);
        this.scenes.put(descriptor, scene);
    }

    public GameScene get(SceneKey key) {
        SceneDescriptor descriptor = (SceneDescriptor) key;

        if (!this.scenes.containsKey(descriptor)) {
            this.load(key);
        }

        return this.scenes.get(descriptor);
    }

    public void clear() {
        for (GameScene scene : this.scenes.values()) {
            scene.dispose();
        }
        
        this.scenes.clear();
    }
}
