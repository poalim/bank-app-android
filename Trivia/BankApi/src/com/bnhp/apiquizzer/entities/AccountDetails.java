package com.bnhp.apiquizzer.entities;

import com.bnhp.apiquizzer.ServiceResponse;


public class AccountDetails 
{
	private int accountNumber;
	private String branchNumber;
	private String branchCity;
	private String branchName;
	private String accountOwnerAddress;
	private String accountOpenDate;
	private String accountTypeDescription;
	private String customerName;
	private int customerAge;
	private String customerGender;
	private String languagePreference;
	
	public int getAccountNumber() 
	{
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber) 
	{
		this.accountNumber = accountNumber;
	}
	
	public String getBranchNumber() 
	{
		return branchNumber;
	}
	
	public void setBranchNumber(String branchNumber) 
	{
		this.branchNumber = branchNumber;
	}
	
	public String getBranchCity() 
	{
		return branchCity;
	}
	
	public void setBranchCity(String branchCity) 
	{
		this.branchCity = branchCity;
	}
	
	public String getBranchName() 
	{
		return branchName;
	}
	
	public void setBranchName(String branchName) 
	{
		this.branchName = branchName;
	}
	
	public String getAccountOwnerAddress() 
	{
		return accountOwnerAddress;
	}

	public void setAccountOwnerAddress(String accountOwnerAddress) 
	{
		this.accountOwnerAddress = accountOwnerAddress;
	}

	public String getAccountOpenDate() 
	{
		return accountOpenDate;
	}
	
	public void setAccountOpenDate(String accountOpenDate) 
	{
		this.accountOpenDate = accountOpenDate;
	}
	
	public String getAccountTypeDescription() 
	{
		return accountTypeDescription;
	}
	
	public void setAccountTypeDescription(String accountTypeDescription) 
	{
		this.accountTypeDescription = accountTypeDescription;
	}
	
	public String getCustomerName() 
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName) 
	{
		this.customerName = customerName;
	}
	
	public int getCustomerAge() 
	{
		return customerAge;
	}
	
	public void setCustomerAge(int customerAge) 
	{
		this.customerAge = customerAge;
	}
	
	public String getCustomerGender() 
	{
		return customerGender;
	}
	
	public void setCustomerGender(String customerGender) 
	{
		this.customerGender = customerGender;
	}
	
	public String getLanguagePreference() 
	{
		return languagePreference;
	}
	
	public void setLanguagePreference(String languagePreference) 
	{
		this.languagePreference = languagePreference;
	}
}
