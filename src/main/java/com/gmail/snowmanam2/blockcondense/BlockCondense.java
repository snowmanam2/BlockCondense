package com.gmail.snowmanam2.blockcondense;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("condense")) {
			Player p = (Player) sender;
			
			ItemType lapis = new ItemType(Material.INK_SACK, (short) 4);
			lapis.setName("lapis"); 
			
			this.convertStacks(p, lapis, 9, new ItemType(Material.LAPIS_BLOCK));
			this.convertStacks(p, new ItemType(Material.GOLD_INGOT), 9, new ItemType(Material.GOLD_BLOCK));
			this.convertStacks(p, new ItemType(Material.IRON_INGOT), 9, new ItemType(Material.IRON_BLOCK));
			this.convertStacks(p, new ItemType(Material.EMERALD),    9, new ItemType(Material.EMERALD_BLOCK));
			this.convertStacks(p, new ItemType(Material.DIAMOND),    9, new ItemType(Material.DIAMOND_BLOCK));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gold")) {
			Player p = (Player) sender;
			
			
			this.convertStacks(p, new ItemType(Material.GOLD_NUGGET), 9, new ItemType(Material.GOLD_INGOT));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gunpowder")) {
			Player p = (Player) sender;
			
			ItemType gunpowder = new ItemType(Material.SULPHUR);
			gunpowder.setName("gunpowder");
			
			ItemType tnt = new ItemType(Material.TNT);
			tnt.setName("TNT");
			
			this.convertStacks(p, gunpowder, 5, tnt);
			return true;
		}
		return false;
	}
	
	private void convertStacks (Player p, ItemType ingredient, int divisor, ItemType product) {
		this.convertStacks(p, ingredient, divisor, product, false);
	}
	
	private int convertStacks (Player p, ItemType ingredient, int divisor, ItemType product, boolean returnExcess) {
		
		Inventory inv = p.getInventory();
		
		int ingredientQty = ingredient.getAmountInInventory(inv);
		int productQty = ingredientQty / divisor;
		int leftoverQty = ingredientQty % divisor;
		
		
		String productName = product.getName();
		String ingredientName = ingredient.getName();
		if (productQty > 0) {
			
			ingredient.removeFromInventory(inv);
			product.addToInventory(inv, productQty);
			
			p.sendMessage(ChatColor.GREEN.toString()+"Converted "+ingredientQty+" "+ingredientName+" to "+productQty+" "+productName);
			
			if (!returnExcess) {
				ingredient.addToInventory(inv, leftoverQty);
				p.sendMessage(ChatColor.GREEN.toString()+"Returned "+leftoverQty+" "+ingredientName);
			}
		} else {
			p.sendMessage(ChatColor.RED.toString()+"Not enough "+ingredientName);
		}
		
		return productQty;
		
	}
}
