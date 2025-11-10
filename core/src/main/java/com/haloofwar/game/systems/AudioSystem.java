package com.haloofwar.game.systems;

import com.haloofwar.common.managers.AudioManager;
import com.haloofwar.common.managers.MusicManager;
import com.haloofwar.common.managers.SoundManager;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.PlayMusicEvent;
import com.haloofwar.engine.events.PlaySoundEvent;
import com.haloofwar.engine.events.StopMusicEvent;
import com.haloofwar.engine.events.StopSoundsEvent;

public class AudioSystem {

    private final SoundManager soundManager;
    private final MusicManager musicManager;
    private final EventListenerManager listenerGlobalManager = new EventListenerManager();
    private final EventListenerManager listenerGameplayManager = new EventListenerManager();
    private final EventBus globalBus;
    private final EventBus gameplayBus;
    
    public AudioSystem(final AudioManager audio, EventBus globalBus, EventBus gameplayBus) {
        this.soundManager = audio.getSound();
        this.musicManager = audio.getMusic();       
        this.globalBus = globalBus;
        this.gameplayBus = gameplayBus;
        this.subscribeGlobalEvents();
    }
    
    public void subscribeGameplayEvents() {
    	this.subscribeEvents(this.listenerGameplayManager, this.gameplayBus);
    }
    
    private void subscribeGlobalEvents() {
    	this.subscribeEvents(this.listenerGlobalManager, this.globalBus);
    }
    
    private void subscribeEvents(EventListenerManager listeners, EventBus bus) {
    	listeners.add(bus, PlaySoundEvent.class, this::onPlaySound);
    	listeners.add(bus, StopSoundsEvent.class, this::onStopSounds);

    	listeners.add(bus, PlayMusicEvent.class, this::onPlayMusic);
    	listeners.add(bus, StopMusicEvent.class, this::onStopMusic);
    }
    
    // -------------------- Sonidos --------------------
    private void onPlaySound(PlaySoundEvent event) {
        this.soundManager.play(event.sound);
    }

    private void onStopSounds(StopSoundsEvent event) {
        this.soundManager.stopAll();
    }

    // -------------------- Música --------------------
    private void onPlayMusic(PlayMusicEvent event) {
        this.musicManager.play(event.track);
    }

    private void onStopMusic(StopMusicEvent event) {
        this.musicManager.stop();
    }

    // -------------------- Volumen y mute --------------------
    public void setSoundVolume(float volume) {
        this.soundManager.setVolume(volume);
    }

    public void toggleSoundMute() {
        this.soundManager.toggleMute();
    }

    public void setMusicVolume(float volume) {
        this.musicManager.setVolume(volume);
    }

    public void toggleMusicMute() {
        this.musicManager.toggleMute();
    }

    public void dispose() {
        this.soundManager.dispose();
        this.musicManager.dispose();
        this.listenerGameplayManager.clear();
        this.listenerGlobalManager.clear();
    }
    
    public void disposeGameplay() {
    	this.listenerGameplayManager.clear();
    }

    public void resetGameplay() {
    	this.listenerGameplayManager.clear();
    	this.subscribeGameplayEvents();
    }
    
}
