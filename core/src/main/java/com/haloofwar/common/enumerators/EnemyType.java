package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.ArmedEntityDescriptor;
import com.haloofwar.interfaces.Weapon;

public enum EnemyType implements ArmedEntityDescriptor {
	ELITE_INOFENSIVO("Elite", "sprites/elite_inofensivo.png", 2, 8),
	ELITE("Elite", "sprites/elite.png", 2, 8, FireArmType.RIFLE_PLASMA_AZUL),
	GRUNT("Grunt", "sprites/grunt.png", 2, 6, FireArmType.RIFLE_PLASMA_VERDE),
	HELIOS("Helios", "sprites/helios.png", 1, 1, FireArmType.BOLA_FUEGO, 126, 126),
	POSEIDON("Poseidon", "sprites/poseidon.png", 1, 1, FireArmType.BOLA_AGUA, 133, 128),
	HADES("Hades", "sprites/hades.png", 1, 1, FireArmType.CALAVERA, 126, 126),
	ZEUS("Zeus", "sprites/zeus.png", 1, 1, FireArmType.RAYO, 85, 128),
	HERMES("Hermes", "sprites/hermes.png", 1, 1, FireArmType.VIENTO, 85, 128),
	ROBOT("Robot", "sprites/robot.png", 4, 4, FireArmType.RIFLE_PLASMA_ROJA);

	private final String name;
	private final String path;
	private final int idleLength;
	private final int walkLength;
	private Weapon weapon;
	private int width = 32;
	private int height = 32;
	
	private EnemyType(final String name, final String path, final int idleLength, final int walkLength, final Weapon weapon, int width, int height) {
		this.name = name;
		this.path = path;
		this.idleLength = idleLength;
		this.walkLength = walkLength;
		this.weapon = weapon;
		this.width = width;
		this.height = height;
	}
	
	private EnemyType(final String name, final String path, final int idleLength, final int walkLength, final Weapon weapon) {
		this.name = name;
		this.path = path;
		this.idleLength = idleLength;
		this.walkLength = walkLength;
		this.weapon = weapon;
	}
	
	private EnemyType(final String name, final String path, final int idleLength, final int walkLength) {
		this.name = name;
		this.path = path;
		this.idleLength = idleLength;
		this.walkLength = walkLength;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public int getIdleLength() {
		return this.idleLength;
	}

	@Override
	public int getWalkLength() {
		return this.walkLength;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	public static EnemyType getEnemyTypeByName(final String NAME) {
	    EnemyType[] types = EnemyType.values();
	    int i = 0;
	    boolean found = false;
	    EnemyType type = null;

	    while (i < types.length && !found) {
	        if (types[i].getName().equalsIgnoreCase(NAME)) {
	            type = types[i];
	            found = true;
	        }
	        i++;
	    }

	    return type;
	}

	@Override
	public int getAttackLength() {
		return 0;
	}

}
