package com.haloofwar.game.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.enumerators.NPCType;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.NewEntityEvent;
import com.haloofwar.game.factories.NPCFactory;
import com.haloofwar.interfaces.Spawner;

public class NPCSpawner implements Spawner {

    private final TiledMap map;
    private final NPCFactory npcFactory;
    private final EventBus gameplayBus;
    
    public NPCSpawner(final NPCFactory npcFactory, final EventBus gameplayBus, final TiledMap map) {
        this.map = map;
        this.npcFactory = npcFactory;
        this.gameplayBus = gameplayBus;
    }

    @Override
    public void spawn() {
        MapLayer npcLayer = map.getLayers().get("NPCs");
        
        if (npcLayer == null) {
            System.out.println("No se encontró el layer 'NPCs' en el mapa: " + map);
            return;
        }

        for (MapObject obj : npcLayer.getObjects()) {
            if (obj instanceof RectangleMapObject rectObj) {

                String npcName = obj.getName();
                
                if (npcName == null) {
                    npcName = obj.getProperties().get("name", String.class);
                }

                if (npcName == null) {
                    continue;
                }

                NPCType npcType = NPCType.getByName(npcName);
                if (npcType == null) {
                    continue;
                }

                Rectangle rect = rectObj.getRectangle();
                float x = rect.x;
                float y = rect.y;
                
                final Entity npc = this.npcFactory.create(-1, npcType, x, y);
                this.gameplayBus.publish(new NewEntityEvent(npc));
            }
        }
    }


}
