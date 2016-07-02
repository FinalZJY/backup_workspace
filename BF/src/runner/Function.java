package runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Function {
	private static ArrayList<String> codeHistory=new ArrayList<String>();
	private JTextArea codeArea;//BFFrame's codeArea
	private static int ptr=0;
	public Function(JTextArea codeArea) {
		this.codeArea=codeArea;
		codeHistory.add("code");
		Thread codeHistoryThread=new Thread(new CodeHistoryHandeler());
		codeHistoryThread.start();
	}
	
	public static String undo() throws NoLastVersionException{	
		if(ptr>0){
			ptr--;
			String code=codeHistory.get(ptr);
			return code;
		}
		else {
			throw new NoLastVersionException();
		}
	}
	
	public static String redo() throws NoLaterVersionException{	
		if(ptr<codeHistory.size()-1){
			ptr++;
			String code=codeHistory.get(ptr);
			return code;
		}
		else {
			throw new NoLaterVersionException();
		}
	}
	
	public static void saveUser(String username,String password) {	
		File file=new File("user_information.txt");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedWriter writer=new BufferedWriter(new FileWriter(file));
			writer.write(username+"\n");
			writer.write(password);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] readUser() {	
		File file=new File("user_information.txt");
		String user[]=new String[2];
		try {
			if(!file.exists()){
				return user;
			}
			else {
				BufferedReader reader=new BufferedReader(new FileReader(file));
				user[0]=reader.readLine();
				user[1]=reader.readLine();
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	class CodeHistoryHandeler implements Runnable {
		public void run() {
			try {
				while (true) {
					String code=codeArea.getText();
					if(code.equals(codeHistory.get(ptr))){
						Thread.sleep(2000);
					}
					else {
						for(int i=ptr+1;i<codeHistory.size();i++){
							codeHistory.remove(ptr+1);
						}
						codeHistory.add(code);
						ptr++;
					}
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
