package hu.comperd.befekt.util;

import java.sql.Date;
import java.util.Calendar;

public class Util {
	
	private Util() {
		//
	}
	
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static boolean isEmpty(final String value) {
		return value == null ? true : value.trim().length() == 0;
	}

	public static String padl(String mit, int hossz, char mivel) {
	    String retstring;
	    String wmit = (isEmpty(mit) ? "" : mit).trim();
	    if (hossz < 0) {
	    	retstring = wmit;
	    } else if (hossz <= wmit.length()) {
	    	retstring = wmit.substring(wmit.length() - hossz, wmit.length());
	    } else {
	    	StringBuffer sb = new StringBuffer(hossz);
	    	for (int ind = 0; ind < hossz; ind++) {
	    		if (ind < hossz - wmit.length()) {
	    			sb.append(mivel);
	    		} else {
	    			sb.append(wmit.charAt(ind - (hossz - wmit.length())));
	    		}
	    	}
	    	retstring=new String(sb);
	    }
	    return retstring;
	}
}
