package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import ui.MainFrame;

public class Client {
	private Socket userSocket;
	private Socket codeSocket;
	private Socket IOSocket;
	private Socket FileSocket;
	private PrintWriter codeWriter;
	private PrintWriter writer;
	private BufferedReader reader;
	private PrintWriter userWriter;
	private BufferedReader userReader;
	private PrintWriter fileWriter;
	private BufferedReader fileReader;
	private MainFrame frame;
	
	public Client() {
		
	}
	
	public void setFrame(MainFrame frame) {
		this.frame=frame;
	}

	public void setUpNetworking() {
        try {
        	userSocket = new Socket("127.0.0.1", 6000);
        	codeSocket = new Socket("127.0.0.1", 6001);
        	IOSocket = new Socket("127.0.0.1", 6002);
        	FileSocket=new Socket("127.0.0.1", 6003);
        	codeWriter=new PrintWriter(codeSocket.getOutputStream());
        	reader = new BufferedReader(new InputStreamReader(IOSocket.getInputStream()));
        	writer = new PrintWriter(IOSocket.getOutputStream()); 
        	fileReader = new BufferedReader(new InputStreamReader(FileSocket.getInputStream()));
        	fileWriter = new PrintWriter(FileSocket.getOutputStream());
        	userReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
        	userWriter = new PrintWriter(userSocket.getOutputStream());
            System.out.println("Client: networking established");
            Thread receiver=new Thread(new OutputReader());
            receiver.start();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
	
	public void runCode(String code) {
		try {
			codeWriter.println(code);
			codeWriter.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void sendInput(String input) {
		try {		
			writer.println(input);
			writer.flush();
			System.out.println("Client: send input: "+input);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
	
	public boolean login(String username,String password) {		
		try {		
			userWriter.println("00*"+username+"_"+password);//00是登入
			userWriter.flush();
			System.out.println("Client: login: "+username+"_"+password);
			if(userReader.readLine().equals("login success")){
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean logout(String username) {
		try {		
			userWriter.println("01*"+username);//01是登出
			userWriter.flush();
			System.out.println("Client: logout: "+username);
			if(userReader.readLine().equals("logout success")){
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean signup(String username,String password) {		
		try {		
			userWriter.println("10*"+username+"_"+password);//10是注册
			userWriter.flush();
			System.out.println("Client: signup: "+username+"_"+password);
			if(userReader.readLine().equals("signup success")){
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public String[] readFileList(String username) {
		ArrayList<String> filelist=new ArrayList<String>();
		try {	
			fileWriter.println("10*"+username);//“10”：读文件列表，“*”：分隔符
			fileWriter.flush();
			String message="";
			while (!(message=fileReader.readLine()).equals("*the end")) {//*the end表示该版本读取完毕
				filelist.add(message);
				System.out.println(message);	
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String[] list=new String[filelist.size()];
		for(int i=0;i<filelist.size();i++){
			list[i]=filelist.get(i);
		}
		return list;
	}
	
	public void saveCode(String username,String fileName,String code) {
		try {	
			fileWriter.println("00*"+username+"*"+fileName+"*"+code);//“00”：写文件，“*”：分隔符
			fileWriter.flush();
			System.out.println("Client: write file: "+username+"*"+fileName+"*"+code);			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public ArrayList<String> readCode(String username,String fileName) {
		ArrayList<String> allVersion=new ArrayList<String>();
		if(fileName==null){
			fileName="not found!";
		}
		/*
		 * 示例：
		 * send:01*admin1*HelloWorld
		 * receive:2
		 *         abcd
		 *         *the end
		 *         abcdefg
		 *         hij
		 *         *the end
		 */
		try {	
			fileWriter.println("01*"+username+"*"+fileName);//“01”：读文件，“*”：分隔符
			fileWriter.flush();
			int version=Integer.parseInt(fileReader.readLine());
			for(int i=0;i<version;i++){//第一个数字表示该文件共有几个版本
				String message="";
				String code="";
				while (!(message=fileReader.readLine()).equals("*the end")) {//*the end表示该版本读取完毕
					code=code+message;
				}
				allVersion.add(code);
			}
			System.out.println(fileReader.readLine());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return allVersion;
	}
	
	public class OutputReader implements Runnable {
		public void run() {
			String message;
            try {
            	
            	while((message = reader.readLine()) != null) {
            		if(message.equals("end")){
            			writer.println("end");
            			writer.flush();
            			System.out.println("Client: resend :end");
            			continue;
            		}
            		else if(message.equals("")){
            			message="\n";
            		}
            		System.out.println("Client: receive output: "+message);
            		frame.appendOutputArea(message);
            	}
            } catch (Exception ex) { ex.printStackTrace(); }
        }
	}
	
	
}
