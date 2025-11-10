package com.haloofwar.game.components;

import com.haloofwar.common.enumerators.AchievementType;

public class AchievementComponent implements Component {
	public AchievementType type;
	public boolean unlocked;
	
	public AchievementComponent(AchievementType type) {
		this.type = type;
		this.unlocked = false;
	}
}
