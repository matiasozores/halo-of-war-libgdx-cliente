package com.haloofwar.common.enumerators;

public enum LayerType {
	ITEM("items", null),
	PORTAL_LEVEL("portal", AnimatedObject.PORTAL_NIVEL),
	PORTAL_SECUNDARIA("portal secundaria", AnimatedObject.PORTAL_SECUNDARIA),
	PORTAL_EXTRA("portal extra", AnimatedObject.PORTAL_EXTRA);

	
	private final String name;
	private final AnimatedObject animated;
	
	private LayerType(final String name, final AnimatedObject animated) {
		this.name = name;
		this.animated = animated;
	}
	
	public String getName() {
		return this.name;
	}
	
	public AnimatedObject getAnimated() {
		return this.animated;
	}
}
