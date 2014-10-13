package com.bnhp.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bnhp.api.entities.AccountDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAccountDetails 
{
	String url = "";
	private AccountDetails object = null;
	private final String TAG = getClass().getSimpleName();
    
    public JsonAccountDetails(Context context, String accountNumber)
    {
    	/*url = "https://api.bankapp.co.il/account/" + accountNumber + "/details";
    	RequestQueue queue = Volley.newRequestQueue(context);
    	
    	ApiJsonObjectRequest jsObjRequest = new ApiJsonObjectRequest(url,
    			new Response.Listener<JSONObject>()
    			{
    				@Override
    				public void onResponse(JSONObject response)
    				{
    					ObjectMapper mapper = new ObjectMapper();
    					
    					try {
    						object = mapper.readValue(response.toString(), AccountDetails.class);
						} catch (JsonParseException e) {
							logE(e);
						} catch (JsonMappingException e) {
							logE(e);
						} catch (IOException e) {
							logE(e);
						}
    				}
    			}, new Response.ErrorListener() {
    				@Override
    				public void onErrorResponse(VolleyError error)
    				{
    					
    				}
				});*/
    	
    	/*ApiJsonArrayRequest jsObjRequest = new ApiJsonArrayRequest(url,
    			new Response.Listener<JSONArray>()
    			{
    				@Override
    				public void onResponse(JSONArray response)
    				{
    					ObjectMapper mapper = new ObjectMapper();
    					
    					List<AccountDetails> list = new ArrayList<AccountDetails>();
    					
    					JSONObject object;
    					
    					for(int i = 0; i < response.length(); i++)
    					{
    						try {
								object = response.getJSONObject(i);
								list.add(mapper.readValue(object.toString(), AccountDetails.class));
							} catch (JSONException e) {
								logE(e);
							} catch (JsonParseException e) {
								logE(e);
							} catch (JsonMappingException e) {
								logE(e);
							} catch (IOException e) {
								logE(e);
							}
    					}
    				}
    			}, new Response.ErrorListener() {
    				@Override
    				public void onErrorResponse(VolleyError error)
    				{
    					
    				}
				}); ARRAY EXAMPLE*/
    	
    		/*Map<String, String> headers = new HashMap<String, String>();
    		headers.put("accessKey", "AKIAJ5JDGGOK3IYF57AA");
    		headers.put("secretKey", "6FJ64HDDPwHKH6BbU/Wnz4fZMwS1NwgOD9LosBWB");
    		jsObjRequest.setHeaders(headers);
    		queue.add(jsObjRequest);*/    			
    }
    
    private void logE(Exception e)
    {
    	Log.e(TAG, e != null ? e.getMessage() : "", e);
    }

	public AccountDetails getObject() {
		return object;
	}
}


