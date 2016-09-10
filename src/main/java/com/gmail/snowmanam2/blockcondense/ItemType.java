package com.gmail.snowmanam2.blockcondense;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/* Workaround wrapper class for Bukkit's inventory system */

public class ItemType {
	private ItemStack item;
	String name;
	
	public ItemType (Material m) {
		item = new ItemStack(m);
		name = getDefaultName();
	}
	
	public ItemType (Material m, short durability) {
		item = new ItemStack(m);
		item.setDurability(durability);
		name = getDefaultName();
	}
	
	public ItemType (ItemStack stack) {
		item = stack.clone();
		name = getDefaultName();
	}
	
	public ItemStack toItemStack (int quantity) {
		ItemStack stack = item.clone();
		stack.setAmount(quantity);
		return stack;
	}
	
	private String getDefaultName () {
		return item.getType().toString().toLowerCase().replaceAll("_", " ");
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String newname) {
		name = newname;
	}
	
	/* getAmountInInventory
	 * Returns the total quantity of all matching stacks from the given Inventory.
	 * Note the items counted in this method differ from Bukkit's Inventory.all
	 * because the latter doesn't count stacks with different quantities,
	 * which doesn't make much sense.
	 */
	public int getAmountInInventory (Inventory inv) {
		int qty = 0;
		
		for (ItemStack stack : inv.getContents()) {
			if (stack != null) {
				if (stack.isSimilar(item)) {
					qty += stack.getAmount();
				}
			}
		}
		
		return qty;
	}
	
	/* removeFromInventory
	 * Removes all matching stacks from the given inventory.
	 * Note this differs from Bukkit's Inventory.remove because
	 * the latter doesn't count stacks with different quantities,
	 * which doesn't make much sense.
	 */
	public void removeFromInventory (Inventory inv) {
		for (ItemStack stack : inv.getContents()) {
			if (stack != null) {
				if (stack.isSimilar(item)) {
					inv.remove(stack);
				}
			}
		}
	}
	
	/* getStacksQuantity
	 * Returns the total quantity of a list of stacks, as returned by the Bukkit
	 * Inventory methods.
	 */
	@SuppressWarnings("unchecked")
	private int getStacksQuantity (Map<Integer, ? extends ItemStack> stacks) {
		Iterator<?> itr = stacks.entrySet().iterator();
		
		int qty = 0;
		
		while (itr.hasNext()) {
			Map.Entry<Integer, ItemStack> pair = (Map.Entry<Integer, ItemStack>) itr.next();
			ItemStack is = pair.getValue();
			
			qty += is.getAmount();
		}
		
		return qty;
	}
	
	/* addToInventory
	 * Adds a quantity of this ItemType to the given inventory
	 * Returns the amount that could not be added to the inventory
	 */
	public int addToInventory (Inventory inv, int quantity) {
		ItemStack stack = item.clone();
		stack.setAmount(quantity);
		
		Map<Integer, ItemStack> remainder = inv.addItem(stack);
		
		return getStacksQuantity(remainder);
	}
}
