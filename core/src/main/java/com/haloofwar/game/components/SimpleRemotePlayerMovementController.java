package com.haloofwar.game.components;
import com.haloofwar.engine.Entity;
import com.haloofwar.interfaces.MovementController;

public class SimpleRemotePlayerMovementController implements MovementController{
	
    private float dx = 0;
    private float dy = 0;
    
    @Override
    public float getDirectionX() {
        return this.dx;
    }

    @Override
    public float getDirectionY() {
        return this.dy;
    }

	@Override
	public void changeTarget(Entity entity) {
		
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
