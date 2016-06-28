import java.io.*;
import java.util.ArrayList;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
public class GameHelper {
	 public String getUserInput(String prompt) {
	     String inputLine = null;
	     System.out.print(prompt + "  ");
	     try {
	       BufferedReader is = new BufferedReader(
		 new InputStreamReader(System.in));
	       inputLine = is.readLine();
	       if (inputLine.length() == 0 )  return null; 
	     } catch (IOException e) {
	       System.out.println("IOException: " + e);
	     }
	     return inputLine;
	  }

	public ArrayList<String> placeDotCom(int numOfships) {
		ArrayList<String> ships = new ArrayList<String>();
		int randomNum3=(int)(Math.random()*6);
		int randomNum4=(int)(Math.random()*6);
		String[] line={"A","B","C","D","E","F","G"};
		String[] list={"0","1","2","3","4","5","6"};
		ships.add(line[randomNum3]+list[randomNum4]);
		
		int randomNum5=(int)(Math.random()*2);
		if(randomNum5==0)
		{
			ships.add(line[extend(randomNum3)]+list[randomNum4]);
			ships.add(line[extend(randomNum3)+1]+list[randomNum4]);
		}
		else {
			ships.add(line[randomNum3]+list[extend(randomNum4)]);
			ships.add(line[randomNum3]+list[extend(randomNum4)+1]);
		}
		return ships;
	}
	
	int extend(int Num)
	{
		int newLine=Num;
		int randomNum6=(int)(Math.random()*2);
		if(randomNum6==0)
		{
			newLine=newLine+1;
			if(newLine>=7)
			{
				newLine=newLine-3;
			}
		}
		else {
			newLine=newLine-2;
			if(newLine<=0)
			{
				newLine=newLine+3;
			}
		}
		return newLine;
	}
}
