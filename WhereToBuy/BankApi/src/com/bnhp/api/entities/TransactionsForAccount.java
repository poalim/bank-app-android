package com.bnhp.api.entities;

import com.bnhp.api.ServiceResponse;


public class TransactionsForAccount 
{
	private int accountNumber;
	private long transactionId;
	private String transactionDate;
	private String transactionDescription;
	private double transactionAmount;
	private String transactionCurrency;
	private String creditDebitCode;
	private String transactionGroupDescription;
	private String transactionTime;
	
	public String getTransactionTime()
	{
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime)
	{
		this.transactionTime = transactionTime;
	}
	public int getAccountNumber()
	{
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	public long getTransactionId()
	{
		return transactionId;
	}
	public void setTransactionId(long transactionId)
	{
		this.transactionId = transactionId;
	}
	public String getTransactionDate()
	{
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate)
	{
		this.transactionDate = transactionDate;
	}
	public String getTransactionDescription()
	{
		return transactionDescription;
	}
	public void setTransactionDescription(String transactionDescription)
	{
		this.transactionDescription = transactionDescription;
	}
	public double getTransactionAmount()
	{
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount)
	{
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrency()
	{
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency)
	{
		this.transactionCurrency = transactionCurrency;
	}
	public String getCreditDebitCode()
	{
		return creditDebitCode;
	}
	public void setCreditDebitCode(String creditDebitCode)
	{
		this.creditDebitCode = creditDebitCode;
	}
	public String getTransactionGroupDescription()
	{
		return transactionGroupDescription;
	}
	public void setTransactionGroupDescription(String transactionGroupDescription)
	{
		this.transactionGroupDescription = transactionGroupDescription;
	}
	

}
