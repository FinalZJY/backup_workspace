package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private ServerSocket userServer;
	private ServerSocket IOServer;
	private ServerSocket codeServer;
	private ServerSocket fileServer;
	private Socket userSocket;
	public static ArrayList<String> loginUser=new ArrayList<String>();
	
	public Server() {
		initial();
	}
	
	public void initial() {
        try {
        	userServer = new ServerSocket(6000);
        	codeServer = new ServerSocket(6001);
        	IOServer = new ServerSocket(6002);
        	fileServer = new ServerSocket(6003);
            while(true) {
            	userSocket = userServer.accept();               
                Thread userThread = new Thread(new AccountHandler(userSocket));
                userThread.start();
                System.out.println("Server: userSocket linked");
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
	
	public class AccountHandler implements Runnable {
		private DataRemoteObject services;		
		private Socket codeSocket;
		private Socket fileSocket;
		private Socket IOSocket;
		public AccountHandler(Socket userSocket) {
			// TODO Auto-generated constructor stub
		}

		public void run() {
			try {
	        	codeSocket = codeServer.accept(); 
	        	System.out.println("Server: codeSocket linked");
	        	IOSocket = IOServer.accept(); 
	        	System.out.println("Server: IOSocket linked");
	        	fileSocket = fileServer.accept(); 
	        	System.out.println("Server: fileSocket linked");
	        	BufferedReader codereader=new BufferedReader(new InputStreamReader(codeSocket.getInputStream()));
	        	BufferedReader IOreader=new BufferedReader(new InputStreamReader(IOSocket.getInputStream()));
	        	PrintWriter IOwriter = new PrintWriter(IOSocket.getOutputStream());
	        	BufferedReader fileReader=new BufferedReader(new InputStreamReader(fileSocket.getInputStream()));
	        	PrintWriter fileWriter = new PrintWriter(fileSocket.getOutputStream());
            	services=new DataRemoteObject(userSocket,codereader,IOSocket,IOreader,IOwriter,fileReader,fileWriter);
	        } catch (Exception ex) { ex.printStackTrace(); }
        }
	}
}
