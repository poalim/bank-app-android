package com.bnhp.api.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ResolutionUtils 
{
	/* ------------------------------------------------------------------------
		getPixels
	------------------------------------------------------------------------- */	
	public static int getPixels(double d, Resources r)
	{
		int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) d, r.getDisplayMetrics());
		return px;		
	}
	
	
	/* ------------------------------------------------------------------------
		getScreenSize
	------------------------------------------------------------------------- */	
	public static int getScreenSize(Resources r) 
	{
		int screenSize = r.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

		return screenSize;
		
	}
	
	/* ------------------------------------------------------------------------
		isScreenSizeXLARGE
	------------------------------------------------------------------------- */	
	public static boolean isScreenSizeXLARGE(Resources r) 
	{
		int screenSize = getScreenSize(r);
		
		switch(screenSize)
		{
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	/* ------------------------------------------------------------------------
		getMetricsSize
	------------------------------------------------------------------------- */	
	public static int getMetricsSize(Resources r) 
	{
		DisplayMetrics metrics = r.getDisplayMetrics(); 
			
		return metrics.densityDpi;
		
	}
	
	/* ------------------------------------------------------------------------
		isScreenSizeXLARGE
	------------------------------------------------------------------------- */	
	public static boolean isMetricsXLARGE(Resources r) 
	{
		int metrics = getMetricsSize(r);
		
		switch(metrics)
		{
			case DisplayMetrics.DENSITY_XHIGH:
			{
				return true;
			}
		}
		
		return false;
		
	}	
	

	/* ------------------------------------------------------------------------
	isMetricsBiggerThenXLARGE
	------------------------------------------------------------------------- */	
	public static boolean isMetricsBiggerThenXLARGE(Resources r) 
	{
		int metrics = getMetricsSize(r);
		
		switch(metrics)
		{
			case DisplayMetrics.DENSITY_XHIGH:
			{
				return true;
			}
			case DisplayMetrics.DENSITY_XXHIGH:
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	/* ------------------------------------------------------------------------
	isMetricsXXLARGE
	------------------------------------------------------------------------- */	
	public static boolean isMetricsXXLARGE(Resources r) 
	{
		int metrics = getMetricsSize(r);
		
		switch(metrics)
		{
			
			case DisplayMetrics.DENSITY_XXHIGH:
			{
				return true;
			}
		}
		
		return false;
		
	}
	/* ------------------------------------------------------------------------
		isScreenSizeMEDIUM
	------------------------------------------------------------------------- */	
	public static boolean isMetricsMEDIUM(Resources r) 
	{
		int metrics = getMetricsSize(r);
		
		switch(metrics)
		{
			case DisplayMetrics.DENSITY_MEDIUM:
			{
				return true;
			}
		}
		
		return false;
		
	}	
}
