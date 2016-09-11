package com.gmail.snowmanam2.blockcondense;

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
	
	public void setAvailableAmount(int amount)  {
		availableAmount = amount;
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
	
	public int processConversion(InventoryWrapper inv, int productQty) {
		inv.removeItem(item);
		int leftover = getLeftoverAmount(productQty);
		
		inv.addItem(item, leftover);
		
		return leftover;
	}
}
