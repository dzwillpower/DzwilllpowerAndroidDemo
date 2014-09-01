package com.wits.dzwillpower.android.utilites;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {

	public String formatTime(long time) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTimeInMillis(Math.max(0, time));

		return String.format("%02d:%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND));
	}
}
