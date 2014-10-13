package com.bnhp.apiquizzer.entities;

public class CreditCardTransaction
{
	private int accountNumber;
	private long chargeId;
	private double purchaseAmount;
	private int numberOfPaymentsInPurchase;
	private String purchaseDate;
	private String purchaseTime;
	private String businessName;
	private String paymentCategory;
	private String businessLocation;
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	
	public long getChargeId()
	{
		return chargeId;
	}
	
	public void setChargeId(long chargeId)
	{
		this.chargeId = chargeId;
	}
	
	public double getPurchaseAmount()
	{
		return purchaseAmount;
	}
	
	public void setPurchaseAmount(double purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}
	
	public int getNumberOfPaymentsInPurchase()
	{
		return numberOfPaymentsInPurchase;
	}
	
	public void setNumberOfPaymentsInPurchase(int numberOfPaymentsInPurchase)
	{
		this.numberOfPaymentsInPurchase = numberOfPaymentsInPurchase;
	}
	
	public String getPurchaseDate()
	{
		return purchaseDate;
	}
	
	public void setPurchaseDate(String purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}
	
	public String getPurchaseTime()
	{
		return purchaseTime;
	}
	
	public void setPurchaseTime(String purchaseTime)
	{
		this.purchaseTime = purchaseTime;
	}
	
	public String getBusinessName()
	{
		return businessName;
	}
	
	public void setBusinessName(String businessName)
	{
		this.businessName = businessName;
	}
	
	public String getPaymentCategory()
	{
		return paymentCategory;
	}
	
	public void setPaymentCategory(String paymentCategory)
	{
		this.paymentCategory = paymentCategory;
	}
	
	public String getBusinessLocation()
	{
		return businessLocation;
	}
	
	public void setBusinessLocation(String businessLocation)
	{
		this.businessLocation = businessLocation;
	}
}