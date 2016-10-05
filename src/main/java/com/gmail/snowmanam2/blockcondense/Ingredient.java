package com.gmail.snowmanam2.blockcondense;


public interface Ingredient {
	public String getName();
	public int getLeftoverAmount(int productQuantity);
	public int loadMaximumProductAmount(ConversionContext context);
	public int processConversion(ConversionContext context, int productQty);
	
	
}
