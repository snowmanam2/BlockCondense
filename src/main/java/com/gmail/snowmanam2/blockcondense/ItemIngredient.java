package com.gmail.snowmanam2.blockcondense;

public class ItemIngredient implements Ingredient {
	private ItemType item;
	private int requiredAmount;
	private int availableAmount = 0;
	
	public ItemIngredient (ItemType item, int requiredAmount) {
		this.item = item;
		this.requiredAmount = requiredAmount;
	}
	
	public String getName () {
		return item.getName();
	}
	
	public int getLeftoverAmount(int productQuantity) {
		return availableAmount - requiredAmount*productQuantity;
	}
	
	public int loadMaximumProductAmount(ConversionContext context) {
		if (requiredAmount > 0) {
			availableAmount = context.getInventory().getItemAmount(item);
			return availableAmount / requiredAmount;
		} else {
			return Integer.MAX_VALUE;
		}
	}
	
	public int processConversion(ConversionContext context, int productQty) {
		InventoryWrapper inventory = context.getInventory();
		
		if (requiredAmount > 0) {
			inventory.removeItem(item);
		
			int leftover = getLeftoverAmount(productQty);
		
			inventory.addItem(item, leftover);
		
			return leftover;
		} else {
			return 0;
		}
	}


}
