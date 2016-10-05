package com.gmail.snowmanam2.blockcondense;

import java.math.BigDecimal;

public class MoneyIngredient implements Ingredient {
	private double requiredAmount;
	private BigDecimal availableAmount = new BigDecimal(0);
	private EconomyWrapper economy;
	
	public MoneyIngredient (double requiredAmount, EconomyWrapper economy) {
		this.requiredAmount = requiredAmount;
		this.economy = economy;
		this.availableAmount = economy.getMoney();
	}
	
	public int processConversion(int productQty) {
		BigDecimal amount = new BigDecimal (requiredAmount * productQty);
		economy.subtract(amount);
		
		return 0;
	}

	public int getAvailableAmount() {
		return availableAmount.intValue();
	}

	public int getLeftoverAmount(int productQuantity) {
		return 0;
	}

	public int getProductAmount() {
		return availableAmount.divide(new BigDecimal(requiredAmount)).intValue();
	}

	public String getName() {
		return "money";
	}

}
