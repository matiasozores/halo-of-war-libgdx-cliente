package com.haloofwar.common.enumerators;

import java.util.ArrayList;

import com.haloofwar.engine.Entity;
import com.haloofwar.game.components.VestmentComponent;
import com.haloofwar.game.factories.VestmentFactory;
import com.haloofwar.interfaces.EntityDescriptor;

public enum VestmentType implements EntityDescriptor {

    KRATOS_DEFAULT(
        "Kratos Armor (Default)",
        "La furia del dios de la guerra en su forma clásica. Poder puro sin adornos.",
        "images/vestments/default_icon_kratos.png", 0,
        PlayerType.KRATOS, VestmentDetailType.KRATOS_DEFAULT
    ),

    KRATOS_VIOLET(
        "Kratos Armor (Violet)",
        "Una versión mística de la armadura de Kratos. Refleja su conexión con la oscuridad divina.",
        "images/vestments/violet_icon_kratos.png", 500,
        PlayerType.KRATOS, VestmentDetailType.KRATOS_VIOLET
    ),

    KRATOS_GREEN(
        "Kratos Armor (Green)",
        "Una variante esmeralda que simboliza resistencia y control interior.",
        "images/vestments/green_icon_kratos.png", 1000,
        PlayerType.KRATOS, VestmentDetailType.KRATOS_GREEN
    ),

    MASTERCHIEF_DEFAULT(
        "Master Chief Armor (Default)",
        "La armadura icónica del Spartan, lista para cualquier batalla.",
        "images/vestments/default_icon_masterchief.png", 0,
        PlayerType.MASTER_CHIEF, VestmentDetailType.MASTERCHIEF_DEFAULT
    ),

    MASTERCHIEF_VIOLET(
        "Master Chief Armor (Violet)",
        "Una edición especial con tintes violetas, símbolo de prestigio y experiencia.",
        "images/vestments/violet_icon_masterchief.png", 500,
        PlayerType.MASTER_CHIEF, VestmentDetailType.MASTERCHIEF_VIOLET
    ),

    MASTERCHIEF_GREEN(
        "Master Chief Armor (Green)",
        "El clásico color del Jefe Maestro, reflejando su espíritu combativo y lealtad.",
        "images/vestments/green_icon_masterchief.png", 1000,
        PlayerType.MASTER_CHIEF, VestmentDetailType.MASTERCHIEF_GREEN
    );

    private final String name;
    private final String description;
    private final String path;
    private final int price;
    private final PlayerType playerType;
    private final VestmentDetailType detailType;
    
    VestmentType(String name, String description, String path, int price, PlayerType playerType, VestmentDetailType detailType) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.price = price;
        this.playerType = playerType;
        this.detailType = detailType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPath() {
        return this.path;
    }
    
    public PlayerType getPlayerType() {
		return this.playerType;
	}
    
    public VestmentComponent getComponent() {
		return new VestmentComponent(this);
	}
    
    public int getPrice() {
		return this.price;
	}
    
    public VestmentDetailType getDetailType() {
		return this.detailType;
	}
    
    public static ArrayList<Entity> getAllVestmentEntityByPlayer(PlayerType playerType) {
    	ArrayList<Entity> entities = new ArrayList<>();
    	
    	for (VestmentType vestment : VestmentType.values()) {
    		if(vestment.getPlayerType().equals(playerType)) {
    			entities.add(VestmentFactory.create(vestment));
    		}
		}
    	
    	return entities;
    }
    
    public static VestmentType getDefaultByPlayerType(PlayerType type) {
    	if(type.equals(PlayerType.KRATOS)) {
			return KRATOS_DEFAULT;
		} else {
			return MASTERCHIEF_DEFAULT;
    		
    	}
    }
    
    public static VestmentType getByName(String name) {
    	boolean found = false;
    	int i = 0;
    	VestmentType vestment = null;
    	
    	while(!found && i < VestmentType.values().length) {
			if(VestmentType.values()[i].getName().equals(name)) {
				found = true;
				vestment = VestmentType.values()[i];
			}
			i++;
		}
    	
    	return vestment;
    }
}
