package com.bnhp.api;

import java.util.ArrayList;
import java.util.List;
import com.bnhp.api.entities.AccountDetails;
import com.bnhp.api.entities.AccountsAndIncomes;
 
/*
 * User session is a singleton class that keeps vital information saved for any need*/
public class UserSession 
{
	private static UserSession instance;
	
	// The amount user makes every month
	private long accountAmount;
	
	// Account number
	private String accountNumber;
	
	// all samples account
	private List<AccountsAndIncomes> accountsList = new ArrayList<AccountsAndIncomes>();
	
	// all accounts that makes the same amount of money as the user
	private List<String> accountsToDisplay = new ArrayList<String>();
	
	public static UserSession getInstance()
	{
		if(instance == null)
		{
			synchronized (UserSession.class) 
			{
				if(instance == null)
				{
					instance = new UserSession();
				}
			}
		}
		
		return instance;
	}

	public long getAccountAmount()
	{
		return accountAmount;
	}

	public void setAccountAmount(long accountAmount)
	{
		this.accountAmount = accountAmount;
	}

	public List<AccountsAndIncomes> getAccountsList()
	{
		return accountsList;
	}

	public void setAccountsList(List<AccountsAndIncomes> accountsList)
	{
		this.accountsList = accountsList;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public List<String> getAccountsToDisplay()
	{
		return accountsToDisplay;
	}

	public void setAccountsToDisplay(List<String> accountsToDisplay)
	{
		this.accountsToDisplay = accountsToDisplay;
	}
}
