package com.bnhp.apiquizzer.entities;

public class AccountIncome
{
	private int accountNumber;
	private String date;
	private double netIncome;
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public double getNetIncome()
	{
		return netIncome;
	}
	
	public void setNetIncome(double netIncome)
	{
		this.netIncome = netIncome;
	}
}