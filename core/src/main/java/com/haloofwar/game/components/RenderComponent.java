package com.haloofwar.game.components;

import com.badlogic.gdx.graphics.Texture;

public class RenderComponent implements Component{
	public Texture texture;
	public float angle;
	public float flashTime;
	
	public RenderComponent(Texture texture) {
		this.texture = texture;
		this.angle = 0f;
	}
	
	public RenderComponent(Texture texture, float angle) {
		this.texture = texture;
		this.angle = angle;
	}
}
