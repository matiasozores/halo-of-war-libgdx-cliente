package com.haloofwar.game.components;

import java.util.ArrayList;

import com.haloofwar.common.enumerators.PlayerType;
import com.haloofwar.common.enumerators.VestmentType;
import com.haloofwar.engine.Entity;

public class VestmentInventoryComponent implements Component {
	private ArrayList<Entity> vestments = new ArrayList<>();
	private Entity currentVestment = null;
	private PlayerType vestmentType;
	
	public VestmentInventoryComponent(ArrayList<Entity> vestments, PlayerType vestmentType) {
		this.vestments = vestments;
		this.vestmentType = vestmentType;
		this.initializeCurrentVestment();
	}
	
	private void initializeCurrentVestment() { 
		VestmentType target = VestmentType.getDefaultByPlayerType(this.vestmentType);
		
		boolean found = false;
		int i = 0;
		
		while (!found && i < this.vestments.size()) {
			Entity vestmentEntity = this.vestments.get(i);
			if (vestmentEntity.hasComponent(VestmentComponent.class)) {
				VestmentComponent vestmentComp = vestmentEntity.getComponent(VestmentComponent.class);
				if (vestmentComp.type.equals(target)) {
					vestmentComp.unlocked = true;
					this.currentVestment = vestmentEntity;
					found = true;
				}
			} 
			
			i++;
		}
		
		if(!found) {
			System.out.println("Ha ocurrido un error al asignar la vestimenta inicial del jugador.");
		}
	}
	
	public void unlockVestment(VestmentType type) {
		boolean found = false;
		int i = 0;
		
		while (!found && i < this.vestments.size()) {
			Entity vestmentEntity = this.vestments.get(i);
			if (vestmentEntity.hasComponent(VestmentComponent.class)) {
				VestmentComponent vestmentComp = vestmentEntity.getComponent(VestmentComponent.class);
				if (vestmentComp.type.equals(type)) {
					vestmentComp.unlocked = true;
					found = true;
				}
			} 
			
			i++;
		}
		
		if(!found) {
			System.out.println("Ha ocurrido un error al desbloquear la vestimenta.");
		}
	}
	
	public boolean equipVestment(VestmentType type) {
		boolean found = false;
		int i = 0;
		
		while (!found && i < this.vestments.size()) {
			Entity vestmentEntity = this.vestments.get(i);
			if (vestmentEntity.hasComponent(VestmentComponent.class)) {
				VestmentComponent vestmentComp = vestmentEntity.getComponent(VestmentComponent.class);
				if (vestmentComp.type.equals(type) && vestmentComp.unlocked) {
					this.currentVestment = vestmentEntity;
					found = true;
				}
			} 
			
			i++;
		}
		
		if(!found) {
			System.out.println("Ha ocurrido un error al equipar la vestimenta: no se ha encontrado o no está desbloqueada.");
		}
		
		return found;
	}
	
	public Entity getCurrentVestment() {
		return this.currentVestment;
	}
	
	public ArrayList<Entity> getVestments() {
		return this.vestments;
	}
}
