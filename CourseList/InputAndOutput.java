import java.util.Scanner;

public class InputAndOutput {
	public static String GetInput() {

		Scanner sc = new Scanner(System.in);	
		String line=sc.nextLine();
			
		MessageHandling message = MessageHandling.getMessage(line);
		return line;

	}

	public static void output(String s) {
		System.out.println(s);
	}
	
	public static void promptUser(){
		System.out.println("Give a command:");
		System.out.println("For example:");
		System.out.println("Add 星期四；三，四节；计算与软件工程；仙2-407");
		System.out.println("Remove Thursday;the first and second;计算与软件工程;仙2-407");
	}
}
