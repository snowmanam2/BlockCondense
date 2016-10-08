package com.gmail.snowmanam2.blockcondense;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCondense extends JavaPlugin {
	private RecipeManager recipeManager;
	
	@Override
	public void onEnable() {
		
		recipeManager = new RecipeManager(this);
		Messages.loadMessages(this);
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
				String message = StringUtils.join(recipeManager.getRecipeList().toArray(), ", ");
				player.sendMessage(Messages.get("recipeList", message));
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (player.hasPermission("blockcondense.reload")) {
					recipeManager.buildRecipeList();
					player.sendMessage(Messages.get("reloadedRecipes"));
				} else {
					player.sendMessage(Messages.get("noPermissionReload"));
				}
				return true;
			} else {
				String recipeName = args[0];
				Recipe recipe = recipeManager.getRecipe(recipeName);
				
				if (recipe != null) {
					if (player.hasPermission("blockcondense.recipe."+recipeName)) {
						ConversionContext context = new ConversionContext(player);
						recipe.doConversion(context);
					} else {
						player.sendMessage(Messages.get("noPermission"));
					}
				} else {
					player.sendMessage(Messages.get("recipeNotFound"));
				}
			}
			
			return true;
		} 
		
		return false;
	}
	
}
