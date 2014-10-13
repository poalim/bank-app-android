package com.bnhp.api.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bnhp.api.ApiJsonArrayRequest;
import com.bnhp.api.R;
import com.bnhp.api.UserSession;
import com.bnhp.api.entities.AccountDetails;
import com.bnhp.api.entities.AccountsAndIncomes;
import com.bnhp.api.util.AccountUtils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static String[] MESSAGES = {"אז מה חדש וזה?",
		"את/ה בא/ה לפה הרבה?",
		"תבחר/י מספר מ1 עד 10?",
		"המספר שבחרת הוא 7",
		"הידעת שהברווז האינדונזי יכול לעשות עמידת מקור?",
		"אני עדיין מבואס מהסוף של דקסטר, אבל ממש",
		"רגעעעעע אל תעזבו! אני עוד מעט מסיים",
		"אני יודע שצדקתי",
		"מי הזיז את הגבינה שלי???",
		"Ob-La-Di Ob-La-Da LIFE GOES ONNNNN",
		"יוווו ציפור!!! תסתכל/י!!!",
	"זהו, מסיימים"};

	private TextView mainTextViewMessage;
	private ProgressBar myProgressBar;
	private EditText editTextEnterAccount;
	private Button letMeIn;
	private TextView textViewErrorMessage;


	private int counter = 0;
	private int msgCounter = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainTextViewMessage = (TextView)findViewById(R.id.mainTextViewMessage);
		myProgressBar = (ProgressBar)findViewById(R.id.myProgressBar);
		editTextEnterAccount = (EditText)findViewById(R.id.editTextEnterAccount);
		textViewErrorMessage = (TextView)findViewById(R.id.textViewErrorMessage);
		textViewErrorMessage.setTextColor(Color.RED);
		letMeIn = (Button)findViewById(R.id.letMeIn);

		letMeIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mainTextViewMessage.setText("יקח לי דקה לבדוק שהחשבון לא מעוקל ונמשיך...");
				new AccountUtils(editTextEnterAccount.getText().toString(), MainActivity.this);
			}
		});

		mainTextViewMessage.postDelayed(new Runnable()
		{

			@Override
			public void run()
			{

				if(msgCounter >= 12)
				{
					mainTextViewMessage.setText("טוב טוב אני אשתוק");
				}
				else
				{
					mainTextViewMessage.setText(MESSAGES[msgCounter]);
					msgCounter++;
				}

			}
		}, 1000);

		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));
		getSampleAccounts();
	}

	private void getSampleAccounts()
	{
		String url = "https://api.bankapp.co.il/account/sample";
		RequestQueue queue = Volley.newRequestQueue(this);
		//showProgress(true);

		ApiJsonArrayRequest jsObjRequest = new ApiJsonArrayRequest(url,
				new Response.Listener<JSONArray>()
				{
			@Override
			public void onResponse(JSONArray response)
			{
				JSONObject object;
				myProgressBar.setMax(response.length());

				for(int i = 0; i < response.length(); i++)
				{
					try
					{
						object = response.getJSONObject(i);
						getAccountIncome(object.getString("accountNumber"));
						//accounts.add(mapper.readValue(object.toString(), AccountDetails.class));
					}
					catch(JSONException e)
					{
						e.printStackTrace();
					}
				}
			}
				}, 
				new Response.ErrorListener() {
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
	}

	private void getAccountIncome(final String accountNumber)
	{
		String url = "https://api.bankapp.co.il/account/" + accountNumber + "/transaction/by-date?fromDate=20140601&toDate=20140630";
		RequestQueue queue = Volley.newRequestQueue(this);

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


				if(amount > 0)
				{
					AccountsAndIncomes currAccount = new AccountsAndIncomes(amount, accountNumber);
					UserSession.getInstance().getAccountsList().add(currAccount);
				}

				updateAccountsProgress();
			}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error)
					{
						updateAccountsProgress();
					}
				});

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
		headers.put("secretKey", "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");
		jsObjRequest.setHeaders(headers);
		queue.add(jsObjRequest);
	}

	private void updateAccountsProgress()
	{
		counter++;
		myProgressBar.setProgress(counter);

		if(counter == myProgressBar.getMax())
		{
			editTextEnterAccount.setVisibility(View.VISIBLE);
			letMeIn.setVisibility(View.VISIBLE);
			textViewErrorMessage.setVisibility(View.VISIBLE);
			myProgressBar.setVisibility(View.GONE);
			mainTextViewMessage.setText("אוקיי סיימתי, אז מה מספר החשבון שלך?");
		}
	}

	public void setMainTextViewMessage(String mainTextViewMessage)
	{
		this.mainTextViewMessage.setText(mainTextViewMessage);
	}

	public void setTextViewErrorMessage(String textViewErrorMessage)
	{
		this.textViewErrorMessage.setText(textViewErrorMessage);
	}
}


