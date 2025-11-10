
package com.haloofwar.game.cutscenes;

import com.haloofwar.interfaces.EntityDescriptor;

public enum CutSceneType implements EntityDescriptor {
	INTRO_1("Intro 1", "images/background/cutscenes/1_1-1.png"),
	INTRO_2("Intro 2", "images/background/cutscenes/1_1-2.png"),
	INTRO_3("Intro 2", "images/background/cutscenes/1_1-3.png"),
	LEVEL_1_1("Nivel 1 1", "images/background/cutscenes/1_1-4.png"),
	LEVEL_1_2("Nivel 1 2", "images/background/cutscenes/1_2.png"),
	LEVEL_1_3("Nivel 1 3", "images/background/cutscenes/1_3.png"),
	
	LEVEL_2_1("Nivel 2 1", "images/background/cutscenes/2_1.png"),
	LEVEL_2_2("Nivel 2 2", "images/background/cutscenes/2_2.png"),
	LEVEL_2_3("Nivel 2 3", "images/background/cutscenes/2_3.png"),
	
	LEVEL_3_1("Nivel 3 1", "images/background/cutscenes/3_1.png"),
	LEVEL_3_2("Nivel 3 2", "images/background/cutscenes/3_2.png"),
	
	LEVEL_4_1("Nivel 4 1", "images/background/cutscenes/4_1.png"),
	LEVEL_4_2("Nivel 4 2", "images/background/cutscenes/4_2.png"),
	
	LEVEL_5_1("Nivel 5 1", "images/background/cutscenes/5_1.png"),
	LEVEL_5_2("Nivel 5 2", "images/background/cutscenes/5_2.png"),;

	private final String name;
	private final String path;
	
	private CutSceneType(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getPath() {
		return this.path;
	}
}
