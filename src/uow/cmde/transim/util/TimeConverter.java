package uow.cmde.transim.util;

/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */

import java.util.StringTokenizer;

public class TimeConverter {
	
	//From 07:55, 08:10, 8:25, 8:40, 8:55, 9:10, 9:25, 9:40, 9:55, 10:10, 10:25, 
	//16:40, 16:55, 17:10, 17:25, 17:40, 17:55, 18:10, 18:25, 18:40, 18:55, 19:10, 19:25, 19:40, 20:40
	//21:10, 21:40, 22:10
	
	public static final int DEAULT_SECONDS = 7*3600 + 55*60;
	public static final int DEAULT_MINUTES = 7*60 + 55;
	
	/**
	 * convertMinuteToTime
	 * @param minutes
	 * @return
	 */
	public static String convertMinuteToTime(int minutes)
	{
		
		int myHours = minutes/60;
		int myMinutes = minutes % 60;
		
		String stMyHours = myHours >=10? ("" + myHours) : ("0" + myHours);
		String stMyMinutes = myMinutes >=10? ("" + myMinutes) : ("0" + myMinutes);
		
		return (stMyHours + ":" + stMyMinutes);
		
	}
	
	
	/**
	 * convertTimeToMinute
	 * @param time
	 * @return
	 */
	public static int convertTimeToMinute(String time)
	{
		StringTokenizer timeTokenizer = new  StringTokenizer(time,":");
		int hour = Integer.parseInt(timeTokenizer.nextToken());
		int minute = Integer.parseInt(timeTokenizer.nextToken());
		
		return minute = hour*60 + minute;
	}
	
	/**
	 * convertSecondToTime
	 * @param seconds
	 * @return
	 */
	public static String convertSecondToTime(int seconds)
	{
		int myHours =seconds/3600;
		int myMinutes = (seconds % 3600) / 60;
		int mySeconds = (seconds % 3600) % 60;
		
		String stMyHours = myHours >=10? ("" + myHours) : ("0" + myHours);
		String stMyMinutes = myMinutes >=10? ("" + myMinutes) : ("0" + myMinutes);
		String stMySeconds = mySeconds >=10? ("" + mySeconds) : ("0" + mySeconds);
		
		return (stMyHours + ":" + stMyMinutes + ":" + stMySeconds);
	}
	
	/**
	 * convertTimeToSecond
	 * @param time
	 * @return
	 */
	public static int convertTimeToSecond(String time)
	{
		StringTokenizer timeTokenizer = new  StringTokenizer(time,":");
		int hour = Integer.parseInt(timeTokenizer.nextToken());
		int minute = Integer.parseInt(timeTokenizer.nextToken());
		int second = Integer.parseInt(timeTokenizer.nextToken());
		
		return second = hour*3600 + minute*60 + second;
	}
	
	public static boolean isStartNewMinute(String time)
	{
		StringTokenizer timeTokenizer = new  StringTokenizer(time,":");
		int hour = Integer.parseInt(timeTokenizer.nextToken());
		int minute = Integer.parseInt(timeTokenizer.nextToken());
		int second = Integer.parseInt(timeTokenizer.nextToken());
		
		return (second == 0);
	}
	
	
	public static String convertTimePoint(String time)
	{
		int minute = convertTimeToMinute(time);
		
		minute =(((minute - DEAULT_MINUTES)/15) * 15 + DEAULT_MINUTES) + 15;
		
		return (convertMinuteToTime(minute)+":00");
	}
	
	public static void main(String[] args) {
		
		System.out.println("real:" + convertTimePoint("08:15"));
	}
}
