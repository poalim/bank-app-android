package com.bnhp.api;

public class JsonProxyResponse
{
	private ServiceResponse serviceResponse = null;	
	
	public JsonProxyResponse() 
	{		
	}
	
	public ServiceResponse getServiceResponse() 
	{
		return serviceResponse;
	}
	
	public void setServiceResponse(ServiceResponse serviceResponse) 
	{
		this.serviceResponse = serviceResponse;
	}
}
