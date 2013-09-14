package com.worldbestsoft.util;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * Produces UUID-based tokens. </br>
 * This is inpired by the following article 
 * <a href="http://stackoverflow.com/questions/1106377/detect-when-browser-receives-file-download">
 * Detect when browser receives file download</a> from stackoverflow.com. Instead of using a
 * cookie, we use a {@link HashMap} to store the tokens.
 */
@Component("downloadToken")
public class DownloadToken  {

	private  HashMap<String,String> tokens = new HashMap<String, String>();
	
    public String check(String token) {
		return tokens.get(token);
	}
	
    public String generate() {
		String uid = UUID.randomUUID().toString();
		tokens.put(uid, uid);
		return uid;
	}
	
    public void remove(String token) {
		tokens.remove(token);
	}
}
