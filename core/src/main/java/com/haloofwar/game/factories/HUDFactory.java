package com.haloofwar.game.factories;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.common.enumerators.FireArmType;
import com.haloofwar.common.enumerators.MeleeWeaponType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.UIAsset;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.cameras.GameStaticCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.components.AchievementInventoryComponent;
import com.haloofwar.game.components.EquipmentComponent;
import com.haloofwar.game.components.HealthComponent;
import com.haloofwar.game.components.InventoryComponent;
import com.haloofwar.game.components.MovementComponent;
import com.haloofwar.game.components.NameComponent;
import com.haloofwar.game.components.PlayerComponent;
import com.haloofwar.game.components.VestmentInventoryComponent;
import com.haloofwar.game.dependences.LevelData;
import com.haloofwar.ui.HUD;
import com.haloofwar.ui.components.AchievementsPopup;
import com.haloofwar.ui.components.DialogueBox;
import com.haloofwar.ui.components.EquipmentPopup;
import com.haloofwar.ui.components.HUDComponent;
import com.haloofwar.ui.components.InterfaceLevel;
import com.haloofwar.ui.components.InterfaceLobby;
import com.haloofwar.ui.components.InventoryPopup;
import com.haloofwar.ui.components.MinimapPopup;
import com.haloofwar.ui.components.PlayerInfoRenderer;
import com.haloofwar.ui.components.Popup;
import com.haloofwar.ui.components.ShopPopup;
import com.haloofwar.ui.components.VestmentPopup;
import com.haloofwar.ui.hud.LevelHUD;
import com.haloofwar.ui.hud.LobbyHUD;

public final class HUDFactory {

    private final GameContext context;
    private final GameStaticCamera camera;
    private final SpriteBatch batch;
    private final EventBus gameplayBus;
    
    public HUDFactory(GameContext context) {
        this.context = context;
        this.camera = context.getStaticCamera();
        this.batch = context.getRender().getBatch();
        this.gameplayBus = context.getGameplay().getBus();
    }

    public HUD createLevelHUD(final LevelData data) {
        final Entity player = context.getGameplay().getCurrentPlayer();

        final HealthComponent healthComp = player.getComponent(HealthComponent.class);
        final NameComponent nameComp = player.getComponent(NameComponent.class);
        final PlayerComponent playerComp = player.getComponent(PlayerComponent.class);
        final EquipmentComponent equipment = player.getComponent(EquipmentComponent.class);
        final MovementComponent movement = player.getComponent(MovementComponent.class);
        
        final PlayerInfoRenderer info = new PlayerInfoRenderer(
                context.getRender().getBatch(),
                context.getRender().getFont().getSmallFont(),
                context.getRender().getFont().getTitleFont(),
                context.getTexture(),
                nameComp,
                healthComp,
                movement,
                equipment,
                context.getTexture().get(Background.PLACEHOLDER_ICON),
                context.getTexture().get(playerComp.type.getHead()),
                context.getGameplay().getBus()
        );

        
        final InterfaceLevel level = new InterfaceLevel(data, context.getRender().getBatch(), context.getRender().getFont().getMediumFont());

        final HUDComponent[] components = {info, level};
        
        return new LevelHUD(components, this.camera, this.batch, this.gameplayBus, this.context.getRender().getFont());
    }

    public HUD createLobbyHUD() {
    	final TextureManager texture = this.context.getTexture();
    	final BitmapFont font = this.context.getRender().getFont().getMediumFont();
    	final InventoryComponent inventoryComponent = this.context.getGameplay().getCurrentPlayer().getComponent(InventoryComponent.class);
    	final EquipmentComponent equipmentComponent = this.context.getGameplay().getCurrentPlayer().getComponent(EquipmentComponent.class);
    	final AchievementInventoryComponent achievementComponent = this.context.getGameplay().getCurrentPlayer().getComponent(AchievementInventoryComponent.class);
    	final VestmentInventoryComponent vestmentComponent = this.context.getGameplay().getCurrentPlayer().getComponent(VestmentInventoryComponent.class);
    	final PlayerType PLAYER_TYPE = this.context.getGameplay().getCurrentPlayer().getComponent(PlayerComponent.class).type;
    	
    	final InterfaceLobby lobby = new InterfaceLobby(this.batch, texture, font, inventoryComponent);
    	
        final DialogueBox dialogue = new DialogueBox(this.batch, texture.get(UIAsset.DIALOGUE_BOX), font, this.gameplayBus);
        
        final InventoryPopup inventory = new InventoryPopup(this.gameplayBus, texture, font, inventoryComponent, this.batch, PLAYER_TYPE);
   
        final ShopPopup shop = new ShopPopup(this.gameplayBus, texture, font, this.shopInitializer(PLAYER_TYPE), equipmentComponent, PLAYER_TYPE, this.batch);

        final EquipmentPopup equipment = new EquipmentPopup(this.gameplayBus, texture, font, equipmentComponent, PLAYER_TYPE, this.batch);
        
        final AchievementsPopup achievement = new AchievementsPopup(this.gameplayBus, texture, font, new ArrayList<>(Arrays.asList(achievementComponent.getAchievements())), this.batch);
        
        final VestmentPopup vestment = new VestmentPopup(this.gameplayBus, texture, font, vestmentComponent, this.batch);
        
        final MinimapPopup minimap = new MinimapPopup(this.gameplayBus, texture, font, this.batch);
        
        final Popup[] popups = {shop, inventory, equipment, achievement, vestment, minimap};
        final HUDComponent[] components = {lobby, dialogue};
        
        return new LobbyHUD(components, popups, this.camera, this.batch, this.gameplayBus);
    }
    
    private ArrayList<Entity> shopInitializer(PlayerType type) {
    	ArrayList<Entity> items = new ArrayList<Entity>();
    	
    	if(type.equals(PlayerType.MASTER_CHIEF)) {
    		items.add(WeaponFactory.createWeapon(FireArmType.PISTOLA));
	    	items.add(WeaponFactory.createWeapon(FireArmType.RIFLE_ASALTO));
	    	items.add(WeaponFactory.createWeapon(FireArmType.FRANCO));
	    	return items;
    	} else {
	    	items.add(WeaponFactory.createWeapon(MeleeWeaponType.ESPADA));
	    	items.add(WeaponFactory.createWeapon(MeleeWeaponType.HACHA_AVANZADA)); 	
	    	items.add(WeaponFactory.createWeapon(MeleeWeaponType.HACHA_ELITE));
    	}
    	
    	return items;
    }
}
