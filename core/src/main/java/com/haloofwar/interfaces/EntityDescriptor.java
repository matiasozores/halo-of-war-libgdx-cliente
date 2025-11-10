package com.haloofwar.interfaces;

public interface EntityDescriptor {
	String getName();
	String getPath();
	default int getWidth() {
		return 32;
	}
	
	default int getHeight() {
		return 32;
	}
}
