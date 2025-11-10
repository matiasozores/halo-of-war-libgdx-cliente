package com.haloofwar.common.enumerators;

import com.haloofwar.game.cutscenes.CutSceneDataType;
import com.haloofwar.game.dependences.LevelData;
import com.haloofwar.interfaces.SceneDescriptor;
import com.haloofwar.interfaces.SceneKey;

public enum SceneType implements SceneDescriptor, SceneKey {

    MAIN("Main", "maps/TMXS/main.tmx", MusicTrack.MAIN),
    TUTORIAL("Tutorial", "maps/TMXS/tutorial.tmx", MusicTrack.CUTSCENE),
    
    COSTA_PERDIDA("Costa Perdida", "maps/TMXS/1_1.tmx", MusicTrack.LEVEL_1_1),
	TIERRAS_HUNDIDAS("Tierras Hundidas", "maps/TMXS/1_2.tmx", MusicTrack.LEVEL_1_2),
	TEMPLO_ABISAL("Templo Abisal", "maps/TMXS/1_3.tmx", MusicTrack.LEVEL_1_3),
	
	ENTRADA_AL_TARTARO("Entrada al Tartaro", "maps/TMXS/2_1.tmx", MusicTrack.LEVEL_2_1),
	FOSA_DEL_ECO("Fosa del Eco", "maps/TMXS/2_2.tmx", MusicTrack.LEVEL_2_2),
	BASE_OCULTA("Base Oculta", "maps/TMXS/2_3.tmx", MusicTrack.LEVEL_2_3),
	
	GRECIA_AMADA("Grecia Amada", "maps/TMXS/3_1.tmx", MusicTrack.LEVEL_3_1),
	EL_COLISEO("El Coliseo", "maps/TMXS/3_2.tmx", MusicTrack.LEVEL_3_2),
	
	SENDERO_OSCURO("Sendero Oscuro", "maps/TMXS/4_1.tmx", MusicTrack.LEVEL_4_1),
	INFIERNO_INFINITO("Infierno Infinito", "maps/TMXS/4_2.tmx", MusicTrack.LEVEL_4_2),
	
	GLACIAR_DE_LOS_SUSURROS_FRIOS("Glaciar de los Susurros Frios", "maps/TMXS/5_1.tmx", MusicTrack.LEVEL_5_1),
	EL_OLIMPO("El Olimpo", "maps/TMXS/5_2.tmx", MusicTrack.LEVEL_5_2),
	
	CINCO_JEFES("CINCO JEFES", "maps/TMXS/5_jefes.tmx", MusicTrack.CUTSCENE);;

    private final String name;
    private final String path;
    private final MusicTrack music;

    private SceneType(final String name, final String path, final MusicTrack music) {
        this.name = name;
        this.path = path;
        this.music = music;
    }

    private SceneType(String name, String path) {
        this(name, path, null);
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public MusicTrack getMusic() {
        return this.music;
    }

    @Override
    public LevelData getLevelData() {
        return null;
    }

    @Override
    public CutSceneDataType getCutSceneType() {
        return null;
    }

    @Override
    public boolean isLevel() {
        return false;
    }

    @Override
    public SceneType getScene() {
        return this;
    }
}
