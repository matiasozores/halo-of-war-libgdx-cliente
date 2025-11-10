package com.haloofwar.game.components;

import com.haloofwar.engine.Entity;
import com.haloofwar.interfaces.MovementController;

public class EnemyMovementController implements MovementController {

    private float dx = 0, dy = 0;

    @Override
    public float getDirectionX() { return dx; }

    @Override
    public float getDirectionY() { return dy; }
    
    @Override
    public void changeTarget(Entity newTarget) {
        // lógica de IA si querés
    }

    @Override
    public void setDirection(float x, float y) {
        this.dx = x;
        this.dy = y;
    }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
