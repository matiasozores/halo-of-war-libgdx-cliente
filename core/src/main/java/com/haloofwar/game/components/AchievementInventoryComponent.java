package com.haloofwar.game.components;

import com.haloofwar.common.enumerators.AchievementType;
import com.haloofwar.engine.Entity;

public class AchievementInventoryComponent implements Component {
	private Entity[] achievements;

	public AchievementInventoryComponent(Entity[] achievements) {
		this.achievements = achievements;
	}
	
	public void unlockAchievement(AchievementType type) {
		boolean found = false;
		int i = 0;
		
		while(!found && i < this.achievements.length) {
			final AchievementComponent ac = this.achievements[i].getComponent(AchievementComponent.class);
			
			if(ac.type.equals(type)) {
				ac.unlocked = true;
				found = true;
			} else {
				i++;	
			}
		}
	}
	
	public Entity[] getAchievements() {
		return this.achievements;
	}
}
