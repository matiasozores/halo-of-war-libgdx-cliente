package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.EntityDescriptor;

public enum HeadType implements EntityDescriptor{
	KRATOS("Cabeza de Kratos","ui/hud/kratosHUD.png"),
	MASTER_CHIEF("Cabeza de Master Chief","ui/hud/jefeHUD.png"),
	CARLOS("Cabeza de Carlos", "ui/hud/carloshead.png"),
	CAMILA("Cabeza de Camila", "ui/hud/camilahead.png"),
	JOAQUIN("Cabeza de Joaquin", "ui/hud/joaquinhead.png"),
	PABLO("Cabeza de Pablo", "ui/hud/pablohead.png"),;
	
	private final String path;
	private final String name;
	
	private HeadType(final String name, final String path) {
		this.name = name;
		this.path = path;
	}

	@Override
	public String getPath() {
		return this.path;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
