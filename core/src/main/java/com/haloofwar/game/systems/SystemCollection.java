package com.haloofwar.game.systems;

import java.util.ArrayList;

import com.haloofwar.engine.Entity;
import com.haloofwar.interfaces.Disposable;
import com.haloofwar.interfaces.Registrable;
import com.haloofwar.interfaces.Renderable;
import com.haloofwar.interfaces.Updatable;

public class SystemCollection {
    private final ArrayList<Updatable> updateSystems = new ArrayList<>();
    private final ArrayList<Renderable> renderSystems = new ArrayList<>();
    private final ArrayList<Registrable> registrySystems = new ArrayList<>();
    private final ArrayList<Disposable> disposableSystems = new ArrayList<>();
    
    public void addEntity(Entity entity) {
        for (Registrable system : this.registrySystems) {
            system.register(entity);
        }
    }

    public void removeEntity(Entity entity) {
        for (Registrable system : this.registrySystems) {
            system.unregister(entity);
        }
    }
    
    public void removeEntityByIdentifier(final int identifier) {
    	for (Registrable system : this.registrySystems) {
            system.unregisterByIdentifier(identifier);
        }
    }
    
    public void addSystem(Object system) {
        if (system instanceof Updatable updatable) {
            this.updateSystems.add(updatable);
        }
        if (system instanceof Renderable renderable) {
            this.renderSystems.add(renderable);
        }
        if (system instanceof Registrable registrable) {
            this.registrySystems.add(registrable);
        }
        if (system instanceof Disposable disposable) {
            this.disposableSystems.add(disposable);
        }
    }

    
    public ArrayList<Disposable> getDisposableSystems() {
		return this.disposableSystems;
	}
    
    public ArrayList<Registrable> getRegistrySystems() {
		return this.registrySystems;
	}
    
    public ArrayList<Renderable> getRenderSystems() {
		return this.renderSystems;
	}
    
    public ArrayList<Updatable> getUpdateSystems() {
		return this.updateSystems;
	}
}
