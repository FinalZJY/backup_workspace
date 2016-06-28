import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.Iterator;

public class Serialization implements Serializable{
	String string = "abc";
	public static void main(String[] args) {
		Serialization serialization=new Serialization();
		serialization.write();
		serialization.change();
		serialization.read();
	}
	public void write() {
		try{
			File file=new File("D:\\eclipse\\eclipse\\workspace\\a\\abcd.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("abcde");
	        writer.close();
	        System.out.println(file.getPath());
			FileOutputStream fileOutputStream=new FileOutputStream("a.ser");
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(string);
			objectOutputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(string);
	}
	public void read() {
		try{
			
			FileInputStream fileInputStream=new FileInputStream("a.ser");
			ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
			string=(String)objectInputStream.readObject();
			
			FileReader fr=new FileReader("abcd.txt");
			BufferedReader br=new BufferedReader(fr);
			while (br.ready()) {
				string=string+br.readLine();
			}
			objectInputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(string);
	}
	public void change() {
		string="d";
		System.out.println(string);
	}

}
