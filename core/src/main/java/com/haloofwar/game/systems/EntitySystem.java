package com.haloofwar.game.systems;

import java.util.ArrayList;

import com.haloofwar.engine.Entity;
import com.haloofwar.game.components.TransformComponent;
import com.haloofwar.interfaces.Disposable;
import com.haloofwar.interfaces.Registrable;

/*
 * Aclaracion: No todos los sitemas es necesario que extiendan de esta pero
 * es una base para aquellos que necesiten manejar entidades con componentes 
 * en especifico
 * 
 * */

public abstract class EntitySystem implements Registrable, Disposable {

    protected final ArrayList<Entity> entities = new ArrayList<>();

    @Override
    public void register(Entity e) {
        if (!this.entities.contains(e)) {
            this.entities.add(e);
        }
    }

    @Override
    public void unregister(Entity e) {
        this.entities.remove(e);
    }
    
    @Override
    public void unregisterByIdentifier(final int identifier) {
    	boolean found = false;
    	int i = 0;
    	
    	while(i < this.entities.size() && !found) {
    		if(this.entities.get(i).hasComponent(TransformComponent.class)) {
    			TransformComponent tc = this.entities.get(i).getComponent(TransformComponent.class);
    			if(tc.identifier == identifier) {
    				found = true;
    				this.entities.remove(i);
    			} else {
    				i++;
    			}
    		} else {
    			i++;
    		}
    	}
    }
    
    
    protected Entity getByIdentifier(final int identifier) {
    	boolean found = false;
    	Entity entity = null;
    	int i = 0;
    	
    	while(i < this.entities.size() && !found) {
    		if(this.entities.get(i).hasComponent(TransformComponent.class)) {
    			TransformComponent tc = this.entities.get(i).getComponent(TransformComponent.class);
    			if(tc.identifier == identifier) {
    				found = true;
    				entity = this.entities.get(i);
    			} else {
    				i++;
    			}
    		} else {
    			i++;
    		}
    	}
    	
    	return entity;
    }
    
    
    @Override
    public void dispose() {
    	this.entities.clear();
    }
}
