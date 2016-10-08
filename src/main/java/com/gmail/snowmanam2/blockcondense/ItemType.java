package com.gmail.snowmanam2.blockcondense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.IItemDb;

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
	
	public static ItemType buildFromString (String name) {
		Essentials essentials = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		if (essentials != null) {
			IItemDb itemDB = essentials.getItemDb();
			ItemStack itemStack;

			try {
				/* Wow, this is bad practice. Why doesn't Essentials use a proper exception? */
				itemStack = itemDB.get(name);
			} catch (Exception e) {
				return null;
			}
				
			ItemType type = new ItemType(itemStack);
			return type;
		} else {
			return null;
		}
	}
	
	public ItemStack toItemStack (int quantity) {
		ItemStack stack = item.clone();
		stack.setAmount(quantity);
		return stack;
	}
	
	private String getDefaultName () {
		Essentials essentials = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		if (essentials != null) {
			IItemDb itemDB = essentials.getItemDb();
			
			return itemDB.name(item);
		} else {
			return item.getType().toString().toLowerCase().replaceAll("_", " ");
		}
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String newname) {
		name = newname;
	}
	
	public Material getMaterial () {
		return item.getType();
	}
	
	public int getMaxStackSize () {
		return item.getMaxStackSize();
	}
	
	public boolean isSimilar (ItemType other) {
		return item.isSimilar(other.item);
	}
	


}
