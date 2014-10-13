package com.bnhp.api;

import com.bnhp.api.entities.AccountDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
			  include = JsonTypeInfo.As.PROPERTY,
			  property = "type")
@JsonSubTypes({@Type(value = AccountDetails.class)})
public class ServiceResponse 
{
}