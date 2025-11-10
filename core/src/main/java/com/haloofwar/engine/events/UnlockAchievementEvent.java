package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.AchievementType;

public class UnlockAchievementEvent {
	public final AchievementType achievement;
	
	public UnlockAchievementEvent(final AchievementType achievement) {
		this.achievement = achievement;
	}
}
