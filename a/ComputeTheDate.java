import java.util.Date;  
import java.text.SimpleDateFormat; 
import java.util.Scanner;
public class ComputeTheDate {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("The year:");
		int year = sc.nextInt();
		while (year<0) {
			System.out.print("The year is not available.Please try again:");
			year = sc.nextInt();
		}
		
		System.out.print("The month:");
		int month = sc.nextInt();
		while ((month<0)||(month>12)) {
			System.out.print("The month is not available.Please try again:");
			month = sc.nextInt();
		}
		
		System.out.print("The day:");
		int day = sc.nextInt();
		while ((day<0)||(day>31)) {
			System.out.print("The day is not available.Please try again:");
			day = sc.nextInt();
		}
		
		String theDate = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
		try {
            System.out.println("There are "+dayDist(theDate)+" days from today.");
        } 
		catch (Exception e) {
			e.printStackTrace();
    		System.out.println("error");
		}
	}

	
	public static int dayDist(String dateStr) throws Exception{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date date=df.parse(dateStr);
		long timeMillion=new Date().getTime()-date.getTime();
		return (int)(timeMillion/(24l*60*60*1000));
	}
	
}
