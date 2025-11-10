package com.haloofwar.game.managers;

import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.NewEntityEvent;
import com.haloofwar.engine.events.NextEvent;
import com.haloofwar.engine.events.SpawnEnemyEvent;
import com.haloofwar.engine.events.UpdateLevelDataEvent;
import com.haloofwar.engine.events.online.ConfirmationNextCutsceneEvent;
import com.haloofwar.game.cutscenes.CutScene;
import com.haloofwar.game.dependences.LevelData;
import com.haloofwar.game.factories.EnemyFactory;

public class LevelController {
    private final LevelData data;
    private final EnemyFactory enemyFactory;
    private final EventBus gameplayBus;
    private final EventListenerManager listenerManager = new EventListenerManager();
    private final CutScene cutscene; 
    
    public LevelController(LevelData data, EnemyFactory enemyFactory, EventBus gameplayBus, CutScene cutscene) {
        this.data = data;
        this.enemyFactory = enemyFactory;
        this.gameplayBus = gameplayBus;
        this.cutscene = cutscene;
    }
    
    private void subscribeEvents() {
    	this.listenerManager.add(this.gameplayBus, SpawnEnemyEvent.class, this::spawnEnemy);
        this.listenerManager.add(this.gameplayBus, UpdateLevelDataEvent.class, this::onUpdateLevelData);
        
        if(this.cutscene != null) {
        	this.listenerManager.add(this.gameplayBus, NextEvent.class, this.cutscene::onNext);
    		this.listenerManager.add(this.gameplayBus, ConfirmationNextCutsceneEvent.class, this.cutscene::onConfirmation);
        }
    }
    
    private void onUpdateLevelData(UpdateLevelDataEvent event) {
    	this.data.setEnemiesDefeated(event.enemiesDefeated);
    	this.data.setWavesPassed(event.wavesPassed);
    }

    public void update(float delta) {
        if (this.cutscene != null && !this.cutscene.isFinished()) {
            this.cutscene.update(delta); 
            return;
        }
    }

    public void render(float delta) {
        if (this.cutscene != null && !this.cutscene.isFinished()) {
            this.cutscene.render(delta);
        }
    }

    private void spawnEnemy(final SpawnEnemyEvent event) {
        final Entity enemy = this.enemyFactory.create(event.IDENTIFIER, event.type, event.x, event.y);
        this.gameplayBus.publish(new NewEntityEvent(enemy));
    }
    
    public void reset() {
        this.listenerManager.clear();
        this.data.reset();
        this.subscribeEvents();

        if (this.cutscene != null) {
        	this.cutscene.reset();
        }
    }
    
    public void dispose() {   	
    	this.listenerManager.clear();
    }

}
