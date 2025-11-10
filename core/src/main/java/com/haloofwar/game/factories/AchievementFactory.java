package com.haloofwar.game.factories;

import com.haloofwar.common.enumerators.AchievementType;
import com.haloofwar.engine.Entity;
import com.haloofwar.game.config.ComponentPresets;

public class AchievementFactory {
	public static Entity create(final AchievementType TYPE) {
	     final Entity entity = new Entity();
	     entity.addComponent(TYPE.create());
	     entity.addComponent(ComponentPresets.createName(TYPE));
	     entity.addComponent(ComponentPresets.createAchievement(TYPE));
	     return entity;
	}
	
	public static Entity[] createAllAchievements() {
	     AchievementType[] types = AchievementType.values();
	     Entity[] achievements = new Entity[types.length];
	     for (int i = 0; i < types.length; i++) {
	         achievements[i] = create(types[i]);
	     }
	     return achievements;
	}
}
