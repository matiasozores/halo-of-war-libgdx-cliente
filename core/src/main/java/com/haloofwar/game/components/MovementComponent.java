package com.haloofwar.game.components;

import com.haloofwar.interfaces.MovementController;

public class MovementComponent implements Component {
    public MovementController controller; 
    public float lastX, lastY, speed, speedDuration;         
    
    public MovementComponent(MovementController controller, float speed) {
        this.controller = controller;
        this.lastX = 0;
        this.lastY = 0;
        this.speed = speed;
    }
    
    public void dispose() {
    	this.controller.dispose();
    }
}