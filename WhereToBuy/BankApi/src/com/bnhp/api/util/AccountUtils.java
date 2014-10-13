package com.bnhp.api.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bnhp.api.ApiJsonArrayRequest;
import com.bnhp.api.UserSession;
import com.bnhp.api.activities.CategoriesActivity;
import com.bnhp.api.activities.MainActivity;

public class AccountUtils
{
	private Context context;
	public AccountUtils(String accountNumber, Context context)
	{
		this.context = context;
		getAccountDetails(accountNumber);
	}

	private void getAccountDetails(final String accountNumber)
	{
		String url = "https://api.bankapp.co.il/account/" + accountNumber + "/transaction/by-date?fromDate=20140601&toDate=20140630";
		RequestQueue queue = Volley.newRequestQueue(context);

		ApiJsonArrayRequest jsObjRequest = new ApiJsonArrayRequest(url,
				new Response.Listener<JSONArray>()
				{
			@Override
			public void onResponse(JSONArray response)
			{
				//ObjectMapper mapper = new ObjectMapper();
				//transactions = new ArrayList<TransactionsForAccount>();
				long amount = 0;
				JSONObject object;

				if(response.length() == 0)
				{
					((MainActivity)context).setTextViewErrorMessage("אתה בטוח שמספר החשבון שלך זה " + accountNumber + "?");
					((MainActivity)context).setMainTextViewMessage("זה עסק רציני, תשתדלו להמנע מטעויות מיותרות");
				}
				else
				{
					for(int i = 0; i < response.length(); i++)
					{
						try
						{
							object = response.getJSONObject(i);
							if(object.getInt("creditDebitCode") == 1)
							{
								amount += object.getLong("transactionAmount");
							}

						}
						catch(JSONException e)
						{
						}
					}

					UserSession.getInstance().setAccountAmount(amount);
					UserSession.getInstance().setAccountNumber(accountNumber);
					Intent intent = new Intent(context,CategoriesActivity.class);
					context.startActivity(intent);
				}
			}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error)
					{
						((MainActivity)context).setTextViewErrorMessage(error.getMessage());
						((MainActivity)context).setMainTextViewMessage("אני לא באמת חושב שהמידע יעזור לך אבל מילא...");
					}
				});


		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
		headers.put("secretKey", "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");
		jsObjRequest.setHeaders(headers);
		queue.add(jsObjRequest);
	}
}
