package com.haloofwar.game.components;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.haloofwar.common.enumerators.SpriteState;
import com.haloofwar.interfaces.AnimatedEntityDescriptor;
import com.haloofwar.interfaces.ArmedEntityDescriptor;

public class AnimationSet {
    private final Map<SpriteState, Animation<TextureRegion>> animations = new HashMap<>();

    public AnimationSet(Texture spritesheet, AnimatedEntityDescriptor descriptor) {
        this.animations.put(SpriteState.IDLE, loadAnimation(spritesheet, 0, descriptor.getIdleLength(), descriptor.getWidth(), descriptor.getHeight(), 0.3f, Animation.PlayMode.LOOP));
        
        // Solo carga la animación de WALK si walkLength es mayor que 0
        if (descriptor.getWalkLength() > 0) {
            this.animations.put(SpriteState.WALK, loadAnimation(spritesheet, 1, descriptor.getWalkLength(), descriptor.getWidth(), descriptor.getHeight(), 0.1f, Animation.PlayMode.LOOP));
        }
    }

    public AnimationSet(Texture spritesheet, ArmedEntityDescriptor descriptor) {
        this.animations.put(SpriteState.IDLE, loadAnimation(spritesheet, 0, descriptor.getIdleLength(), descriptor.getWidth(), descriptor.getHeight(), 0.3f, Animation.PlayMode.LOOP));
        
        // Solo carga la animación de WALK si walkLength es mayor que 0
        if (descriptor.getWalkLength() > 0) {
            this.animations.put(SpriteState.WALK, loadAnimation(spritesheet, 1, descriptor.getWalkLength(), descriptor.getWidth(), descriptor.getHeight(), 0.1f, Animation.PlayMode.LOOP));
        }

        // Solo carga la animación de ATTACK si attackLength es mayor que 0
        if (descriptor.getAttackLength() > 0) {
            this.animations.put(SpriteState.ATTACK, loadAnimation(spritesheet, 3, descriptor.getAttackLength(), descriptor.getWidth(), descriptor.getHeight(), 0.1f, Animation.PlayMode.NORMAL));
        }
    }

    private Animation<TextureRegion> loadAnimation(Texture sheet, int row, int frameCount, int frameWidth,
            int frameHeight, float frameDuration, PlayMode mode) {
			// Si frameCount es menor o igual a 0, retornamos null para evitar errores
			if (frameCount <= 0) {
			return null; // No cargamos la animación si la longitud es 0 o menor
			}
			
			TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
			Array<TextureRegion> frames = new Array<>();
			
			// Si solo hay 1 fotograma, no se necesita recorrer más
			if (frameCount == 1) {
			frames.add(tmp[row][0]);
			} else {
			// Verifica que la fila solicitada existe en el spritesheet y tiene suficientes frames
			if (row >= tmp.length || frameCount > tmp[row].length) {
			System.out.println("Advertencia: La fila o los frames solicitados no existen. fila=" + row + ", frames=" + frameCount);
			return null; // No cargar la animación si no hay suficientes fotogramas
			}
			
			for (int i = 0; i < frameCount; i++) {
			frames.add(tmp[row][i]);
			}
}

return new Animation<>(frameDuration, frames, mode);
}


    public Animation<TextureRegion> getAnimation(SpriteState key) {
        return this.animations.get(key);
    }
}
