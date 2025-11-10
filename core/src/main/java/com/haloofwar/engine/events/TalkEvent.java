package com.haloofwar.engine.events;

import com.haloofwar.common.enumerators.HeadType;

public class TalkEvent {
	public String name;
	public String[] texts;
	public HeadType avatar;
	
	public TalkEvent(String name, String[] texts, HeadType avatar) {
		this.name = name;
		this.texts = texts;
		this.avatar = avatar;
	}
}
