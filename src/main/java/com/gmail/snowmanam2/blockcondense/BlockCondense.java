package com.gmail.snowmanam2.blockcondense;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("condense")) {
			Player p = (Player) sender;
			InventoryWrapper inv = new InventoryWrapper(p.getInventory());
			
			ItemType lapis = new ItemType(Material.INK_SACK, (short) 4);
			lapis.setName("lapis"); 
			
			List<Ingredient> lapisIngredients = new ArrayList<Ingredient>();
			lapisIngredients.add(new ItemIngredient(lapis, 9, inv));
			convertStacks(p, lapisIngredients, new ItemType(Material.LAPIS_BLOCK));
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new ItemIngredient(new ItemType(Material.GOLD_INGOT), 9, inv));
			convertStacks(p, goldIngredients, new ItemType(Material.GOLD_BLOCK));
			
			List<Ingredient> ironIngredients = new ArrayList<Ingredient>();
			ironIngredients.add(new ItemIngredient(new ItemType(Material.IRON_INGOT), 9, inv));
			convertStacks(p, ironIngredients, new ItemType(Material.IRON_BLOCK));
			
			List<Ingredient> emeraldIngredients = new ArrayList<Ingredient>();
			emeraldIngredients.add(new ItemIngredient(new ItemType(Material.EMERALD), 9, inv));
			convertStacks(p, emeraldIngredients, new ItemType(Material.EMERALD_BLOCK));
			
			List<Ingredient> diamondIngredients = new ArrayList<Ingredient>();
			diamondIngredients.add(new ItemIngredient(new ItemType(Material.DIAMOND), 9, inv));
			convertStacks(p, diamondIngredients, new ItemType(Material.DIAMOND_BLOCK));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gold")) {
			Player p = (Player) sender;
			InventoryWrapper inv = new InventoryWrapper(p.getInventory());
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new ItemIngredient(new ItemType(Material.GOLD_NUGGET), 9, inv));
			this.convertStacks(p, goldIngredients, new ItemType(Material.GOLD_INGOT));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gunpowder")) {
			Player p = (Player) sender;
			InventoryWrapper inv = new InventoryWrapper(p.getInventory());
			EconomyWrapper econ = new EconomyWrapper(p);
			
			ItemType gunpowder = new ItemType(Material.SULPHUR);
			gunpowder.setName("gunpowder");
			
			ItemType tnt = new ItemType(Material.TNT);
			tnt.setName("TNT");
			
			List<Ingredient> tntIngredients = new ArrayList<Ingredient>();
			tntIngredients.add(new ItemIngredient(gunpowder, 5, inv));
			//tntIngredients.add(new ItemIngredient(new ItemType(Material.SAND), 4, inv));
			tntIngredients.add(new MoneyIngredient(1.5, econ));
			this.convertStacks(p, tntIngredients, tnt);
			
			return true;
		}
		return false;
	}
	
	private void convertStacks(Player p, List<Ingredient> ingredients, ItemType product) {
		
		InventoryWrapper inv = new InventoryWrapper(p.getInventory());
		
		int productQty = -1;
		
		for (Ingredient ingredient : ingredients) {
			
			int productAvailable = ingredient.getProductAmount();
			
			if (productQty < 0) {
				productQty = productAvailable;
			} else {
				productQty = Math.min(productQty, productAvailable);
			}
			
			if (productAvailable == 0) {
				p.sendMessage(ChatColor.RED.toString()+"Not enough "+ingredient.getName());
			}
		}
		
		if (productQty == 0) {
			return;
		}
		
		p.sendMessage(ChatColor.GREEN.toString()+"Converting ingredients to "+productQty+" "+product.getName());
		
		for (Ingredient ingredient : ingredients) {
			int leftoverQty = ingredient.processConversion(productQty);
			
			if (leftoverQty > 0) {
				p.sendMessage(ChatColor.GREEN.toString()+"Returned "+leftoverQty+" "+ingredient.getName());
			}
		}
		
		inv.addItem(product, productQty);
	}
}
