package com.gmail.snowmanam2.blockcondense;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RecipeManager {
	private BlockCondense plugin;
	private Map<String, Recipe> recipeList;
	private Logger logger;
	
	public RecipeManager(BlockCondense plugin) {
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		recipeList = new HashMap<String, Recipe>();
		
		buildRecipeList();
	}
	
	private void buildRecipeList() {
		File recipeFile = new File(plugin.getDataFolder(), "recipes.yml");
		FileConfiguration recipeConfig = YamlConfiguration.loadConfiguration(recipeFile);
		
		for (String key: recipeConfig.getKeys(false)) {
			logger.log(Level.INFO, "Building recipe "+key);
			
			ConfigurationSection section = recipeConfig.getConfigurationSection(key);
			
			Set<String> recipeKeys = section.getKeys(false);
			
			ItemType product = null;
			Set<Ingredient> ingredients = new HashSet<Ingredient>();
			
			if (recipeKeys.contains("product")) {
				if (section.isConfigurationSection("product")) {
					product = buildProduct(section.getConfigurationSection("product"));
				} else {
					logger.log(Level.WARNING, "Recipe "+key+" did not have valid product information! Dropping...");
					continue;
				}
			} else {
				logger.log(Level.WARNING, "Recipe "+key+" did not have a product! Dropping...");
				continue;
			}
			
			if (recipeKeys.contains("ingredients")) {
				if (section.isList("ingredients")) {
					List<Map<?,?>> ingredientList = section.getMapList("ingredients");
					
					for (Map<?,?> ingredientMap : ingredientList) {
						Ingredient ingredient = buildIngredient(ingredientMap);
						
						if (ingredient != null) {
							ingredients.add(ingredient);
						}
					}
				} else {
					logger.log(Level.WARNING, "Recipe "+key+" did not have a valid ingredient list! Dropping...");
				}
			} else {
				logger.log(Level.WARNING, "Recipe "+key+" did not have any ingredients! Dropping...");
				continue;
			}
			
			Recipe recipe = new Recipe(ingredients, product);
			
			recipeList.put(key, recipe);
		}
	}
	
	private ItemType buildProduct(ConfigurationSection productConfig) {
		Set<String> keys = productConfig.getKeys(false);
		
		if (keys.contains("item")) {
			ItemType productType = ItemType.buildFromString(plugin, productConfig.getString("item"));
			if (keys.contains("name")) {
				productType.setName(productConfig.getString("name"));
			}
			
			return productType;
		} else {
			logger.log(Level.WARNING, "Product did not contain an item specification.");
			return null;
		}
	}
	
	private Ingredient buildIngredient(Map<?,?> ingredientMap) {
		Ingredient ingredient = null;
		String amount = null;
		String name = null;
		
		if (ingredientMap.containsKey("amount")) {
			amount = String.valueOf(ingredientMap.get("amount"));
		} else {
			logger.log(Level.WARNING, "Ingredient did not have a specified amount! Dropping...");
			return null;
		}
		
		if (ingredientMap.containsKey("item")) {
			String ingredientItem = String.valueOf(ingredientMap.get("item"));
			
			if (ingredientItem.equalsIgnoreCase("money")) {
				double cost;
				
				try {
					cost = Double.valueOf(amount);
				} catch (NumberFormatException e) {
					logger.log(Level.WARNING, "Money ingredient cost was not a number! Setting to zero...");
					cost = 0;
				}
				
				ingredient = new MoneyIngredient(cost);
			} else {
				ItemType ingredientType = ItemType.buildFromString(plugin, ingredientItem);
				
				if (ingredientType != null) {
					if (ingredientMap.containsKey("name")) {
						name = String.valueOf(ingredientMap.get("name"));
						ingredientType.setName(name);
					}
					
					int quantity;
					try {
						quantity = Integer.valueOf(amount);
					} catch (NumberFormatException e) {
						logger.log(Level.WARNING, "Item amount was not an integer! Setting to zero...");
						quantity = 0;
					}
					
					ingredient = new ItemIngredient(ingredientType, quantity);
				}
			}
		} else {
			logger.log(Level.WARNING, "Ingredient did not contain an item field! Dropping...");
			return null;
		}
		
		return ingredient;
	}
	
	public Collection<String> getRecipeList() {
		return recipeList.keySet();
	}
	
	public Recipe getRecipe(String name) {
		return recipeList.get(name);
	}
}
