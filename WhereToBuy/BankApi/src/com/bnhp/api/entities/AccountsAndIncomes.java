package com.bnhp.api.entities;

public class AccountsAndIncomes
{
	private long income;
	private String accountNumber;
	
	public AccountsAndIncomes(long income, String accountNumber)
	{
		this.income = income;
		this.accountNumber = accountNumber;
	}

	public long getIncome()
	{
		return income;
	}

	public void setIncome(long income)
	{
		this.income = income;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
}
