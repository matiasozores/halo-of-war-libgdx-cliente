package com.haloofwar.engine.events;

import com.badlogic.gdx.graphics.Texture;

public class ShowDialogueEvent {
	private final String name;
    private final String text;
    private Texture avatar;

    public ShowDialogueEvent(String name, String text) {
        this.text = text;
        this.name = name;
        System.out.println("Intentando mostrar dialogo");
    }
    
    public ShowDialogueEvent(String name, String text, Texture avatar) {
        this.text = text;
        this.avatar = avatar;
        this.name = name;
    }

    public String getText() {
        return this.text;
    }
    
    public Texture getAvatar() {
		return this.avatar;
	}
    
    public String getName() {
		return this.name;
	}
}
