package com.bnhp.apiquizzer.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bnhp.apiquizzer.ApiJsonArrayRequest;
import com.bnhp.apiquizzer.ApiJsonObjectRequest;
import com.bnhp.apiquizzer.UserSession;
import com.bnhp.apiquizzer.entities.AccountIncome;
import com.bnhp.apiquizzer.entities.CreditCardTransaction;
import com.bnhp.apiquizzer.entities.Question;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuestionsHelper
{
	private static final int STRINGS_NUM = 3;
	private static final int ANSWERS_NUM = 4;
	
	private static QuestionsHelper instance;
	private OnHelperInitialized onInitialized;
	private List<CreditCardTransaction> transactions;
	private Random rand = new Random();
	private List<String> correctStrings = new ArrayList<String>();
	private List<String> wrongStrings = new ArrayList<String>();
	private AccountIncome income;
	private int randomMonth;
	
	public interface OnHelperInitialized 
	{
		public void onInitialized();
	}
	
	public interface OnQuestionLoaded 
	{
		public void onLoaded(Question question);
	}
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	private static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
	private static final SimpleDateFormat FORMATTER_DISPLAY = new SimpleDateFormat(DATE_FORMAT_DISPLAY);
//	private static final String date = FORMATTER.format(new Date());
	private RequestQueue queue;
	
	private QuestionsHelper()
	{
	}
	
	public static QuestionsHelper getInstance()
	{
		if(instance == null)
		{
			synchronized (UserSession.class) 
			{
				if(instance == null)
				{
					instance = new QuestionsHelper();
				}
			}
		}
		
		return instance;
	}

	private final String TAG = getClass().getSimpleName();

	private void logE(Exception e)
	{
		Log.e(TAG, e.getMessage() != null ? e.getMessage() : "", e);
	}

	public void initialize(Context ctx, OnHelperInitialized onInitialized)
	{
		this.onInitialized = onInitialized;
		initializedStrings();
		int accountNumber = UserSession.getInstance().getSelectedAccount().getAccountNumber();
		queue = Volley.newRequestQueue(ctx);
		getCreditCardTransactions(ctx, accountNumber);
	}
	
	private void initializedStrings()
	{
		wrongStrings.add("שוב פעם גנבו לך את האשראי?");
		wrongStrings.add("אם הידע שלך לא פלא שהיתרה שלך נראית ככה, כן כן, הסתכלתי.");
		wrongStrings.add("טעות טעות טעות. סתם, צדקת. סתם סתם, טעות.");
		
		correctStrings.add("א-תה תו-תח! אין אין עליך!");
		correctStrings.add("תראו מי עוקב!!!");
		correctStrings.add("הצצת בפירוט חשבון שלך נכון?");
	}

	private void getCreditCardTransactions(final Context ctx, final int accountNumber)
	{
		String url = "https://api.bankapp.co.il/account/" + accountNumber
				+ "/credit-card-transaction/by-date";

		ApiJsonArrayRequest jsObjRequest = new ApiJsonArrayRequest(url,
		new Response.Listener<JSONArray>()
		{
			@Override
			public void onResponse(JSONArray response)
			{
				ObjectMapper mapper = new ObjectMapper();

				transactions = new ArrayList<CreditCardTransaction>();

				JSONObject object;

				for(int i = 0; i < response.length(); i++)
				{
					try
					{
						object = response.getJSONObject(i);
						transactions.add(mapper.readValue(object.toString(), CreditCardTransaction.class));
					}
					catch(JSONException e)
					{
						logE(e);
					}
					catch(JsonParseException e)
					{
						logE(e);
					}
					catch(JsonMappingException e)
					{
						logE(e);
					}
					catch(IOException e)
					{
						logE(e);
					}
				}
				
				onInitialized.onInitialized();
			}
		}, 
		new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				logE(error);
				onInitialized.onInitialized();
			}
		});

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
		headers.put("secretKey", "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");
		jsObjRequest.setHeaders(headers);
		queue.add(jsObjRequest);
	}
	
	public void generateQuestion(Context ctx, OnQuestionLoaded onQuestionLoaded)
	{
		int accountNumber = UserSession.getInstance().getSelectedAccount().getAccountNumber();
		
		if(rand.nextBoolean())
		{
			getNetIncome(ctx, accountNumber, onQuestionLoaded);
		}
		else
		{
			generateTransactionQuestion(onQuestionLoaded);
		}
	}
	
	private void generateIncomeQuestion(OnQuestionLoaded onQuestionLoaded)
	{
		Question question = new Question();
		
		// Build question
		String date = "0" + randomMonth + "/2014";
		question.setQuestion("מה לדעתך הרווח הנקי שלך בתאריך " + date + "?");
		
		// Build answers
		List<String> answers = new ArrayList<String>();
		int correctAnswerIndex = rand.nextInt(ANSWERS_NUM);
		
		answers.add("" + (int)(income.getNetIncome() + rand.nextInt(1 + Math.abs((int) income.getNetIncome()))));
		answers.add("" + (int)(income.getNetIncome() - rand.nextInt(1 + Math.abs((int) income.getNetIncome()))));
		answers.add("אף תשובה לא נכונה");
		answers.add(correctAnswerIndex, (int)income.getNetIncome() + "");
		
		question.setAnswers(answers);
		question.setCorrectAnswer(correctAnswerIndex);
				
		onQuestionLoaded.onLoaded(question);
	}

	private void generateTransactionQuestion(OnQuestionLoaded onQuestionLoaded)
	{
		Question question = new Question();
		
		// Generate question 1
		int transactionIndex = rand.nextInt(transactions.size() - 1);
		CreditCardTransaction transaction = transactions.get(transactionIndex);
		int sum = 0;
		
		// Calculate sum for a random date
		for(CreditCardTransaction currTransaction : transactions)
		{
			if(currTransaction.getPurchaseDate().equals(transaction.getPurchaseDate()) &&
					currTransaction.getBusinessName().equals(transaction.getBusinessName()))
			{
				sum += currTransaction.getPurchaseAmount();
			}
		}
		
		// Build question
		String dateDiaplay = null;
		
		try
		{
			dateDiaplay = FORMATTER_DISPLAY.format(FORMATTER.parse(transaction.getPurchaseDate()));
		}
		catch(ParseException e)
		{
			logE(e);
		}
		
		question.setQuestion("כמה כסף הוצאת בתאריך " + dateDiaplay + " בחנות " + transaction.getBusinessName() + "?");
		
		// Build answers
		List<String> answers = new ArrayList<String>();
		int correctAnswerIndex = rand.nextInt(ANSWERS_NUM);
		
		answers.add("" + (sum + rand.nextInt(sum)));
		answers.add("" + (sum - rand.nextInt(sum)));
		answers.add("אף תשובה לא נכונה");
		answers.add(correctAnswerIndex, sum + "");
		
		question.setAnswers(answers);
		question.setCorrectAnswer(correctAnswerIndex);

		onQuestionLoaded.onLoaded(question);
	}
	
	private void getNetIncome(Context ctx, int accountNumber, final OnQuestionLoaded onQuestionLoaded)
	{
		Calendar.getInstance();
		int month = Calendar.MONTH;
		randomMonth = rand.nextInt(month) + 1;
		String date = "20140" + randomMonth;
		String url = "https://api.bankapp.co.il/account/" + accountNumber
				+ "/net-income-for-month?date=" + date;

		ApiJsonObjectRequest jsObjRequest = new ApiJsonObjectRequest(url,
		new Response.Listener<JSONObject>()
		{
			@Override
			public void onResponse(JSONObject response)
			{
				ObjectMapper mapper = new ObjectMapper();
		
				try 
				{
					income = mapper.readValue(response.toString(), AccountIncome.class);
				} 
				catch (JsonParseException e) 
				{
					logE(e);
				} 
				catch (JsonMappingException e) 
				{
					logE(e);
				} 
				catch (IOException e) 
				{
					logE(e);
				}
				
				generateIncomeQuestion(onQuestionLoaded);
			}
		}, 
		new Response.ErrorListener() 
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				logE(error);
				generateIncomeQuestion(onQuestionLoaded);
			}
		});

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
		headers.put("secretKey", "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");
		jsObjRequest.setHeaders(headers);
		queue.add(jsObjRequest);
	}
	
	public String getSuccessString()
	{
		return correctStrings.get(rand.nextInt(STRINGS_NUM));
	}
	
	public String getFailureString()
	{
		return wrongStrings.get(rand.nextInt(STRINGS_NUM));
	}
}