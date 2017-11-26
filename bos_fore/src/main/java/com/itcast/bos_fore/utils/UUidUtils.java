package com.itcast.bos_fore.utils;

import java.util.UUID;

public class UUidUtils {

	public static String getUUID(){
		UUID randomUUID = UUID.randomUUID();
		String randomString = randomUUID.toString();
		String[] split = randomString.split("-");
		String s="";
		for (String string : split) {
			s=s+string;
		}
		return s;
	}
	
}
