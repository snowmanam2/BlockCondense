package com.gmail.snowmanam2.blockcondense;

import java.util.Set;

import org.bukkit.ChatColor;

public class Recipe {
	private Set<Ingredient> ingredients;
	private ItemType product;
	
	public Recipe (Set<Ingredient> ingredients, ItemType product) {
		this.ingredients = ingredients;
		this.product = product;
	}
	
	public void doConversion (ConversionContext context) {
		
		int productQty = Integer.MAX_VALUE;
		
		for (Ingredient ingredient : ingredients) {
			
			int productAvailable = ingredient.loadMaximumProductAmount(context);
			
			productQty = Math.min(productQty, productAvailable);

			if (productAvailable <= 0) {
				context.sendMessage(ChatColor.RED.toString()+"Not enough "+ingredient.getName());
			}
		}
		
		if (productQty <= 0) {
			return;
		}
		
		context.sendMessage(ChatColor.GREEN.toString()+"Converting ingredients to "+productQty+" "+product.getName());
		
		for (Ingredient ingredient : ingredients) {
			int leftoverQty = ingredient.processConversion(context, productQty);
			
			if (leftoverQty > 0) {
				context.sendMessage(ChatColor.GREEN.toString()+"Returned "+leftoverQty+" "+ingredient.getName());
			}
		}
		
		context.getInventory().addItem(product, productQty);
	}
}
