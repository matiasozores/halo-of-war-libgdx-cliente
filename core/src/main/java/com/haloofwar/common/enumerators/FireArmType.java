package com.haloofwar.common.enumerators;

import com.haloofwar.game.components.FireArmComponent;
import com.haloofwar.interfaces.Weapon;

public enum FireArmType implements Weapon {
    RIFLE_ASALTO("Rifle de asalto", "images/weapons/assaultRifle.png", 15, 25f, 0.1f, 30f, 50, BulletType.ASSAULT),
    PISTOLA("Pistola", "images/weapons/pistol.png", 35, 35f, 0.5f, 30f, 0, BulletType.PISTOL),
    ESCOPETA("Escopeta", "images/weapons/shotgun.png", 25, 20f, 1.2f, 30f, 400, BulletType.SHOTGUN),
    FRANCO("Francotirador", "images/weapons/sniper.png", 100, 50f, 1.5f, 30f, 800, BulletType.SNIPER),
    REVOLVER("Revolver", "images/weapons/revolver.png", 20, 35f, 0.7f, 30f, 250, BulletType.REVOLVER),
    
    VIENTO("Viento", "images/weapons/revolver.png", 30, 35f, 1f, 0f, -1, BulletType.VIENTO),
    BOLA_AGUA("Bola de Agua", "images/weapons/revolver.png", 80, 35f, 1f, 0f, -1, BulletType.BOLA_AGUA),
    BOLA_FUEGO("Bola de Fuego", "images/weapons/revolver.png", 80, 35f, 1f, 0f, -1, BulletType.BOLA_FUEGO),
    RAYO("Rayo", "images/weapons/revolver.png", 80, 35f, 1f, 0f, -1, BulletType.RAYO),
    CALAVERA("Calavera", "images/weapons/revolver.png", 80, 35f, 1f, 0f, -1, BulletType.CALAVERA),
    RIFLE_PLASMA_VERDE("Rifle de plasma verde", "images/weapons/revolver.png", 20, 35f, 1f, 0f, -1, BulletType.PLASMA_VERDE),
    RIFLE_PLASMA_AZUL("Rifle de plasma azul", "images/weapons/revolver.png", 30, 35f, 1f, 0f, -1, BulletType.PLASMA_AZUL),
    RIFLE_PLASMA_ROJA("Rifle de plasma roja", "images/weapons/revolver.png", 80, 35f, 1f, 0f, -1, BulletType.PLASMA_ROJA);

    private final String name;
    private final String path;
    private final int damage;
    private final float speed;
    private final float fireCooldown;
    private final float bulletOffset;
    private final String description;
    private final int price;
    private final BulletType bulletType;

    FireArmType(
    	final String name, 
    	final String path, 
    	final int damage, 
    	final float speed, 
    	final float fireCooldown, 
    	final float bulletOffset, 
    	final int price, 
    	final BulletType bulletType
    ) {
    	this.name = name;
        this.path = path;
        this.damage = damage;
        this.speed = speed;
        this.fireCooldown = fireCooldown;
        this.bulletOffset = bulletOffset;
        this.description = "DMG del arma: " + this.damage;
        this.price = price;
        this.bulletType = bulletType;
    }

    @Override
    public String getName() { return this.name; }

    @Override
    public String getPath() { return this.path; }

    public FireArmComponent createComponent() {
        return new FireArmComponent(this.damage, this.speed, this.fireCooldown, this.bulletOffset, this);
    }

    @Override
    public String getDescription() { return this.description; }

    @Override
    public int getPrice() { return this.price; }
    
    public BulletType getBulletType() {
		return this.bulletType;
	}
    
    public static FireArmType getByName(final String NAME) {
    	boolean found = false;
    	FireArmType type = null;
    	int i = 0;
    	final FireArmType FIRE_ARMS[] = FireArmType.values();
    	
    	while(i < FIRE_ARMS.length && !found) {
    		if(FIRE_ARMS[i].getName().equals(NAME)) {
    			found = true;
    			type = FIRE_ARMS[i];
    		} else {
    			i++;
    		}
    	}
    	
    	return type;
    }
}
