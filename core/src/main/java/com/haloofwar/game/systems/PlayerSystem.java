package com.haloofwar.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.context.GameplayContext;
import com.haloofwar.common.enumerators.InventoryItemStatus;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.AddWeaponToInventoryEvent;
import com.haloofwar.engine.events.AttackEvent;
import com.haloofwar.engine.events.AttackEventOnline;
import com.haloofwar.engine.events.BuyVestmentEvent;
import com.haloofwar.engine.events.EquipVestmentEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.InteractEvent;
import com.haloofwar.engine.events.SuccessfulAttackEvent;
import com.haloofwar.engine.events.UnlockAchievementEvent;
import com.haloofwar.engine.events.UpdateCurrentWeaponEvent;
import com.haloofwar.engine.events.UpdateVelocityEvent;
import com.haloofwar.engine.events.online.InteractEventOnline;
import com.haloofwar.engine.events.online.InventoryUpdateEvent;
import com.haloofwar.game.components.AchievementInventoryComponent;
import com.haloofwar.game.components.AnimationComponent;
import com.haloofwar.game.components.EquipmentComponent;
import com.haloofwar.game.components.InventoryComponent;
import com.haloofwar.game.components.MovementComponent;
import com.haloofwar.game.components.PlayerComponent;
import com.haloofwar.game.components.TransformComponent;
import com.haloofwar.game.components.VestmentInventoryComponent;
import com.haloofwar.game.factories.ObjectFactory;
import com.haloofwar.game.factories.WeaponFactory;
import com.haloofwar.interfaces.Weapon;

public class PlayerSystem extends EventEntitySystem {
	private TextureManager texture;
	private GameplayContext context;
	private Entity player;
	
	public PlayerSystem(Entity player, EventBus bus, TextureManager texture, GameplayContext context) {
		super(bus);
		this.listenerManager.add(bus, InteractEvent.class, this::onInteract);
		this.listenerManager.add(bus, InventoryUpdateEvent.class, this::onUpdateInventory);
		this.listenerManager.add(bus, AddWeaponToInventoryEvent.class, this::onUpdateWeaponInventory);
		this.listenerManager.add(bus, UpdateCurrentWeaponEvent.class, this::onUpdateCurrentWeapon);
		this.listenerManager.add(bus, AttackEvent.class, this::onAttack);
		this.listenerManager.add(bus, UnlockAchievementEvent.class, this::onUnlockAchievement);
		this.listenerManager.add(bus, BuyVestmentEvent.class, this::onBuyVestment);
		this.listenerManager.add(bus, EquipVestmentEvent.class, this::onEquipVestment);
		this.listenerManager.add(bus, SuccessfulAttackEvent.class, this::onSuccessfulAttack);
		this.listenerManager.add(bus, UpdateVelocityEvent.class, this::onUpdateVelocity);
		this.bus = bus;
		this.texture = texture;
		this.player = player;
		this.context = context;
	}
	
	private void onBuyVestment(BuyVestmentEvent event) {
		PlayerComponent pc = this.player.getComponent(PlayerComponent.class);
		
		if(pc.type.equals(event.vestment.getPlayerType())) {
			VestmentInventoryComponent vestmentInventory = this.player.getComponent(VestmentInventoryComponent.class);
			vestmentInventory.unlockVestment(event.vestment);
		}
	}
	
	private void onEquipVestment(EquipVestmentEvent event) {
		PlayerComponent pc = this.player.getComponent(PlayerComponent.class);
		
		if(pc.type.equals(event.vestment.getPlayerType())) {
			VestmentInventoryComponent vestmentInventory = this.player.getComponent(VestmentInventoryComponent.class);
			vestmentInventory.equipVestment(event.vestment);
			AnimationComponent animation = this.player.getComponent(AnimationComponent.class);
			Texture newVestmentTexture = this.texture.get(event.vestment.getDetailType());
			animation.changeSpritesheet(newVestmentTexture);
		}
	}
	
	private void onUnlockAchievement(UnlockAchievementEvent event) {
		AchievementInventoryComponent achievementInventory = this.player.getComponent(AchievementInventoryComponent.class);
		achievementInventory.unlockAchievement(event.achievement);
	}
	
	private void onInteract(InteractEvent event) {
		if(this.player != null && this.player == this.context.getCurrentPlayer()) {
			PlayerComponent pc = this.player.getComponent(PlayerComponent.class);
			this.bus.publish(new InteractEventOnline(pc.type, event.isPressed()));
		}
	}
	
	private void onUpdateInventory(InventoryUpdateEvent event) {		
		
		PlayerType playerType = this.player.getComponent(PlayerComponent.class).type;
		
		if(playerType.equals(event.playerType)) {
			InventoryComponent inventory = this.player.getComponent(InventoryComponent.class);
			
			if(event.status.equals(InventoryItemStatus.REMOVE)) {
				inventory.remove(event.itemType, event.quantity);
			} else if(event.status.equals(InventoryItemStatus.ADD)) {
				Entity entity = ObjectFactory.createItem(event.identifier, new Rectangle(0,0,0,0), event.itemType, this.texture);
				inventory.add(entity, event.quantity);
			} else {
				System.out.println("Status de item de inventario no reconocido: " + event.status);
			}
		}	
	}
	
	private void onUpdateWeaponInventory(AddWeaponToInventoryEvent event) {
		PlayerType playerType = this.player.getComponent(PlayerComponent.class).type;
		
		if(playerType.equals(event.playerType)) {
			EquipmentComponent equipment = this.player.getComponent(EquipmentComponent.class);
			
			if(equipment.isInInventory(event.weapon.getName())){
				System.out.println("El jugador ya tiene este arma en su inventario: " + event.weapon.getName());
				return;
			}
			
			Entity weaponEntity = WeaponFactory.createWeapon(event.weapon);
			
			equipment.weaponInventory.add(weaponEntity);
			System.out.println("Arma agregada al inventario del jugador: " + event.weapon.getName());
		}
	}
	
	private void onUpdateCurrentWeapon(UpdateCurrentWeaponEvent event) {
		PlayerType playerType = this.player.getComponent(PlayerComponent.class).type;
		
		if(playerType.equals(event.playerType)) {
			EquipmentComponent equipment = this.player.getComponent(EquipmentComponent.class);
			
			Weapon currentWeapon = equipment.getCurrentWeapon();
			
			if(currentWeapon.getName().equals(event.weapon.getName())) {
				System.out.println("Ya tienes equipado el arma que has seleccionado!");
				return;
			} 
			
			Entity newCurrentWeapon = equipment.getEntityByWeapon(event.weapon);
			
			if(newCurrentWeapon == null) {
				System.out.println("Se ha intentado seleccionar un arma que no existe en el inventario...");
				return;
			}
			
			equipment.currentWeapon = newCurrentWeapon;
		}
	}
	
	private void onAttack(AttackEvent event) {
	    if (this.player != this.context.getCurrentPlayer()) {
	        return;
	    }

	    EquipmentComponent equipment = this.player.getComponent(EquipmentComponent.class);
	    PlayerComponent pc = this.player.getComponent(PlayerComponent.class);
	    
	    if (equipment == null) {
	    	return;
	    }
	    
	    this.bus.publish(new AttackEventOnline(pc.type, event.pressed, event.x, event.y));

	    Entity weaponEntity = equipment.getEntityCurrentWeapon();
	    
	    if (weaponEntity == null) {
	    	return;
	    }
	}
	
	private void onSuccessfulAttack(SuccessfulAttackEvent event) {
		PlayerComponent pc = this.player.getComponent(PlayerComponent.class);
		if(pc.type != event.type) {
			return;
		}
		
		pc.isAttacking = event.isPressed;
	}
	
	private void onUpdateVelocity(UpdateVelocityEvent event) {
		final TransformComponent tc = this.player.getComponent(TransformComponent.class);
		
		if(tc.identifier == event.IDENTIFIER) {
			final MovementComponent mc = this.player.getComponent(MovementComponent.class);
			mc.speed = event.CURRENT_SPEED;
			mc.speedDuration = event.CURRENT_DURATION;
		}
	}
}
