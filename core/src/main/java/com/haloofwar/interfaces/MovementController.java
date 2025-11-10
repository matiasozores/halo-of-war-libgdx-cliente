package com.haloofwar.interfaces;

import com.haloofwar.engine.Entity;

public interface MovementController extends Disposable {
	float getDirectionX();
	float getDirectionY();
	void setDirection(float x, float y);
	default void update(float delta) {}
	void changeTarget(Entity entity);
}
