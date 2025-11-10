package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.Talkable;

public enum NPCType implements Talkable {
	CARLOS("Carlos", "sprites/npcs/carlos.png", 7, 2,
			new String[] { "Vienes preparado o solo a morir rapido?", "Tengo armas que pueden salvarte... o condenarte.",
					"Recuerda: no todos los enemigos sangran igual." },
			HeadType.CARLOS),
	CAMILA("Camila", "sprites/npcs/camila.png", 7, 2,
			new String[] { "Te ves cansado... puedo ofrecerte algo para calmar el dolor.",
					"Algunos compran esperanza, otros solo compran tiempo.",
					"Vuelve vivo... asi me das motivos para seguir aqui." },
			HeadType.CAMILA),
	JOAQUIN("Joaquin", "sprites/npcs/joaquin.png", 13, 2,
			new String[] { "Vienes a negociar o a perder dinero?", "Nada es gratis, ni siquiera la paz.",
					"Si te matan, avisame... vendere tus cosas." },
			HeadType.JOAQUIN),
	PABLO("Pablo", "sprites/npcs/pablo.png", 8, 2,
			new String[] { "He visto mas guerras que amaneceres...",
					"Si confias demasiado, no llegaras al siguiente pueblo.",
					"Buena suerte... aunque la suerte no sirve en el infierno." },
			HeadType.PABLO);

	private final String name;
	private final String path;
	private final int idleLength;
	private final int walkLength;
	private final String[] dialogues;
	private final HeadType head;

	private NPCType(final String name, final String path, final int idleLength, final int walkLength,
			final String[] dialogues, final HeadType head) {
		this.name = name;
		this.path = path;
		this.idleLength = idleLength;
		this.walkLength = walkLength;
		this.dialogues = dialogues;
		this.head = head;
	}

	public String getName() {
		return this.name;
	}

	public String getPath() {
		return this.path;
	}

	public int getIdleLength() {
		return this.idleLength;
	}

	public int getWalkLength() {
		return this.walkLength;
	}

	@Override
	public String[] getLines() {
		return this.dialogues;
	}

	public HeadType getHead() {
		return this.head;
	}

	public static NPCType getByName(final String NAME) {
		boolean found = false;
		int i = 0;
		NPCType type = null;

		while (i < NPCType.values().length && !found) {
			if (NPCType.values()[i].getName().equals(NAME)) {
				found = true;
				type = NPCType.values()[i];
			} else {
				i++;
			}
		}

		return type;
	}
}
