package com.gmail.snowmanam2.blockcondense;

import java.util.ArrayList;
import java.util.List;

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
			
			List<Ingredient> lapisIngredients = new ArrayList<Ingredient>();
			lapisIngredients.add(new Ingredient(lapis, 9));
			convertStacks(p, lapisIngredients, new ItemType(Material.LAPIS_BLOCK));
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new Ingredient(new ItemType(Material.GOLD_INGOT), 9));
			convertStacks(p, goldIngredients, new ItemType(Material.GOLD_BLOCK));
			
			List<Ingredient> ironIngredients = new ArrayList<Ingredient>();
			ironIngredients.add(new Ingredient(new ItemType(Material.IRON_INGOT), 9));
			convertStacks(p, ironIngredients, new ItemType(Material.IRON_BLOCK));
			
			List<Ingredient> emeraldIngredients = new ArrayList<Ingredient>();
			emeraldIngredients.add(new Ingredient(new ItemType(Material.EMERALD), 9));
			convertStacks(p, emeraldIngredients, new ItemType(Material.EMERALD_BLOCK));
			
			List<Ingredient> diamondIngredients = new ArrayList<Ingredient>();
			diamondIngredients.add(new Ingredient(new ItemType(Material.DIAMOND), 9));
			convertStacks(p, diamondIngredients, new ItemType(Material.DIAMOND_BLOCK));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gold")) {
			Player p = (Player) sender;
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new Ingredient(new ItemType(Material.GOLD_NUGGET), 9));
			this.convertStacks(p, goldIngredients, new ItemType(Material.GOLD_INGOT));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gunpowder")) {
			Player p = (Player) sender;
			
			ItemType gunpowder = new ItemType(Material.SULPHUR);
			gunpowder.setName("gunpowder");
			
			ItemType tnt = new ItemType(Material.TNT);
			tnt.setName("TNT");
			
			List<Ingredient> tntIngredients = new ArrayList<Ingredient>();
			tntIngredients.add(new Ingredient(gunpowder, 5));
			tntIngredients.add(new Ingredient(new ItemType(Material.SAND), 4));
			this.convertStacks(p, tntIngredients, tnt);
			
			return true;
		}
		return false;
	}
	
	private void convertStacks(Player p, List<Ingredient> ingredients, ItemType product) {
		Inventory inv = p.getInventory();
		
		int productQty = -1;
		
		for (Ingredient ingredient : ingredients) {
			ingredient.loadAvailableAmount(inv);
			
			int productAvailable = ingredient.getProductAmount();
			
			if (productQty < 0) {
				productQty = productAvailable;
			} else {
				productQty = Math.min(productQty, productAvailable);
			}
			
			if (productAvailable == 0) {
				p.sendMessage(ChatColor.RED.toString()+"Not enough "+ingredient.getItem().getName());
			}
		}
		
		if (productQty == 0) {
			return;
		}
		
		p.sendMessage(ChatColor.GREEN.toString()+"Converting ingredients to "+productQty+" "+product.getName());
		
		for (Ingredient ingredient : ingredients) {
			int leftoverQty = ingredient.processConversion(inv, productQty);
			p.sendMessage(ChatColor.GREEN.toString()+"Returned "+leftoverQty+" "+ingredient.getItem().getName());
		}
		
		product.addToInventory(inv, productQty);
	}
}
