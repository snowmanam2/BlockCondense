package com.gmail.snowmanam2.blockcondense;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	private FileConfiguration config = getConfig();
	
	@Override
	public void onEnable() {
		config = getConfig();
		config.addDefault("tntCost", 1.5);
		config.options().copyDefaults(true);
		saveConfig();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("condense")) {
			Player p = (Player) sender;
			ConversionContext context = new ConversionContext (p);
			
			ItemType lapis = new ItemType(Material.INK_SACK, (short) 4);
			lapis.setName("lapis"); 
			
			List<Ingredient> lapisIngredients = new ArrayList<Ingredient>();
			lapisIngredients.add(new ItemIngredient(lapis, 9));
			Recipe lapisRecipe = new Recipe(lapisIngredients, new ItemType(Material.LAPIS_BLOCK));
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new ItemIngredient(new ItemType(Material.GOLD_INGOT), 9));
			Recipe goldRecipe = new Recipe(goldIngredients, new ItemType(Material.GOLD_BLOCK));
			
			List<Ingredient> ironIngredients = new ArrayList<Ingredient>();
			ironIngredients.add(new ItemIngredient(new ItemType(Material.IRON_INGOT), 9));
			Recipe ironRecipe = new Recipe(ironIngredients, new ItemType(Material.IRON_BLOCK));
			
			List<Ingredient> emeraldIngredients = new ArrayList<Ingredient>();
			emeraldIngredients.add(new ItemIngredient(new ItemType(Material.EMERALD), 9));
			Recipe emeraldRecipe = new Recipe(emeraldIngredients, new ItemType(Material.EMERALD_BLOCK));
			
			List<Ingredient> diamondIngredients = new ArrayList<Ingredient>();
			diamondIngredients.add(new ItemIngredient(new ItemType(Material.DIAMOND), 9));
			Recipe diamondRecipe = new Recipe(diamondIngredients, new ItemType(Material.DIAMOND_BLOCK));
			
			lapisRecipe.doConversion(context);
			goldRecipe.doConversion(context);
			ironRecipe.doConversion(context);
			emeraldRecipe.doConversion(context);
			diamondRecipe.doConversion(context);
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gold")) {
			Player p = (Player) sender;
			ConversionContext context = new ConversionContext (p);
			
			List<Ingredient> goldIngredients = new ArrayList<Ingredient>();
			goldIngredients.add(new ItemIngredient(new ItemType(Material.GOLD_NUGGET), 9));
			Recipe goldRecipe = new Recipe(goldIngredients, new ItemType(Material.GOLD_INGOT));
			
			goldRecipe.doConversion(context);
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gunpowder")) {
			Player p = (Player) sender;
			ConversionContext context = new ConversionContext (p);
			
			ItemType gunpowder = new ItemType(Material.SULPHUR);
			gunpowder.setName("gunpowder");
			
			ItemType tnt = new ItemType(Material.TNT);
			tnt.setName("TNT");
			
			List<Ingredient> tntIngredients = new ArrayList<Ingredient>();
			tntIngredients.add(new ItemIngredient(gunpowder, 5));
			tntIngredients.add(new MoneyIngredient(config.getDouble("tntCost")));
			Recipe tntRecipe = new Recipe(tntIngredients, tnt);
			
			tntRecipe.doConversion(context);
			
			return true;
		}
		return false;
	}
	
}
