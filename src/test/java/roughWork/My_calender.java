package roughWork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class My_calender {

	public static void main(String[] args) {
		
       Date currentDate = new Date();
		System.out.println(currentDate);
		
		//String date = table.get("DateOfPurchase");  // o/p is string ...change the format
		String date="08-04-2013";
		System.out.println(date);
        
        Date dateToBeSelected = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				
		try {
			dateToBeSelected = formatter.parse(date);
			System.out.println(dateToBeSelected);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String month=new SimpleDateFormat("MMMM").format(dateToBeSelected);
		System.out.println(month);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeSelected);
		int year = cal.get(Calendar.YEAR);
		
		int day  = cal.get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
		System.out.println(year);
		String month_yearExpected = month+" "+year;
		System.out.println(month_yearExpected);
		
	}
}
