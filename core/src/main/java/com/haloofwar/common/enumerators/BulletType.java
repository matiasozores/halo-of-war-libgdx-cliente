package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.EntityDescriptor;

public enum BulletType implements EntityDescriptor {
    ASSAULT("Bala de rifle de asalto", "images/weapons/bullets/bullet_assault.png", SoundType.SHOOT_ASSAULT),
    PISTOL("Bala de pistola", "images/weapons/bullets/bullet_pistol.png", SoundType.SHOOT_PISTOL),
    SHOTGUN("Cartucho de escopeta", "images/weapons/bullets/shell_shotgun.png", SoundType.SHOOT_SHOTGUN),
    SNIPER("Bala de francotirador", "images/weapons/bullets/bullet_sniper.png", SoundType.SHOOT_SNIPER),
    REVOLVER("Bala de revolver", "images/weapons/bullets/bullet_revolver.png", SoundType.SHOOT_REVOLVER),
    
    VIENTO("Bala del arma especial de Hermes", "images/weapons/bullets/viento.png", SoundType.BOLA_AGUA),
    BOLA_AGUA("Bala del arma especial de Poseidon", "images/weapons/bullets/bola_agua.png", SoundType.BOLA_AGUA),
    BOLA_FUEGO("Bala del arma especial de Helios", "images/weapons/bullets/bola_fuego.png", SoundType.BOLA_FUEGO),
    
    RAYO("Bala del arma especial de Zeus", "images/weapons/bullets/rayo.png", SoundType.RAYO),
    CALAVERA("Bala del arma especial de Hermes", "images/weapons/bullets/calavera.png", SoundType.BOLA_FUEGO),
    PLASMA_VERDE("Bala del arma especial de Grunts", "images/weapons/bullets/bala_plasma_verde.png", SoundType.SHOOT_ASSAULT),
    PLASMA_AZUL("Bala del arma especial de Elites", "images/weapons/bullets/bala_plasma_azul.png", SoundType.SHOOT_PISTOL),
    PLASMA_ROJA("Bala del arma especial de Robot", "images/weapons/bullets/bala_plasma_roja.png", SoundType.SHOOT_REVOLVER),;

    private final String name;
    private final String path;
    private final SoundType sound;

    BulletType(final String name, final String path, final SoundType sound) {
        this.name = name;
        this.path = path;
        this.sound = sound;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public SoundType getSound() {
        return this.sound;
    }
    
    public static BulletType getByName(final String NAME) {
    	boolean found = false;
    	int i = 0;
    	BulletType type = null;
    	
    	while(i < BulletType.values().length && !found) {
    		if(BulletType.values()[i].getName().equals(NAME)) {
    			found = true;
    			type = BulletType.values()[i];
    		} else {
    			i++;
    		}
    	}
    	
    	return type;
    }
}
