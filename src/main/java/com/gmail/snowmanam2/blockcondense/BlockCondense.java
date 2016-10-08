package com.gmail.snowmanam2.blockcondense;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	private FileConfiguration config = getConfig();
	private RecipeManager recipeManager;
	
	@Override
	public void onEnable() {
		config = getConfig();
		config.addDefault("tntCost", 1.5);
		config.options().copyDefaults(true);
		saveConfig();
		
		recipeManager = new RecipeManager(this); 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command must be run by a player");
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("blockcondense")) {
			Player player = (Player) sender;
			
			if (args.length == 0 || args.length > 2) {
				return false;
			} else if (args[0].equalsIgnoreCase("help")){
				return false;
			} else if (args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.AQUA+"Available recipes:");
				String message = StringUtils.join(recipeManager.getRecipeList().toArray(), ", ");
				player.sendMessage(ChatColor.AQUA+message);
				return true;
			} else {
				String recipeName = args[0];
				Recipe recipe = recipeManager.getRecipe(recipeName);
				
				if (recipe != null) {
					if (player.hasPermission("blockcondense.recipe."+recipeName)) {
						ConversionContext context = new ConversionContext(player);
						recipe.doConversion(context);
					} else {
						player.sendMessage(ChatColor.RED+"You don't have permission for that recipe.");
					}
				} else {
					player.sendMessage(ChatColor.RED+"That recipe does not exist.");
				}
			}
			
			return true;
		} 
		
		return false;
	}
	
}
