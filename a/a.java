
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class a {
	static String actions="1 2 3 4 ";
	public static void main(String[] args) {
		
		try {
			File newestVersion = new File("_version4.txt");
			BufferedReader reader=new BufferedReader(new FileReader(newestVersion));
			String message="";
			while ((message=reader.readLine())!=null) {
				System.out.println(message);
			}
			System.out.println("aaa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	public void change() {
		actions="132";
	}

}
