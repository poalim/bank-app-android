package com.bnhp.api.activities;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bnhp.api.ApiJsonArrayRequest;
import com.bnhp.api.R;
import com.bnhp.api.UserSession;
import com.bnhp.api.entities.CreditCardTransaction;
import com.bnhp.api.util.TransactionAdapter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This activity goes over the accounts and calls the transaction API and collect all the transaction from the 
 * chose category from previous activity*/
public class TransactionsActivity extends Activity
{
	private List<CreditCardTransaction> transactions = new ArrayList<CreditCardTransaction>();
	private RequestQueue queue;
	private String category = "";
	private ListView transactionsList;
	private int accountsCounter = 0;
	private ProgressBar progressBar;
	private TextView transactionExplainText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_transaction);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));

		transactionsList = (ListView)findViewById(R.id.transactionsList);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		transactionExplainText = (TextView)findViewById(R.id.transactionExplainText);
		
		LayoutAnimationController controller 
		   = AnimationUtils.loadLayoutAnimation(
		     this, R.anim.list_layout_controller);
		transactionsList.setLayoutAnimation(controller);

		// Set volley for api call
		queue = Volley.newRequestQueue(this);

		// Get bundle to retrieve the selected category from last activity
		Bundle b = new Bundle();
		b = getIntent().getExtras();

		if(b != null)
		{
			category = b.getString("CATEGORY");
		}

		// If category set
		if(!category.isEmpty())
		{
			getActionBar().setTitle("הוצאות של " + category);

			// For each account in the list call the transaction API
			for(int i = 0; i < UserSession.getInstance().getAccountsToDisplay().size(); i++)
			{
				getCreditCardTransactions(TransactionsActivity.this, UserSession.getInstance().getAccountsToDisplay().get(i), category);
			}

		}
	}

	private void getCreditCardTransactions(final Context ctx, final String accountNumber, final String category)
	{

		// Set the api url
		String url = "https://api.bankapp.co.il/account/" + accountNumber
				+ "/credit-card-transaction/by-date";

		// Call JsonArray request
		ApiJsonArrayRequest jsObjRequest = new ApiJsonArrayRequest(url,
				new Response.Listener<JSONArray>()
				{
			@Override
			public void onResponse(JSONArray response)
			{
				// On response map the array into the CreditCardTransaction entity
				ObjectMapper mapper = new ObjectMapper();

				JSONObject object;

				for(int i = 0; i < response.length(); i++)
				{
					try
					{
						// For each transaction check if its from the selected category and add it to the list
						object = response.getJSONObject(i);
						CreditCardTransaction currCredit = mapper.readValue(object.toString(), CreditCardTransaction.class);
						if(currCredit.getPaymentCategory().equals(category))
						{
							transactions.add(mapper.readValue(object.toString(), CreditCardTransaction.class));
						}
					}
					catch(JSONException e)
					{
					}
					catch(JsonParseException e)
					{
					}
					catch(JsonMappingException e)
					{
					}
					catch(IOException e)
					{
					}
				}

				//This counter helps us know when we made the last server call from the list
				//When done we can set the adapter
				accountsCounter++;
				if(accountsCounter >= UserSession.getInstance().getAccountsToDisplay().size())
				{
					progressBar.setVisibility(View.GONE);
					
					if(transactions.size() > 0)
					{
						// Set transaction Adapter
						TransactionAdapter adapter = new TransactionAdapter(transactions, TransactionsActivity.this);
						transactionsList.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						

						// on item click call google with business name string as a query
						transactionsList.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent, final View view,
									int position, long id)
							{
								String query = "";
								try
								{
									query = URLEncoder.encode(transactions.get(position).getBusinessName(), "utf-8");
								}
								catch(UnsupportedEncodingException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								if(!query.isEmpty())
								{
									String url = "http://www.google.com/search?q=" + query;
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(url));
									startActivity(intent);
								}
							}
						});
					}
					else
					{
						transactionExplainText.setText("לא נמצאו הוצאות לקטגוריה הנבחרת");
						transactionExplainText.setTextColor(Color.RED);
					}
				}

			}
				}, 
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
					}
				});

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
		headers.put("secretKey", "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");
		jsObjRequest.setHeaders(headers);
		queue.add(jsObjRequest);
		progressBar.setVisibility(View.VISIBLE);
	}
}


