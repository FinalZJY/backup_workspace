
import java.io.*;
public class ReadAndWriteFile {    
    public static String read(){
    	String content=null;
    	try{
    		File fileA =new File("A.txt");
    		if(!fileA.exists()){
    			System.out.println("Can not find the file");
    		}

    		FileReader fileReaderer = new FileReader(fileA.getName());
    		BufferedReader bufferedReader = new BufferedReader(fileReaderer);
    		content=bufferedReader.readLine();
    		bufferedReader.close();
    		System.out.println("Reading completed");
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		System.out.println("Reading file error");
    	}
    	return content;
    }
    
    public static void write(String data){
    	try{
    		File fileB =new File("CurriculumSchedule.txt");
    		if(!fileB.exists()){
    			fileB.createNewFile();
    		}

    		FileWriter fileWritter = new FileWriter(fileB.getName(),true);//true = append file
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    		bufferWritter.write(data);
    		bufferWritter.close();
    		System.out.println("writing completed");
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		System.out.println("writing file error");
    	}
    }

}
