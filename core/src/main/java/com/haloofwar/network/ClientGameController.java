package com.haloofwar.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.haloofwar.common.enumerators.AchievementType;
import com.haloofwar.common.enumerators.BulletType;
import com.haloofwar.common.enumerators.EnemyType;
import com.haloofwar.common.enumerators.InventoryItemStatus;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.enumerators.NPCType;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.PowerUpType;
import com.haloofwar.common.enumerators.SoundType;
import com.haloofwar.common.enumerators.VestmentType;
import com.haloofwar.common.managers.TextureManager;
import com.haloofwar.engine.Entity;
import com.haloofwar.engine.events.AddWeaponToInventoryEvent;
import com.haloofwar.engine.events.AlreadyExistsGameEvent;
import com.haloofwar.engine.events.AttackEventOnline;
import com.haloofwar.engine.events.BuyVestmentEvent;
import com.haloofwar.engine.events.ConnectEvent;
import com.haloofwar.engine.events.DisconnectEvent;
import com.haloofwar.engine.events.EndGameEvent;
import com.haloofwar.engine.events.EquipVestmentEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.FinishGameEvent;
import com.haloofwar.engine.events.GameOverEvent;
import com.haloofwar.engine.events.IncorrectCodeEvent;
import com.haloofwar.engine.events.LevelCompletedEvent;
import com.haloofwar.engine.events.LevelEnterEvent;
import com.haloofwar.engine.events.MeleeAttackEvent;
import com.haloofwar.engine.events.NewEntityEvent;
import com.haloofwar.engine.events.PlaySoundEvent;
import com.haloofwar.engine.events.RemoveEntityByIdentifierEvent;
import com.haloofwar.engine.events.RespawnPlayerEvent;
import com.haloofwar.engine.events.ShootBulletEvent;
import com.haloofwar.engine.events.ShowTextEvent;
import com.haloofwar.engine.events.SpawnEnemyEvent;
import com.haloofwar.engine.events.StartGameEvent;
import com.haloofwar.engine.events.SuccessfulAttackEvent;
import com.haloofwar.engine.events.SwitchToSpectatorEvent;
import com.haloofwar.engine.events.TalkEvent;
import com.haloofwar.engine.events.UnlockAchievementEvent;
import com.haloofwar.engine.events.UpdateCurrentWeaponEvent;
import com.haloofwar.engine.events.UpdateHealthEvent;
import com.haloofwar.engine.events.UpdateLevelDataEvent;
import com.haloofwar.engine.events.UpdateVelocityEvent;
import com.haloofwar.engine.events.online.BuyVestmentEventOnline;
import com.haloofwar.engine.events.online.BuyWeaponEventOnline;
import com.haloofwar.engine.events.online.ChangeWeaponEventOnline;
import com.haloofwar.engine.events.online.ConfirmationNextCutsceneEvent;
import com.haloofwar.engine.events.online.EquipVestmentEventOnline;
import com.haloofwar.engine.events.online.InteractEventOnline;
import com.haloofwar.engine.events.online.InventoryUpdateEvent;
import com.haloofwar.engine.events.online.LevelEnterEventOnline;
import com.haloofwar.engine.events.online.MeleeAttackEventOnline;
import com.haloofwar.engine.events.online.MoveEventOnline;
import com.haloofwar.engine.events.online.NextCutsceneEventOnline;
import com.haloofwar.engine.events.online.RemotePositionUpdateEventOnline;
import com.haloofwar.engine.events.online.SellObjectEventOnline;
import com.haloofwar.game.factories.ObjectFactory;
import com.haloofwar.interfaces.Weapon;
import com.haloofwar.threads.ClientThread;

public class ClientGameController implements GameController {
	private ClientThread client;
	private EventBus gameplayBus;
	private EventBus globalBus;
	private TextureManager textureManager;
	private EventListenerManager listenerManager = new EventListenerManager();
	
	public ClientGameController(final EventBus gameplayBus, final EventBus globalBus, final TextureManager textureManager, final boolean CREATE_GAME, final PlayerType chosenType) {
		this.client = new ClientThread(this);
		this.client.start();
		this.gameplayBus = gameplayBus;
		this.globalBus = globalBus;
		this.textureManager = textureManager;

		if(CREATE_GAME && chosenType != null) {
			this.client.sendFirstMessage(chosenType);	
		}
	}
	
	public ClientGameController(final EventBus gameplayBus, final EventBus globalBus, final TextureManager textureManager, final boolean CREATE_GAME) {
		this(gameplayBus, globalBus, textureManager, CREATE_GAME, null);
	}
	
	@Override
	public void resetListeners() {
		this.listenerManager.clear();
		this.subscribeGameplayEvents();
	}
	
	private void subscribeGameplayEvents() {
		this.listenerManager.add(this.gameplayBus, MoveEventOnline.class, this::onMove);
		this.listenerManager.add(this.gameplayBus, InteractEventOnline.class, this::onInteract);
		this.listenerManager.add(this.gameplayBus, BuyWeaponEventOnline.class, this::onBuyWeapon);
		this.listenerManager.add(this.gameplayBus, ChangeWeaponEventOnline.class, this::onChangeWeapon);
		this.listenerManager.add(this.gameplayBus, NextCutsceneEventOnline.class, this::onAdvanceCutscene);
		this.listenerManager.add(this.gameplayBus, LevelEnterEventOnline.class, this::onEnterLevel);
		this.listenerManager.add(this.gameplayBus, MeleeAttackEventOnline.class, this::onMeleeAttack);
		this.listenerManager.add(this.gameplayBus, SellObjectEventOnline.class, this::onSellObject);
		this.listenerManager.add(this.gameplayBus, BuyVestmentEventOnline.class, this::onBuyVestment);
		this.listenerManager.add(this.gameplayBus, EquipVestmentEventOnline.class, this::onEquipVestment);
		this.listenerManager.add(this.gameplayBus, AttackEventOnline.class, this::onAttack);
	}
	
	public void sendJoinGame(final int code) {
		this.client.sendMessage("JoinGame:" + code);
	}

	@Override
	public void connect(final int code) {
		String newText = "Esperando a otro jugador para empezar la partida... \n Codigo de partida: " + code; 
		this.globalBus.publish(new ConnectEvent(newText));
	}
	
	@Override
	public void connect(String message) {
		this.globalBus.publish(new ConnectEvent(message));
	}
	
	@Override
	public void gameAlreadyExists() {
		this.globalBus.publish(new AlreadyExistsGameEvent());
	}

	@Override
	public void incorrectCode() {
		this.globalBus.publish(new IncorrectCodeEvent());
	}
	
	@Override
	public void onServerClosed() {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new DisconnectEvent());
		});
	}

	@Override
	public void startGame(final int kratosId, final int masterchiefId, PlayerType playerType) {
		this.subscribeGameplayEvents();
		Gdx.app.postRunnable(() -> {
			this.globalBus.publish(new StartGameEvent(kratosId, masterchiefId, playerType));
		});
	}

	@Override
	public void updateMovement(int identifier, float x, float y, float dirX, float dirY) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new RemotePositionUpdateEventOnline(identifier, x, y, dirX, dirY));
		});
	}
	
	@Override
	public void spawnItem(final int identifier, ObjectType type, float x, float y) {
		Gdx.app.postRunnable(() -> {
			Entity entity = ObjectFactory.createItem(identifier, new Rectangle(x,y,16,16), type, this.textureManager);
			this.gameplayBus.publish(new NewEntityEvent(entity));
		});
	}

	@Override
	public void updateInventory(final int identifier, PlayerType playerType, int amount, ObjectType itemType, InventoryItemStatus status) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new InventoryUpdateEvent(identifier, itemType, amount, playerType, status));
			this.gameplayBus.publish(new PlaySoundEvent(SoundType.ITEM_PICKUP));
		});
	}

	@Override
	public void buySuccessWeapon(Weapon weapon, PlayerType playerType, final int PRICE) {
		System.out.println("Se ha comprado un arma con exito!: " + PRICE);
		this.gameplayBus.publish(new InventoryUpdateEvent(ObjectType.MONEDA_DE_ORO, PRICE, playerType, InventoryItemStatus.REMOVE));
		this.gameplayBus.publish(new AddWeaponToInventoryEvent(weapon, playerType));
		this.gameplayBus.publish(new PlaySoundEvent(SoundType.PURCHASE));
	}

	@Override
	public void changeWeaponSuccess(PlayerType playerType, Weapon weapon) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new UpdateCurrentWeaponEvent(playerType, weapon));
			this.gameplayBus.publish(new PlaySoundEvent(SoundType.SELECT_WEAPON));
		});
	}
	
	@Override
	public void updateScene(LevelSceneType type) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new LevelEnterEvent(type));
		});
	}
	
	@Override
	public void spawnEnemy(int IDENTIFIER, EnemyType type, float x, float y) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new SpawnEnemyEvent(IDENTIFIER, type, x, y));
		});
	}
	
	@Override
	public void advanceCutscene() {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new ConfirmationNextCutsceneEvent());
		});
	}
	
	@Override
	public void gameOver() {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new GameOverEvent());
		});
	}
	
	@Override
	public void meleeAttck(int identifier, float x, float y, float width, float height, int damage, float range) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new MeleeAttackEvent(identifier, x, y, width, height, damage, range));
		});
	}

	@Override
	public void removeEntity(int identifier) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new RemoveEntityByIdentifierEvent(identifier));
		});
	}
	
	@Override
	public void levelCompleted(LevelSceneType levelType) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new LevelCompletedEvent(levelType));
		});
	}
	

	@Override
	public void talk(NPCType npcType) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new TalkEvent(npcType.getName(), npcType.getLines(), npcType.getHead()));
		});
	}
	
	@Override
	public void spawnPowerUp(int identifier, PowerUpType powerUpType, float x, float y) {
		Gdx.app.postRunnable(() -> {
			Entity entity = ObjectFactory.createPowerUp(identifier, new Rectangle(x,y,16,16), powerUpType, this.textureManager);
			this.gameplayBus.publish(new NewEntityEvent(entity));
		});
	}
	
	@Override
	public void switchToSpectator() {
		this.gameplayBus.publish(new SwitchToSpectatorEvent());
	}
	
	@Override
	public void respawnPlayer(PlayerType type) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new RespawnPlayerEvent(type));
		});
	}
	
	@Override
	public void endGame() {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new EndGameEvent());
		});
	}
	
	@Override
	public void sellSuccessObject(PlayerType player, ObjectType object, int amount) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new InventoryUpdateEvent(object, 1, player, InventoryItemStatus.REMOVE));
			this.gameplayBus.publish(new InventoryUpdateEvent(ObjectType.MONEDA_DE_ORO, amount, player, InventoryItemStatus.ADD));
			this.gameplayBus.publish(new PlaySoundEvent(SoundType.PURCHASE));
		});
	}
	
	@Override
	public void showText(String message, final int duration) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new ShowTextEvent(message, duration));
		});
	}
	

	@Override
	public void updateHealth(int identifier, int currentHealth, final int currentShield, final float visibility) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new UpdateHealthEvent(identifier, currentHealth, currentShield, visibility));
		});
	}
	
	@Override
	public void unlockAchievement(AchievementType achievement) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new UnlockAchievementEvent(achievement));
		});
	}
	
	@Override
	public void buyVestmentSuccess(VestmentType vestment, final int PRICE, final PlayerType PLAYER_TYPE) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new InventoryUpdateEvent(ObjectType.MONEDA_DE_ORO, PRICE, PLAYER_TYPE, InventoryItemStatus.REMOVE));
			this.gameplayBus.publish(new BuyVestmentEvent(vestment));
			this.gameplayBus.publish(new PlaySoundEvent(SoundType.PURCHASE));
		});
	}

	@Override
	public void equipVestmentSuccess(VestmentType vestment) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new EquipVestmentEvent(vestment));
		});
	}
	
	@Override
	public void finishGame() {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new FinishGameEvent());
		});
	}
	
	@Override
	public void attack(PlayerType type, boolean isPressed) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new SuccessfulAttackEvent(type, isPressed));
		});
	}
	
	@Override
	public void shootBullet(int identifier, float x, float y, float dirX, float dirY, int damage, float speed, BulletType type) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new ShootBulletEvent(identifier, x, y, dirX, dirY, damage, speed, type));
		});
	}
	
	@Override
	public void updateLevelData(int enemiesDefeated, int wavesPassed) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new UpdateLevelDataEvent(enemiesDefeated, wavesPassed));
		});
	}
	
	@Override
	public void updateVelocity(final int IDENTIFIER, final float CURRENT_SPEED, final float CURRENT_DURATION) {
		Gdx.app.postRunnable(() -> {
			this.gameplayBus.publish(new UpdateVelocityEvent(IDENTIFIER, CURRENT_SPEED, CURRENT_DURATION));
		});
	}
	
	@Override
	public void onMove(MoveEventOnline event) {
		String message = "Move:" + event.getIDENTIFIER() + ":" + event.getDirection().toString() + ":" + event.isPressed();
		this.client.sendMessage(message);
	}

	@Override
	public void onInteract(InteractEventOnline event) {
		String message = "Interact:" + event.type.getName() + ":" + event.isPressed;
		this.client.sendMessage(message);
	}
	
	@Override
	public void onBuyWeapon(BuyWeaponEventOnline event) {
		String message = "BuyWeapon:" + event.playerType.getName() + ":" + event.weapon.getName();
		this.client.sendMessage(message);
	}

	@Override
	public void onChangeWeapon(ChangeWeaponEventOnline event) {
		String message = "ChangeWeapon:" + event.playerType.getName() + ":" + event.weapon.getName();
		this.client.sendMessage(message);
	}
	
	@Override
	public void onAdvanceCutscene(NextCutsceneEventOnline event) {
		String message = "NextCutscene";
		this.client.sendMessage(message);
	}
	
	@Override
	public void onEnterLevel(LevelEnterEventOnline event) {
		String message = "EnterLevel:" + event.type.getName();
		this.client.sendMessage(message);
	}

	@Override
	public void onMeleeAttack(MeleeAttackEventOnline event) {
		String message = "MeleeAttack:" + event.identifier + ":" + event.x + ":" + event.y + ":" + event.width + ":" + event.height + ":" + event.damage + ":" + event.range;
		this.client.sendMessage(message);
	}
	
	@Override
	public void onSellObject(SellObjectEventOnline event) {
		String message = "SellObject:" + event.player.getName() + ":" + event.object.getName();
		this.client.sendMessage(message);
	}
	
	@Override
	public void onBuyVestment(BuyVestmentEventOnline event) {
		String message = "BuyVestment:" + event.vestmentType.getName();
		this.client.sendMessage(message);
	}

	@Override
	public void onEquipVestment(EquipVestmentEventOnline event) {
		String message = "EquipVestment:" + event.vestmentType.getName();
		this.client.sendMessage(message);
	}
	
	@Override
	public void onAttack(AttackEventOnline event) {
		String message = "Attack:" + event.type.getName() + ":" + event.pressed + ":" + event.x + ":" + event.y;
		this.client.sendMessage(message);
	}
	
	@Override
	public void dispose() {
		this.listenerManager.clear();
		
		if(this.client.isConnected()) {
			this.client.sendMessage("Disconnect");
		}

		this.client.terminate();
	}
}
