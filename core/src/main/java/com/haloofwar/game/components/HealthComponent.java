package com.haloofwar.game.components;

public class HealthComponent implements Component{
    private int maxHealth;
    public int currentHealth;
    public float shield;

    public boolean alive = true;

    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }


    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
    
    public float getShield() {
		return this.shield;
	}

    public void setShield(float shield) {
    	if(this.shield > 0) {
    		this.shield += shield;
    	} else {
			this.shield = shield;
		}
	}
    
    public boolean isAlive() {
        return this.alive;
    }
    
}