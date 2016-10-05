package com.gmail.snowmanam2.blockcondense;

import org.bukkit.entity.Player;

public class ConversionContext {
	private Player player;
	private InventoryWrapper inventory;
	private EconomyWrapper economy;
	
	public ConversionContext (Player player) {
		this.player = player;
		this.inventory = new InventoryWrapper (player.getInventory());
		this.economy = new EconomyWrapper (player);
	}
	
	public EconomyWrapper getEconomy() {
		return economy;
	}
	
	public InventoryWrapper getInventory() {
		return inventory;
	}
	
	public void sendMessage (String message) {
		player.sendMessage(message);
	}
}
