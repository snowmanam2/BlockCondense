package com.gmail.snowmanam2.blockcondense;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyIngredient implements Ingredient {
	private double requiredAmount;
	private BigDecimal availableAmount = new BigDecimal(0);
	
	public MoneyIngredient (double requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
	
	public String getName() {
		return "money";
	}

	public int getLeftoverAmount(int productQuantity) {
		return 0;
	}

	public int loadMaximumProductAmount(ConversionContext context) {
		availableAmount = context.getEconomy().getMoney();
		
		if (requiredAmount == 0) {
			return Integer.MAX_VALUE;
		}
		
		return availableAmount.divide(new BigDecimal(requiredAmount), RoundingMode.HALF_UP).intValue();
	}
	
	public int processConversion(ConversionContext context, int productQty) {
		BigDecimal amount = new BigDecimal (requiredAmount * productQty);
		context.getEconomy().subtract(amount);
		
		return 0;
	}

}
