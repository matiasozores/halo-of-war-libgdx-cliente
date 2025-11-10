package com.haloofwar.game.cutscenes;

public enum CutSceneDataType {
	NONE(null),
	INTRO(new CutSceneType[] {CutSceneType.INTRO_1, CutSceneType.INTRO_2, CutSceneType.INTRO_3}),
	LEVEL_1_1(new CutSceneType[] {CutSceneType.LEVEL_1_1}),
	LEVEL_1_2(new CutSceneType[] {CutSceneType.LEVEL_1_2}),
	LEVEL_1_3(new CutSceneType[] {CutSceneType.LEVEL_1_3}),
	
	LEVEL_2_1(new CutSceneType[] {CutSceneType.LEVEL_2_1}),
	LEVEL_2_2(new CutSceneType[] {CutSceneType.LEVEL_2_2}),
	LEVEL_2_3(new CutSceneType[] {CutSceneType.LEVEL_2_3}),
	
	LEVEL_3_1(new CutSceneType[] {CutSceneType.LEVEL_3_1}),
	LEVEL_3_2(new CutSceneType[] {CutSceneType.LEVEL_3_2}),
	
	LEVEL_4_1(new CutSceneType[] {CutSceneType.LEVEL_4_1}),
	LEVEL_4_2(new CutSceneType[] {CutSceneType.LEVEL_4_2})
	,
	LEVEL_5_1(new CutSceneType[] {CutSceneType.LEVEL_5_1}),
	LEVEL_5_2(new CutSceneType[] {CutSceneType.LEVEL_5_2}),
	;

	
	private CutSceneType[] images;
	
	private CutSceneDataType(CutSceneType[] images) {
		this.images = images;
	}
	
	public CutSceneType[] getImages() {
		return this.images;
	}
}

