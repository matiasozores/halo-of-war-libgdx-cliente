package com.haloofwar.common.enumerators;

import com.haloofwar.game.components.AchievementComponent;
import com.haloofwar.interfaces.EntityDescriptor;

public enum AchievementType implements EntityDescriptor {

    POSEIDON_SLAYER("Trono Abisal", "Derrota a Poseidón, señor de los mares corrompidos.", "images/achievements/achievement1.png"),
    HADES_SLAYER("Infierno de Acero", "Derrota a Hades y rompe su pacto con el Covenant.", "images/achievements/achievement2.png"),
    HELIOS_SLAYER("Sol Sangriento", "Derrota a Helios y apaga su luz divina.", "images/achievements/achievement3.png"),
    HERMES_SLAYER("Carrera Hacia el Fin", "Derrota a Hermes antes de que escape con las coordenadas multiversales.", "images/achievements/achievement4.png"),
    ZEUS_SLAYER("Dios Supremo", "Derrota a Zeus fusionado con la tecnología del Covenant.", "images/achievements/achievement5.png");

    private final String title;
    private final String description;
    private final String icon;

    AchievementType(String title, String description, String icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getIcon() { return this.icon; }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public String getPath() {
        return this.icon;
    }

    public AchievementComponent create() {
        return new AchievementComponent(this);
    }
    
    public static AchievementType getByName(final String NAME) {
		boolean found = false;
		AchievementType type = null;
		int i = 0;
		
		while (!found && i < AchievementType.values().length) {
			if (AchievementType.values()[i].getTitle().equals(NAME)) {
				type = AchievementType.values()[i];
				found = true;
			} else {
				i++;
			}
		}
		
		return type;
	}
}
