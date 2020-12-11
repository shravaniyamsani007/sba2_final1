package com.iiht.training.eloan.util;

import java.sql.Timestamp;


public interface EloanUtil {
	
	public static long currentTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.getTime();
	}
}
