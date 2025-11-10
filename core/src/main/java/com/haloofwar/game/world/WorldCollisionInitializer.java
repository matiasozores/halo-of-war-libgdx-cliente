package com.haloofwar.game.world;
	
import java.util.Set;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.enumerators.LayerType;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.NewEntityEvent;
import com.haloofwar.game.factories.PortalFactory;
	
	public class WorldCollisionInitializer {
	
	    public static void initializeMapColliders(Set<LevelSceneType> lockedLevels, final MapRenderer MAP, final TextureManager TEXTURE, final EventBus GAMEPLAY_BUS) {
	        initializeLayerEntities(lockedLevels, MAP, TEXTURE, GAMEPLAY_BUS, LayerType.PORTAL_LEVEL);
	        initializeLayerEntities(lockedLevels, MAP, TEXTURE, GAMEPLAY_BUS, LayerType.PORTAL_SECUNDARIA);
	        initializeLayerEntities(lockedLevels, MAP, TEXTURE, GAMEPLAY_BUS, LayerType.PORTAL_EXTRA);
	    }
	
	    private static void initializeLayerEntities(Set<LevelSceneType> lockedLevels, final MapRenderer MAP, final TextureManager TEXTURE, final EventBus GAMEPLAY_BUS, final LayerType TYPE) {
	        MapLayer layer = MAP.getMetaData().getTiledMap().getLayers().get(TYPE.getName());
	        if (layer == null) {
	        	return;
	        }
	
	        for (MapObject object : layer.getObjects()) {
	            if (object instanceof RectangleMapObject) {
	                Rectangle rect = ((RectangleMapObject) object).getRectangle();
	                Entity entity;
	
                	String teleportationTarget = object.getProperties().get("teleportation", String.class);
                	LevelSceneType type = LevelSceneType.getLevelByName(teleportationTarget);
                	boolean lastState;
                	if(type != null) {
                		if(lockedLevels.contains(type)) {
                			lastState = false;
                		} else {
                			lastState = true;
                		}
                	} else {
                		lastState = false;
                	}
                	
                	entity = PortalFactory.create(rect, TEXTURE, teleportationTarget, lastState, TYPE.getAnimated());
                	GAMEPLAY_BUS.publish(new NewEntityEvent(entity));
                
	            }
	        }
	    }
	}
