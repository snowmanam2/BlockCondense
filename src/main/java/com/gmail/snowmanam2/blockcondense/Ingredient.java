package com.gmail.snowmanam2.blockcondense;


public interface Ingredient {
	public int processConversion(int productQty);
	public int getAvailableAmount();
	public int getLeftoverAmount(int productQuantity);
	public int getProductAmount();
	public String getName();
}
