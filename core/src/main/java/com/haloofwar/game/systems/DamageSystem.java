package com.haloofwar.game.systems;

import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.UpdateHealthEvent;
import com.haloofwar.game.components.AnimationComponent;
import com.haloofwar.game.components.HealthComponent;

public class DamageSystem extends EventEntitySystem {

    public DamageSystem(EventBus bus) {
    	super(bus);
        this.listenerManager.add(bus, UpdateHealthEvent.class, this::onDamage);
    }

    @Override
    public void register(Entity e) {
        if (e.hasComponent(HealthComponent.class)) {
            super.register(e);
        }
    }
    
    private void onDamage(UpdateHealthEvent event) {
    	Entity entity = this.getByIdentifier(event.identifier);
    	
    	if(entity == null) {
    		System.out.println("Ha ocurrido un problema al intentar actualizar la vida de una entidad... | DamageSystem");
    		return;
    	}
    	
    	HealthComponent health = entity.getComponent(HealthComponent.class);
    	
    	if(health.currentHealth > event.currentHealth || health.shield > event.currentShield) {
    		AnimationComponent anim = entity.getComponent(AnimationComponent.class);
    		if (anim != null) {
    		    anim.flashTime = 0.3f;
    		}

    	}
    	
    	health.currentHealth = event.currentHealth;
    	health.shield = event.currentShield;
    }
}

