package com.bitekite.driver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;

import com.bitekite.Log;

/**
 * date and time related operations functions
 * @author shankar
 *
 */
@SuppressLint("SimpleDateFormat")
public class DateOperations {

	public DateOperations(){
		
	}
	
	/**
	 * Converts UTC time to local time
	 * @param utcTime UTC time String
	 * @return Local time String
	 */
	@SuppressLint("SimpleDateFormat")
	public String utcToLocal(String utcTime) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date myDate = simpleDateFormat.parse(utcTime);
			String localDate = sdf.format(myDate);
			return localDate;
		} catch (Exception e1) {
			Log.e("e1","="+e1);
			return utcTime;
		}
	}


	/**
	 * Converts date string from 2014-01-12 00:00 to 12 Jan, 2014 12:00 AM
	 * @param dateTime 
	 * @return
	 */
	public String convertDate(String dateTime){
		Log.e("dateTime", "="+dateTime);
		try{
		String date = dateTime.split(" ")[0];
		
		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		
		
		if("01".equalsIgnoreCase(month)){
			month = "Jan";
		}
		else if("02".equalsIgnoreCase(month)){
			month = "Feb";
		}
		else if("03".equalsIgnoreCase(month)){
			month = "Mar";
		}
		else if("04".equalsIgnoreCase(month)){
			month = "Apr";
		}
		else if("05".equalsIgnoreCase(month)){
			month = "May";
		}
		else if("06".equalsIgnoreCase(month)){
			month = "Jun";
		}
		else if("07".equalsIgnoreCase(month)){
			month = "Jul";
		}
		else if("08".equalsIgnoreCase(month)){
			month = "Aug";
		}
		else if("09".equalsIgnoreCase(month)){
			month = "Sept";
		}
		else if("10".equalsIgnoreCase(month)){
			month = "Oct";
		}
		else if("11".equalsIgnoreCase(month)){
			month = "Nov";
		}
		else if("12".equalsIgnoreCase(month)){
			month = "Dec";
		}
		
		
		String time = dateTime.split(" ")[1];
		
		String hour = time.split(":")[0];
		String min = time.split(":")[1];
		
		String amOrpm = Integer.parseInt(hour)>=12?"PM":"AM";
		String newHour = "";
		
		if(Integer.parseInt(hour)==0){
			newHour = "12";
		}
		else if(Integer.parseInt(hour)<=12){
			newHour = hour;
		}
		else{
			newHour = ""+(Integer.parseInt(hour) - 12);
		}
		
		String finalTime = ""+ newHour + ":" + min + " " + amOrpm; 
		
		
		String finalDate = ""+ day + " " + month + ", " + year + " " + finalTime; 
		
		return finalDate;
		}
		catch(Exception e){
			e.printStackTrace();
			return dateTime;
		}
	}
	
	
	public static String getCurrentTime() {
	    long foo = System.currentTimeMillis();
	    Date date = new Date(foo);
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
	}
	
	
}
