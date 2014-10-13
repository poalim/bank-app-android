package com.bnhp.api.activities;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bnhp.api.R;
import com.bnhp.api.UserSession;
import com.bnhp.api.entities.AccountsAndIncomes;
import com.bnhp.api.util.CategoryAdapter;

/* 
 * In this class we calculate how much the user makes.
 * then the calss goes through the list of accounts that makes the same money as the user and calls 
 * the transaction API to see where exactly did they spend money
 * This activity will present the user with a list of categories to chose from so he can see a list
 * of transactions that were made by those accounts we searched for earlier.
 * */
public class CategoriesActivity extends Activity
{
	// user income max
	private int incomeBar;
	
	// user income min
	private int incomeLow;
	
	// How much the user makes
	private long accountAmount = UserSession.getInstance().getAccountAmount();
	
	// All the accounts that makes around the same amount of money
	private ListView accountList;
	
	// All the categories
	private List<String> categoriesList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_categories);
		accountList = (ListView)findViewById(R.id.accountList);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));
		
		
		setCategoriesList();
		setIncomeBar();
		
		 LayoutAnimationController controller 
		   = AnimationUtils.loadLayoutAnimation(
		     this, R.anim.list_layout_controller);
		 accountList.setLayoutAnimation(controller);
	}
	
	/*
	 * This procedure set all the transactions categories in the categories list*/
	private void setCategoriesList()
	{
		categoriesList.add("רכב ותחבורה");
		categoriesList.add("ביטוח");
		categoriesList.add("מזון ומשקאות");
		categoriesList.add("בית וגן");
		categoriesList.add("יופי ואביזרי אופנה");
		categoriesList.add("בילוי ומסעדות");
		categoriesList.add("בריאות");
		categoriesList.add("ביגוד והנעלה");
		categoriesList.add("תרבות ופנאי");
		categoriesList.add("מיסים ותשלומים");
		categoriesList.add("נופש בארץ ובחו'ל");
		categoriesList.add("הלוואות ומשכנתא");
		categoriesList.add("חינוך");
		categoriesList.add("חינוך ומשפחה");
		categoriesList.add("מוצרי אלקטרוניקה ותקשורת");
		categoriesList.add("שונות");
	}

	/*This procedure is setting the user income bar*/
	private void setIncomeBar()
	{
		if(accountAmount < 4000)
		{
			incomeLow = 0;
			incomeBar = 4000;
		}
		else if(accountAmount < 8000)
		{
			incomeLow = 4000;
			incomeBar = 8000;
		}
		else if(accountAmount < 12000)
		{
			incomeLow = 8000;
			incomeBar = 12000;
		}
		else if(accountAmount < 16000)
		{
			incomeLow = 12000;
			incomeBar = 16000;
		}
		else if(accountAmount < 20000)
		{
			incomeLow = 16000;
			incomeBar = 20000;
		}
		else if(accountAmount < 30000)
		{
			incomeLow = 20000;
			incomeBar = 30000;
		}
		else
		{
			incomeLow = 30000;
			incomeBar = 0;
		}

		setAccounts();
	}
	
	/*
	 * This procedure takes the */
	private void setAccounts()
	{
		List<String> accountsToDisplay = new ArrayList<String>();

		// Go over the list of accounts
		for(AccountsAndIncomes currAccount : UserSession.getInstance().getAccountsList())
		{
			// If not the user account
			if(currAccount.getAccountNumber() != UserSession.getInstance().getAccountNumber())
			{
				// Check to see if the account makes the same money as the user
				if((currAccount.getIncome() < incomeBar && currAccount.getIncome() > incomeLow) ||
						(incomeBar == 0 && currAccount.getIncome() > 30000))
				{
					accountsToDisplay.add(currAccount.getAccountNumber());
				}
			}
		}
		
		// save the list to user session
		UserSession.getInstance().setAccountsToDisplay(accountsToDisplay);
		
		// Set the list adapter
		final CategoryAdapter adapter = new CategoryAdapter(categoriesList,this);
		    accountList.setAdapter(adapter);
		    
		    accountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		        @Override
		        public void onItemClick(AdapterView<?> parent, final View view,
		            int position, long id)
		        {
		        	// When item clicked call the transaction activity with the chosen category
		        	final String category = categoriesList.get(position);
		        	Intent intent = new Intent(CategoriesActivity.this, TransactionsActivity.class);
		        	intent.putExtra("CATEGORY", category);
		        	startActivity(intent);
		        }
		      });
	}
	
}


