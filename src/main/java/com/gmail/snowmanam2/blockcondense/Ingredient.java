package com.gmail.snowmanam2.blockcondense;

import org.bukkit.inventory.Inventory;

public class Ingredient {
	private ItemType item;
	private int requiredAmount;
	private int availableAmount = 0;
	
	public Ingredient (ItemType it, int amount) {
		item = it;
		requiredAmount = amount;
	}
	
	public ItemType getItem() {
		return item;
	}
	
	public int getRequiredAmount() {
		return requiredAmount;
	}
	
	public void loadAvailableAmount(Inventory inv)  {
		availableAmount = item.getAmountInInventory(inv);
	}
	
	public int getAvailableAmount() {
		return availableAmount;
	}
	
	public int getLeftoverAmount(int productQuantity) {
		return availableAmount - requiredAmount*productQuantity;
	}
	
	public int getProductAmount() {
		return availableAmount / requiredAmount;
	}
	
	public int processConversion(Inventory inv, int productQty) {
		item.removeFromInventory(inv);
		int leftover = getLeftoverAmount(productQty);
		
		item.addToInventory(inv, leftover);
		
		return leftover;
	}
}
