package com.haloofwar.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.haloofwar.common.enumerators.AchievementType;
import com.haloofwar.common.enumerators.BulletType;
import com.haloofwar.common.enumerators.EnemyType;
import com.haloofwar.common.enumerators.FireArmType;
import com.haloofwar.common.enumerators.InventoryItemStatus;
import com.haloofwar.common.enumerators.LevelSceneType;
import com.haloofwar.common.enumerators.MeleeWeaponType;
import com.haloofwar.common.enumerators.NPCType;
import com.haloofwar.common.enumerators.ObjectType;
import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.PowerUpType;
import com.haloofwar.common.enumerators.VestmentType;
import com.haloofwar.interfaces.Weapon;
import com.haloofwar.network.GameController;

public class ClientThread extends Thread {
    private boolean end = false;
    private boolean connected = false;
    private boolean isFirstMessage = false;
    private DatagramSocket socket;
    private int serverPort = 5000;
    private InetAddress serverIp;

    private GameController controller;
    private RetryThread retry;
    private boolean isTerminated = false;
    private PlayerType chosenType;
    
    public ClientThread(GameController controller) {
        this.controller = controller;

        try {
            this.socket = new DatagramSocket();
            this.serverIp = InetAddress.getByName("255.255.255.255");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        this.retry = new RetryThread(this);
        
    }

    @Override
    public void run() {
        do {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                this.socket.receive(packet);
                this.processMessage(packet);
            } catch (SocketException e) {
                if (!this.end) e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } while (!this.end);
    }

    public void sendFirstMessage(PlayerType type) {
        if (!this.isFirstMessage) {
            this.isFirstMessage = true;
            this.chosenType = type;
            this.retry.start();
        } else {
            System.out.println("No se puede mandar un primer mensaje de nuevo!");
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        String[] parts = message.split(":");

        switch (parts[0]) {
            case "AlreadyConnected": 
            	this.alreadyConnected(); 
            	break;
            case "Connected":  // puede llegar Connected:CorrectCode o Connected:Code:codigo
            	this.connected(parts, packet); 
            	break;
            case "GameAlreadyExists": // error 1 al crear
            	this.controller.gameAlreadyExists();
            	break;
            case "IncorrectCode": // error 1 al unirte
            	this.controller.incorrectCode();
            	break;
            case "FullRoom": // error 2 al unirte 
            	break;
            case "StartGame": 
            	this.startGame(parts); 
            	break;
            case "UpdatePosition": 
            	this.updatePosition(parts);
            	break;
            case "SpawnItem": 
            	this.spawnItem(parts);
            	break;
            case "UpdateInventory": 
            	this.updateInventory(parts); 
            	break;
            case "BuySuccessWeapon": 
            	this.buySuccessWeapon(parts); 
            	break;
            case "ChangeSuccessWeapon": 
            	this.changeSuccessWeapon(parts); 
            	break;
            case "EnterLevel": 
            	this.enterLevel(parts); 
            	break;
            case "NextCutscene": 
            	this.nextCutscene(); 
            	break;
            case "SpawnEnemy": 
            	this.spawnEnemy(parts); 
            	break;
            case "GameOver":
            	this.controller.gameOver();
            	break;
            case "ShootBullet":
            	this.shootBullet(parts);
            	break;
            case "RemoveEntity":
            	this.removeEntity(parts);
            	break;
            case "LevelCompleted":
            	this.levelCompleted(parts);
            	break;
            case "StartDialogue":
            	this.startDialogue(parts);
            	break;
            case "SpawnPowerUp": 
            	this.spawnPowerUp(parts);
            	break;
            case "SwitchToSpectator":
            	this.switchToSpectator();
            	break;
            case "RespawnPlayer":
            	this.respawnPlayer(parts);
            	break;
            case "EndGame":
            	this.controller.endGame();
            	break;
            case "SellObject":
            	this.sellObject(parts);
            	break;
            case "ShowText":
            	this.showText(parts);
            	break;
            case "ReceiveDamage":
            	this.updateHealth(parts);
            	break;
            case "UnlockAchievement":
            	this.unlockAchievement(parts);
            	break;
            case "BuyVestment":
            	this.buyVestment(parts);
            	break;
            case "EquipVestment":
            	this.equipVestment(parts);
            	break;
            case "FinishGame":
            	this.controller.finishGame();
            	break;
            case "SuccessfulAttack":
            	this.attack(parts);
            	break;
            case "UpdateLevelData":
            	this.updateLevelData(parts);
            	break;
            case "UpdateVelocity":
            	this.updateVelocity(parts);
            	break;
            case "ServerClosed": 
            	this.serverClosed(); 
            	break;
            default: 
            	System.out.println("Mensaje inesperado: " + parts[0]); 
            	break;
        }
    }

    
    private void alreadyConnected() {
        System.out.println("Ya estás conectado");
    }

    private void connected(String[] parts, DatagramPacket packet) {
        if(parts[1].equals("CorrectCode")) {
        	this.controller.connect("Esperando a iniciar la partida");
        } else if(parts[1].equals("Code")) {
        	final int code = Integer.parseInt(parts[2]);
        	this.controller.connect(code);
        } else {
        	System.out.println("Se ha recibido un mensaje con un formato inesperado... | Connected Client Thread");
        	return;
        }
        
        this.serverIp = packet.getAddress();
        this.connected = true;
    }

    private void startGame(String[] parts) {
    	if(!this.validateParts(parts, 4, "StartGame")) {
    		
    	}
    	
        PlayerType type = PlayerType.getPlayerByName(parts[1]);
        if (type == null) {
            System.out.println("Problema al empezar la partida | StartGame");
            return;
        }
        
        final int kratosId = Integer.parseInt(parts[2]);
        final int masterchiefId = Integer.parseInt(parts[3]);
        
        this.controller.startGame(kratosId, masterchiefId, type);
    }

    private void updatePosition(String[] parts) {
        if (!validateParts(parts, 6, "UpdatePosition")) {
        	return;
        }

        int identifier = parseInt(parts[1], "UpdatePosition");
        float x = parseFloat(parts[2], "UpdatePosition");
        float y = parseFloat(parts[3], "UpdatePosition");
        float dirX = parseFloat(parts[4], "UpdatePosition");
        float dirY = parseFloat(parts[5], "UpdatePosition");

        this.controller.updateMovement(identifier, x, y, dirX, dirY);
    }

    private void spawnItem(String[] parts) {
        if (!validateParts(parts, 5, "SpawnItem")) return;

        final int identifier = Integer.parseInt(parts[1]);
        ObjectType type = ObjectType.getByName(parts[2]);
        float x = parseFloat(parts[3], "SpawnItem");
        float y = parseFloat(parts[4], "SpawnItem");

        this.controller.spawnItem(identifier, type, x, y);
    }

    private void updateInventory(String[] parts) {
        if (!validateParts(parts, 6, "UpdateInventory")) return;

        final int identifier = Integer.parseInt(parts[1]);
        PlayerType player = PlayerType.getPlayerByName(parts[2]);
        ObjectType itemType = ObjectType.getByName(parts[3]);
        int amount = parseInt(parts[4], "UpdateInventory");
        InventoryItemStatus status = parseInventoryStatus(parts[5]);

        this.controller.updateInventory(identifier, player, amount, itemType, status);
    }

    private void buySuccessWeapon(String[] parts) {
        if (!validateParts(parts, 4, "BuySuccessWeapon")) return;

        PlayerType player = PlayerType.getPlayerByName(parts[1]);
        Weapon weapon = parseWeapon(parts[2]);
        int remainingMoney = parseInt(parts[3], "BuySuccessWeapon");

        this.controller.buySuccessWeapon(weapon, player, remainingMoney);
    }

    private void changeSuccessWeapon(String[] parts) {
        if (!validateParts(parts, 3, "ChangeSuccessWeapon")) return;

        PlayerType player = PlayerType.getPlayerByName(parts[1]);
        Weapon weapon = parseWeapon(parts[2]);

        this.controller.changeWeaponSuccess(player, weapon);
    }

    private void enterLevel(String[] parts) {
        if (!validateParts(parts, 2, "EnterLevel")) return;

        LevelSceneType level = LevelSceneType.getLevelByName(parts[1]);
        this.controller.updateScene(level);
    }

    private void nextCutscene() {
        this.controller.advanceCutscene();
    }

    private void spawnEnemy(String[] parts) {
        if (!validateParts(parts, 5, "SpawnEnemy")) return;

        int id = parseInt(parts[1], "SpawnEnemy");
        EnemyType type = EnemyType.getEnemyTypeByName(parts[2]);
        float x = parseFloat(parts[3], "SpawnEnemy");
        float y = parseFloat(parts[4], "SpawnEnemy");

        this.controller.spawnEnemy(id, type, x, y);
    }
    
    private void shootBullet(String parts[]) {
		if(!this.validateParts(parts, 9, "ShootBullet")) {
			return;
		}
		
		final int identifier = Integer.parseInt(parts[1]);
		float x = Float.parseFloat(parts[2]);
		float y = Float.parseFloat(parts[3]);
		float dirX = Float.parseFloat(parts[4]);
		float dirY = Float.parseFloat(parts[5]);
		int damage = Integer.parseInt(parts[6]);
		float speed = Float.parseFloat(parts[7]);
		BulletType type = BulletType.getByName(parts[8]);
		
		this.controller.shootBullet(identifier, x, y, dirX, dirY, damage, speed, type);
	}
    
    private void removeEntity(String parts[]) {
    	if(!this.validateParts(parts, 2, "RemoveEntity")) {
			return;
		}
    	
    	final int identifier = Integer.parseInt(parts[1]);
    	this.controller.removeEntity(identifier);
    }
    
    private void levelCompleted(String parts[]) {
    	if(!this.validateParts(parts, 2, "LevelCompleted")) {
			return;
		}
    	
    	final LevelSceneType levelType = LevelSceneType.getLevelByName(parts[1]);
    	this.controller.levelCompleted(levelType);
    }

    private void startDialogue(String parts[]) {
    	if(!this.validateParts(parts, 2, "StartDialogue")) {
			return;
		}
    	
    	final NPCType npcType = NPCType.getByName(parts[1]);
    	this.controller.talk(npcType);
    }
    
    private void spawnPowerUp(String[] parts) {
        if (!validateParts(parts, 5, "SpawnPowerUp")) {
        	return;
        }

        final int identifier = Integer.parseInt(parts[1]);
        PowerUpType type = PowerUpType.getByName(parts[2]);
        float x = parseFloat(parts[3], "SpawnPowerUp");
        float y = parseFloat(parts[4], "SpawnPowerUp");

        this.controller.spawnPowerUp(identifier, type, x, y);
    }
    
    private void switchToSpectator() {
    	this.controller.switchToSpectator();
    }
    
    private void respawnPlayer(String[] parts) {
    	 if (!validateParts(parts, 2, "RespawnPlayer")) {
         	return;
         }
    	 
    	 final PlayerType type = PlayerType.getPlayerByName(parts[1]);
    	 this.controller.respawnPlayer(type);
    }
    
    private void sellObject(String[] parts) {
    	 if (!validateParts(parts, 4, "SellObject")) {
          	return;
         }
    	 
    	 final PlayerType player = PlayerType.getPlayerByName(parts[1]);
    	 final ObjectType object = ObjectType.getByName(parts[2]);
    	 final int amount = Integer.parseInt(parts[3]);
    	 
    	 if(player == null || object == null) {
    		 System.out.println("Ha ocurrido un error al intentar actualizar la venta del jugador | ClientThread - sellObject");
    		 return;
    	 }
    	 
    	 this.controller.sellSuccessObject(player, object, amount);
    }
    
    private void showText(String[] parts) {
    	 if (!validateParts(parts, 3, "ShowText")) {
           	return;
         }
    	 
    	 final String message = parts[1];
    	 final int duration = Integer.parseInt(parts[2]);
    	 
    	 this.controller.showText(message, duration);
    }
    
    private void updateHealth(String[] parts) {
    	if (!validateParts(parts, 5, "ReceiveDamage")) {
           	return;
        }
    	
    	final int identifier = Integer.parseInt(parts[1]);
    	final int currentHealth = Integer.parseInt(parts[2]);
    	final int currentShield = Integer.parseInt(parts[3]);
    	final float visibility = Float.parseFloat(parts[4]);
    	
    	this.controller.updateHealth(identifier, currentHealth, currentShield, visibility);
    	
    }
    
    private void unlockAchievement(String[] parts) {
		if (!validateParts(parts, 2, "UnlockAchievement")) {
		   	return;
		}
		
		final AchievementType achievement = AchievementType.getByName(parts[1]);
		this.controller.unlockAchievement(achievement);
    }
    
	private void buyVestment(String parts[]) {
		if(!this.validateParts(parts, 2, "BuyVestment")) {
			return;
		}
		
		final VestmentType type = VestmentType.getByName(parts[1]);
		final int PRICE = type.getPrice();
		final PlayerType PLAYER_TYPE = type.getPlayerType();
		
		this.controller.buyVestmentSuccess(type, PRICE, PLAYER_TYPE);
	}
	
	private void equipVestment(String parts[]) {
		if(!this.validateParts(parts, 2, "EquipVestment")) {
			return;
		}
		
		final VestmentType type = VestmentType.getByName(parts[1]);
		this.controller.equipVestmentSuccess(type);
	}
	
	private void attack(String parts[]) {
		if(!this.validateParts(parts, 3, "Attack")) {
			return;
		}
		
		final PlayerType type = PlayerType.getPlayerByName(parts[1]);
		final boolean isPressed = Boolean.parseBoolean(parts[2]);
		
		this.controller.attack(type, isPressed);
	}
	
	private void updateLevelData(String parts[]) {
		if(!this.validateParts(parts, 3, "UpdateLevelData")) {
			return;
		}
		
		final int ENEMIES_DEFEATED = Integer.parseInt(parts[1]);
		final int WAVES_PASSED = Integer.parseInt(parts[2]);
		
		this.controller.updateLevelData(ENEMIES_DEFEATED, WAVES_PASSED);
	}
	
	private void updateVelocity(String parts[]) {
		if(!this.validateParts(parts, 4, "UpdateVelocity")) {
			return;
		}
		
		final int IDENTIFIER = Integer.parseInt(parts[1]);
		final float CURRENT_SPEED = Float.parseFloat(parts[2]);
		final float CURRENT_DURATION = Float.parseFloat(parts[3]);
		
		this.controller.updateVelocity(IDENTIFIER, CURRENT_SPEED, CURRENT_DURATION);
	}
    
    private void serverClosed() {
    	System.out.println("Ejecutando server closed");
        this.controller.onServerClosed();
    }

    private boolean validateParts(String[] parts, int expected, String type) {
        if (parts.length != expected) {
            System.out.println("Formato inválido " + type + ": esperado " + expected + " partes, recibido " + parts.length);
            return false;
        }
        return true;
    }

    private int parseInt(String str, String context) {
        try { return Integer.parseInt(str); }
        catch (NumberFormatException e) {
            System.out.println("Número inválido en " + context + ": " + str);
            return 0;
        }
    }

    private float parseFloat(String str, String context) {
        try { return Float.parseFloat(str); }
        catch (NumberFormatException e) {
            System.out.println("Número float inválido en " + context + ": " + str);
            return 0f;
        }
    }

    private Weapon parseWeapon(String name) {
        Weapon w = FireArmType.getByName(name);
        if (w == null) w = MeleeWeaponType.getByName(name);
        if (w == null) System.out.println("Arma inválida: " + name);
        return w;
    }

    private InventoryItemStatus parseInventoryStatus(String str) {
        if ("ADD".equalsIgnoreCase(str)) return InventoryItemStatus.ADD;
        if ("REMOVE".equalsIgnoreCase(str)) return InventoryItemStatus.REMOVE;
        System.out.println("Status de inventario inválido: " + str);
        return null;
    }

    public void sendMessage(String message) {
    	
        if (socket == null || socket.isClosed()) {
            System.out.println("[WARN] Intento de enviar mensaje con socket cerrado: " + message);
            return;
        }
    	
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, this.serverIp, this.serverPort);
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
    	if(this.isTerminated) {
    		return;
    	}
    	this.end = true;
        this.socket.close();
        this.interrupt();
        if (this.retry != null) {
        	this.retry.interrupt();
        }
    }

    public boolean isConnected() { return this.connected; }
    public boolean isEnd() { return this.end; }
    
    public PlayerType getChosenType() {
		return this.chosenType;
	}
}
