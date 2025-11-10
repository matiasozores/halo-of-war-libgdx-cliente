package com.haloofwar.game.dependences;

import com.haloofwar.common.enumerators.LevelSceneType;

public class LevelData {
    private LevelSceneType type;
    private int enemySpawnRate;
    private int maxEnemies;
    private int waveCount;
    private int enemiesToDefeatPerWave;
    private int enemiesToDefeat;
    private int enemiesDefeated = 0;
    private int wavesPassed = 0;

    public LevelData(
        LevelSceneType type,
        int enemySpawnRate,
        int maxEnemies,
        int waveCount,
        int enemiesToDefeatPerWave
    ) {
        this.type = type;
        this.enemySpawnRate = enemySpawnRate;
        this.maxEnemies = maxEnemies;
        this.waveCount = waveCount;
        this.enemiesToDefeatPerWave = enemiesToDefeatPerWave;
        this.enemiesToDefeat = this.enemiesToDefeatPerWave * this.waveCount;
    }

    public LevelData copy() {
        LevelData clone = new LevelData(
            this.type,
            this.enemySpawnRate,
            this.maxEnemies,
            this.waveCount,
            this.enemiesToDefeatPerWave
        );
        clone.enemiesDefeated = this.enemiesDefeated;
        clone.wavesPassed = this.wavesPassed;
        return clone;
    }

    public void setEnemiesDefeated(int enemiesDefeated) {
		this.enemiesDefeated = enemiesDefeated;
	}
    
    public void setWavesPassed(int wavesPassed) {
		this.wavesPassed = wavesPassed;
	}
    
    public void reset() {
    	this.wavesPassed = 0;
    	this.enemiesDefeated = 0;
    }

    public LevelSceneType getType() { return this.type; }
    public int getEnemySpawnRate() { return this.enemySpawnRate; }
    public int getMaxEnemies() { return this.maxEnemies; }
    public int getWaveCount() { return this.waveCount; }
    public int getEnemiesToDefeat() { return this.enemiesToDefeat; }
    public int getEnemiesDefeated() { return this.enemiesDefeated; }
    public int getWavesPassed() { return this.wavesPassed; }
    public int getEnemiesToDefeatPerWave() { return this.enemiesToDefeatPerWave; }
}
