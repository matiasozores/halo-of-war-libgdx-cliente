package com.haloofwar.game.config;

import com.badlogic.gdx.graphics.Texture;
import com.haloofwar.common.enumerators.AchievementType;
import com.haloofwar.common.enumerators.EnemyType;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.PowerUpType;
import com.haloofwar.common.enumerators.VestmentType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.cameras.GameWorldCamera;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.game.components.AchievementComponent;
import com.haloofwar.game.components.AchievementInventoryComponent;
import com.haloofwar.game.components.AnimationComponent;
import com.haloofwar.game.components.AnimationSet;
import com.haloofwar.game.components.AnimationStateController;
import com.haloofwar.game.components.ArmedEntityAnimationStateController;
import com.haloofwar.game.components.Component;
import com.haloofwar.game.components.CrosshairComponent;
import com.haloofwar.game.components.DialogueComponent;
import com.haloofwar.game.components.EnemyComponent;
import com.haloofwar.game.components.EnemyMovementController;
import com.haloofwar.game.components.EnemyWeaponAIComponent;
import com.haloofwar.game.components.EquipmentComponent;
import com.haloofwar.game.components.HealthComponent;
import com.haloofwar.game.components.InventoryComponent;
import com.haloofwar.game.components.MovementComponent;
import com.haloofwar.game.components.NPCComponent;
import com.haloofwar.game.components.NameComponent;
import com.haloofwar.game.components.ObjectAnimationStateController;
import com.haloofwar.game.components.PlayerComponent;
import com.haloofwar.game.components.PortalComponent;
import com.haloofwar.game.components.PowerUpComponent;
import com.haloofwar.game.components.RemotePlayerMovementController;
import com.haloofwar.game.components.RenderComponent;
import com.haloofwar.game.components.ShieldComponent;
import com.haloofwar.game.components.SimpleRemotePlayerMovementController;
import com.haloofwar.game.components.StockComponent;
import com.haloofwar.game.components.TransformComponent;
import com.haloofwar.game.components.VestmentInventoryComponent;
import com.haloofwar.game.components.VisibilityComponent;
import com.haloofwar.game.factories.AchievementFactory;
import com.haloofwar.game.factories.WeaponFactory;
import com.haloofwar.interfaces.AnimatedEntityDescriptor;
import com.haloofwar.interfaces.ArmedEntityDescriptor;
import com.haloofwar.interfaces.EntityDescriptor;
import com.haloofwar.interfaces.MovementController;
import com.haloofwar.interfaces.StateController;
import com.haloofwar.interfaces.Talkable;
import com.haloofwar.interfaces.Weapon;

public class ComponentPresets {

	private static final int DEFAULT_HEALTH = 100;
	private static final float WIDTH = 32, HEIGHT = 32;
	private static final float DEFAULT_VELOCITY = 150;
	
	public static TransformComponent createTransform(int identifier, float x, float y) {
		return new TransformComponent(identifier, x, y, WIDTH, HEIGHT);
	}
	
	public static TransformComponent createTransform(int identifier, float x, float y, float width, float height) {
		return new TransformComponent(identifier, x, y, width, height);
	}
	
    public static AnimationComponent createBasicAnimation(AnimatedEntityDescriptor descriptor, TextureManager textureManager) {
    	Texture spritesheet = textureManager.get(descriptor);
    	AnimationSet set = new AnimationSet(spritesheet, descriptor);
        StateController controller = new AnimationStateController(set, descriptor);
        
        return new AnimationComponent(controller);
    }
    
    public static AnimationComponent createArmedAnimation(ArmedEntityDescriptor descriptor, TextureManager textureManager, PlayerComponent pc) {
    	Texture spritesheet = textureManager.get(descriptor);
    	AnimationSet set = new AnimationSet(spritesheet, descriptor);
        StateController controller = new ArmedEntityAnimationStateController(set, pc, descriptor);
        
        return new AnimationComponent(controller);
    }
    
    public static AnimationComponent createObjectAnimation(AnimatedEntityDescriptor descriptor, TextureManager textureManager, boolean lastState) {
    	Texture spritesheet = textureManager.get(descriptor);
    	AnimationSet set = new AnimationSet(spritesheet, descriptor);
    	StateController controller = new ObjectAnimationStateController(set, lastState, descriptor);
    	
        return new AnimationComponent(controller);
    }
    
    public static HealthComponent createHealth() {
        return new HealthComponent(DEFAULT_HEALTH);
    }
    
    public static MovementComponent createRemotePlayerMovement(final int IDENTIFIER, EventBus bus) {
		MovementController controller = new RemotePlayerMovementController(IDENTIFIER, bus);
		return new MovementComponent(controller, DEFAULT_VELOCITY);
	}
    
    public static MovementComponent createSimpleRemotePlayerMovement() {
		MovementController controller = new SimpleRemotePlayerMovementController();
		return new MovementComponent(controller, DEFAULT_VELOCITY);
	}
    
    public static MovementComponent createEnemyMovement(TransformComponent enemyTransform, TransformComponent playerTransform, VisibilityComponent visibilityComponent) {
        MovementController controller = new EnemyMovementController();
        return new MovementComponent(controller, DEFAULT_VELOCITY * 0.25f);
    }

    public static CrosshairComponent createCrosshair(PlayerType type, TextureManager manager, GameWorldCamera camera) {
    	Texture texture = manager.get(type.getCrosshair());
    	return new CrosshairComponent(texture, camera);
    }
    
    public static NameComponent createName(EntityDescriptor type) {
    	return new NameComponent(type.getName());
    }
    
    public static InventoryComponent createInventory() {
    	return new InventoryComponent();
    }

    public static RenderComponent createRender(EntityDescriptor type, TextureManager manager) {
    	Texture texture = manager.get(type);
        return new RenderComponent(texture);
    }

    public static StockComponent createPickup(ObjectType type) {
        return new StockComponent(type);
    }
    
    public static PlayerComponent createPlayer(PlayerType type) {
    	return new PlayerComponent(type);
    }
    
        public static EnemyComponent createEnemy(final EnemyType type) {
		return new EnemyComponent(type);
	}
    
    public static NPCComponent createVillager() {
    	return new NPCComponent();
    }
    
    public static DialogueComponent createDialogue(Talkable type, Texture avatar) {
    	return new DialogueComponent(type.getLines(), avatar);
    }
    
    public static PortalComponent createPortal(String targetScene) {
		return new PortalComponent(targetScene);
	}
    
    public static EnemyWeaponAIComponent createEnemyWeaponAI() {
    	return new EnemyWeaponAIComponent();
    }

    public static ShieldComponent createShield() {
		return new ShieldComponent();
	}
    
    public static ShieldComponent createShield(int shieldPoints) {
    	return new ShieldComponent(shieldPoints);
    }	
    
    public static PowerUpComponent createPowerUp(PowerUpType type) {
		return new PowerUpComponent(type, type.getAmount(), type.getDuration());
	}
    
    public static VisibilityComponent createVisibility() {
		return new VisibilityComponent();
	}
    
    public static AchievementComponent createAchievement(AchievementType type) {
    	return new AchievementComponent(type);
    }
    
    public static AchievementInventoryComponent createAchievements() {
		return new AchievementInventoryComponent(AchievementFactory.createAllAchievements());
	}
    
    public static VestmentInventoryComponent createVestment(PlayerType playerType) {
		return new VestmentInventoryComponent(VestmentType.getAllVestmentEntityByPlayer(playerType), playerType);
	}
    
    public static Component createWeaponComponent(Weapon weapon) {
		return weapon.createComponent();
	}
    
    public static EquipmentComponent createEquipment(Weapon type) {
        Entity weapon = WeaponFactory.createWeapon(type);
        EquipmentComponent equipment = new EquipmentComponent();

        equipment.currentWeapon = weapon;
        equipment.weaponInventory.add(weapon);
        
        return equipment;
    }
}

