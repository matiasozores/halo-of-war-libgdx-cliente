package com.haloofwar.common.enumerators;

import com.haloofwar.interfaces.EntityDescriptor;

public enum Background implements EntityDescriptor{
	MAIN_MENU("Fondo del menu principal", "images/background/main_menu.png"),
	PORTAL_MENU("Fondo portal", "images/background/main_menu2.png"),
	PLAYER_SELECTION("Fondo de seleccion", "images/background/player_selection.png"),
	GAME_OVER("Fondo de game over", "images/background/game_over.png"),
	VICTORY("Fondo de victoria", "images/background/victory.png"),
	
	INVENTORY("Fondo del inventario", "images/background/fullinventory.png"),
	EQUIPMENT("Fondo del equipamiento", "images/background/fullequipment.png"),
	SHOP("Fondo de la tienda", "images/background/fullshop.png"),
	VESTMENT("Fondo de la vestimentas","images/background/fullvestment.png"),
	ACHIEVEMENT("Fondo de los logros","images/background/fullachievement.png"),
	PIXEL("Pixel 1x1 ", "images/background/pixel.png"),
	SHOP_ICON("Icono de Tienda", "images/background/shop_icon.png"),
	INVENTORY_ICON("Icono de Inventario", "images/background/inventory_icon.png"),
	EQUIPMENT_ICON("Icono de Equipamiento", "images/background/equipment_icon.png"),
	ACHIEVEMENT_ICON("Icono de Logros", "images/background/achievement.png"),
	VESTMENT_ICON("Icono de Vestimenta", "images/background/vestment.png"),
	MINIMAP_ICON("Icono de Mapa", "images/background/openminimap.png"),
	PLACEHOLDER_ICON("Icono de Placeholder", "images/background/placeholder_icons.png"),
	BUY("Boton de comprar", "images/background/buy.png"),
	SELECT("Boton de seleccionar", "images/background/select.png"),
	MAP("Mapa en grande", "ui/hud/main.png"),
	MINIMAP("Minimapa", "ui/hud/minimap.png"),
	LOADING("Fondo de cargando", "images/background/loading.png");
	
	private final String name;
	private final String path;
	
	private Background(final String name, final String path) {
		this.name = name;
		this.path = path;
	}
	
	@Override
	public String getPath() {
		return this.path;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
