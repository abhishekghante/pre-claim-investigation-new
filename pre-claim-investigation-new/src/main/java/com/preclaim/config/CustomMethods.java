package com.preclaim.config;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CustomMethods {
	
	private static final Logger LOGGER = Logger.getLogger(CustomMethods.class);
	
	public static ArrayList<String> importCaseHeader()
	{
		ArrayList<String> header_list = new ArrayList<String>();
		header_list.add("Policy Number");
		header_list.add("Investigation Category");
		header_list.add("Insured Name");
		header_list.add("Insured DOD");
		header_list.add("Insured DOB");
		header_list.add("Sum Assured");
		header_list.add("Intimation Type");
		header_list.add("Claimant City");
		header_list.add("Nominee Name");
		header_list.add("Nominee Mob");
		header_list.add("Nominee Address");
		header_list.add("Insured Address");
		return header_list;
	}
	
	public static void logError(Exception e)
	{
		String error_message = "*************" + e.getClass() + "*************\n";
		LOGGER.error(e.getMessage());
		LOGGER.error(e.getCause());
		StackTraceElement[] trace = e.getStackTrace();
	    for (StackTraceElement traceElement : trace)
	        error_message += "\tat " + traceElement + "\n";
		LOGGER.error(error_message);
		
	}

}
