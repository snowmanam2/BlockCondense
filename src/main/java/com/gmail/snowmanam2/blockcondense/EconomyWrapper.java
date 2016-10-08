package com.gmail.snowmanam2.blockcondense;

import java.math.BigDecimal;

import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EconomyWrapper {
	Player player;
	
	public EconomyWrapper(Player player) {
		this.player = player; 
	}
	
	public BigDecimal getMoney () {
		BigDecimal result = new BigDecimal(0);
		try {
			result = Economy.getMoneyExact(player.getName());
		} catch (UserDoesNotExistException e) {
			result = new BigDecimal(0);
		}
		
		return result;
	}
	
	public void subtract(BigDecimal amount) {
		try {
			Economy.substract(player.getName(), amount);
			String amountString = Economy.format(amount);
			player.sendMessage(Messages.get("moneyWithdraw", amountString));
		} catch (UserDoesNotExistException e) {
			
		} catch (NoLoanPermittedException e) {
			
		} catch (ArithmeticException e) {
			
		}
	}
	
	public void add(BigDecimal amount) {
		try {
			Economy.add(player.getName(), amount);
			String amountString = Economy.format(amount);
			player.sendMessage(Messages.get("moneyDeposit", amountString));
		} catch (NoLoanPermittedException e) {
			
		} catch (ArithmeticException e) {
			
		} catch (UserDoesNotExistException e) {
			
		}
	}
	
}
