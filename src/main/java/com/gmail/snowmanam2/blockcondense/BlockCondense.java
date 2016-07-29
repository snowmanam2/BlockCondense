package com.gmail.snowmanam2.blockcondense;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("condense")) {
			Player p = (Player) sender;
			
			this.convertStacks(p, Material.GOLD_INGOT, 9, Material.GOLD_BLOCK);
			this.convertStacks(p, Material.IRON_INGOT, 9, Material.IRON_BLOCK);
			this.convertStacks(p, Material.EMERALD,    9, Material.EMERALD_BLOCK);
			this.convertStacks(p, Material.DIAMOND,    9, Material.DIAMOND_BLOCK);
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gold")) {
			Player p = (Player) sender;
			
			
			this.convertStacks(p, Material.GOLD_NUGGET, 9, Material.GOLD_INGOT);
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gunpowder")) {
			Player p = (Player) sender;
			
			this.convertStacks(p, Material.SULPHUR, 5, Material.TNT);
			return true;
		}
		return false;
	}
	
	private void convertStacks (Player p, Material ingredient, int divisor, Material product) {
		this.convertStacks(p, ingredient, divisor, product, false);
	}
	
	private int convertStacks (Player p, Material ingredient, int divisor, Material product, boolean returnExcess) {
		
		Inventory inv = p.getInventory();
		HashMap<Integer, ? extends ItemStack> stacks = inv.all(ingredient);
		
		int ingredientQty = this.getStacksQuantity(stacks);
		int productQty = ingredientQty / divisor;
		int leftoverQty = ingredientQty % divisor;
		
		
		String productName = product.toString().toLowerCase().replaceAll("_", " ");
		String ingredientName = ingredient.toString().toLowerCase().replaceAll("_", " ");
		if (productQty > 0) {
			
			inv.remove(ingredient);
			this.addItemsToInventory(inv, product, productQty);
			
			p.sendMessage(ChatColor.GREEN.toString()+"Converted "+ingredientQty+" "+ingredientName+" to "+productQty+" "+productName);
			
			if (!returnExcess) {
				this.addItemsToInventory(inv, ingredient, leftoverQty);
				p.sendMessage(ChatColor.GREEN.toString()+"Returned "+leftoverQty+" "+ingredientName);
			}
		} else {
			p.sendMessage(ChatColor.RED.toString()+"Not enough "+ingredientName);
		}
		
		return productQty;
		
	}
	
	@SuppressWarnings("unchecked")
	private int getStacksQuantity (HashMap<Integer, ? extends ItemStack> stacks) {
		Iterator<?> itr = stacks.entrySet().iterator();
		
		int qty = 0;
		
		while (itr.hasNext()) {
			Map.Entry<Integer, ItemStack> pair = (Map.Entry<Integer, ItemStack>) itr.next();
			ItemStack is = pair.getValue();
			
			qty += is.getAmount();
		}
		
		return qty;
	}
	
	private int addItemsToInventory (Inventory inv, Material mat, int quantity) {
		
		if (quantity == 0) return 0;
		
		HashMap<Integer, ItemStack> leftover = inv.addItem(new ItemStack(mat, quantity));
		
		return this.getStacksQuantity(leftover);
	}
}
