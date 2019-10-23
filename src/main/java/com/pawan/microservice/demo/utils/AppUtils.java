package com.pawan.microservice.demo.utils;

public class AppUtils {
	
	
	
	public final static String  USERS_COLLECTION_NAME="users";

	

	public static String getPrefixZeroString(Object v,int length){
		String value="";
		if(v!=null){
			value=v.toString().trim();
		}
		if(value.length()>length) {
			int extra=value.length()-length;
			value.substring(value.length()-extra);
		}
		
		while(value.length()<length){
			value="0"+value;
		}
		
		return value;
		
	}
	
	public static boolean isNotNullOfEmpty(String v) {
		if(v!=null && !v.trim().isEmpty()) {
			return true;
		}
		
		return false;
	}
}
