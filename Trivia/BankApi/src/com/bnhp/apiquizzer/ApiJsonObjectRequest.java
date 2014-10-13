package com.bnhp.apiquizzer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.error.ParseError;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;


public class ApiJsonObjectRequest extends JsonRequest<JSONObject> 
{
	private Map<String,String> reqHeaders;
	
    /**
     * Creates a new request.
     * @param url URL to fetch the JSON from
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public ApiJsonObjectRequest(String url, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }
    
    public ApiJsonObjectRequest(int method,String url, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try 
        {
        	reqHeaders = response.headers;
            String jsonString =
                new String(response.data, "UTF-8");/*HttpHeaderParser.parseCharset(response.headers));*/
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
    
    public Map<String,String> getReqHeaders()
    {
    	return reqHeaders;
    }
}