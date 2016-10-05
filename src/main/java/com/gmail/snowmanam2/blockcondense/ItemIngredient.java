package com.gmail.snowmanam2.blockcondense;

public class ItemIngredient implements Ingredient {
	private ItemType item;
	private int requiredAmount;
	private int availableAmount = 0;
	private InventoryWrapper inventory;
	
	public ItemIngredient (ItemType item, int requiredAmount, InventoryWrapper inventory) {
		this.item = item;
		this.requiredAmount = requiredAmount;
		this.inventory = inventory;
		
		availableAmount = inventory.getItemAmount(item);
	}
	
	public ItemType getItem() {
		return item;
	}
	
	public String getName () {
		return item.getName();
	}
	
	public int getRequiredAmount() {
		return requiredAmount;
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
	
	public int processConversion(int productQty) {
		inventory.removeItem(item);
		int leftover = getLeftoverAmount(productQty);
		
		inventory.addItem(item, leftover);
		
		return leftover;
	}
}
