package com.bnhp.apiquizzer;

import com.bnhp.apiquizzer.entities.AccountDetails;

public class UserSession 
{
	private static UserSession instance;
	
	private AccountDetails selectedAccount;
	
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

	public AccountDetails getSelectedAccount() 
	{
		return selectedAccount;
	}

	public void setSelectedAccount(AccountDetails selectedAccount)
	{
		this.selectedAccount = selectedAccount;
	}
}
