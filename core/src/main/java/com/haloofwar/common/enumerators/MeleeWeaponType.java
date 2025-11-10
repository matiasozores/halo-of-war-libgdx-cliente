package com.haloofwar.common.enumerators;

import com.haloofwar.game.components.MeleeWeaponComponent;
import com.haloofwar.interfaces.EntityDescriptor;
import com.haloofwar.interfaces.Weapon;

public enum MeleeWeaponType implements Weapon, EntityDescriptor {
    ESPADA("Espada", "ui/hud/espadaHUD.png", 40, 40f, 0.2f, 10f, 0f, 200),
    HACHA_AVANZADA("Hacha avanzada", "images/weapons/hacha_avanzada.png", 75, 40f, 0.2f, 15f, 0f, 300),
    HACHA_ELITE("Hacha magestuosa", "images/weapons/hacha_elite.png", 100, 40f, 0.2f, 15f, 0f, 600);

    private final String name;
    private final String path;
    private final int damage;
    private final float range;
    private final float fireCooldown;
    private final float offsetX; 
    private final float offsetY;
    private final String description;
    private final int price;

    MeleeWeaponType(
    	final String name, 
    	final String path, 
    	final int damage, 
    	final float range, 
    	final float fireCooldown, 
    	final float offsetX, 
    	final float offsetY, 
    	final int price
    ) {
    	this.name = name;
        this.path = path;
        this.damage = damage;
        this.range = range;
        this.fireCooldown = fireCooldown;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.description = "DMG del arma: " + this.damage;
        this.price = price;
    }

    @Override
    public String getName() { return this.name; }
    @Override
    public String getPath() { return this.path; }
    
    public MeleeWeaponComponent createComponent() {
        return new MeleeWeaponComponent(this.damage, this.range, this.fireCooldown, this.offsetX, this.offsetY, this);
    }

    @Override
    public String getDescription() { return 
    		this.description; 
    }

    @Override
    public int getPrice() { return this.price; }
    
    public static MeleeWeaponType getByName(final String NAME) {
    	boolean found = false;
    	MeleeWeaponType type = null;
    	int i = 0;
    	final MeleeWeaponType MELEE_WEAPONS[] = MeleeWeaponType.values();
    	
    	while(i < MELEE_WEAPONS.length && !found) {
    		if(MELEE_WEAPONS[i].getName().equals(NAME)) {
    			found = true;
    			type = MELEE_WEAPONS[i];
    		} else {
    			i++;
    		}
    	}
    	
    	return type;
    }

	@Override
	public BulletType getBulletType() {
		System.out.println("Se ha retornado un tipo de bala nulo para las armas cuerpo a cuerpo... | MeleeWeaponType");
		return null;
	}
}
