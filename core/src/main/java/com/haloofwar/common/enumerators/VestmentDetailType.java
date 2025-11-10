package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.EntityDescriptor;

public enum VestmentDetailType implements EntityDescriptor {
	
	KRATOS_DEFAULT("Spritesheet por defecto de Kratos", "sprites/default_kratos.png"),
	KRATOS_VIOLET("Spritesheet violeta de Kratos", "sprites/violet_kratos.png"),
	KRATOS_GREEN("Spritesheet verde de Kratos", "sprites/green_kratos.png"),
	
	MASTERCHIEF_DEFAULT("Spritesheet por defecto de MasterChief", "sprites/default_masterchief.png"),
	MASTERCHIEF_VIOLET("Spritesheet violeta de MasterChief", "sprites/violet_masterchief.png"),
	MASTERCHIEF_GREEN("Spritesheet verde de MasterChief", "sprites/green_masterchief.png"),
	;

	private final String name;
	private final String path;
	
	private VestmentDetailType(String name, String path) {
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
