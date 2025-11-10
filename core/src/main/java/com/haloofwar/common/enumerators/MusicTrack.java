package com.haloofwar.common.enumerators;

public enum MusicTrack {
	MAIN("audio/music/main_.ogg"),
	TUTORIAL("audio/music/tutorial_theme.ogg"),
	VICTORY("audio/music/victory.wav"),
	CUTSCENE("audio/music/cinematicas_iniciales_.ogg"),
	LEVEL_1_1("audio/music/1_1_.ogg"),
	LEVEL_1_2("audio/music/1_2_.ogg"),
	LEVEL_1_3("audio/music/1_3_.ogg"),
	
	LEVEL_2_1("audio/music/2_1_.ogg"),
	LEVEL_2_2("audio/music/2_2_.ogg"),
	LEVEL_2_3("audio/music/2_3_.ogg"),
	
	LEVEL_3_1("audio/music/3_1_.ogg"),
	LEVEL_3_2("audio/music/3_2_.ogg"),
	
	LEVEL_4_1("audio/music/4_1_.ogg"),
	LEVEL_4_2("audio/music/4_2_.ogg"),
	
	LEVEL_5_1("audio/music/5_1_.ogg"),
	LEVEL_5_2("audio/music/5_2_.ogg"),
	MENU("audio/music/menu_principal_.ogg");
	

	private final String path;
	
	private MusicTrack(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
