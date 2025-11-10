package com.haloofwar.interfaces;

import com.haloofwar.engine.Entity;

public interface Registrable {
    void register(Entity entity);
    void unregister(Entity entity);
    void unregisterByIdentifier(final int identifier);
}
