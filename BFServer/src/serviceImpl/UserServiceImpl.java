package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

import service.UserService;
import socket.Server;

public class UserServiceImpl implements UserService{
	private Socket userSocket;
	private String username;
	private boolean logIn=false;
	public UserServiceImpl(Socket userSocket) {
		this.userSocket=userSocket;
		waitCommand();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public boolean isLogIn() {
		return this.logIn;
	}
	
	private void waitCommand() {
		String message;
        try {      	
        	System.out.println("Server: userService: waiting command");
        	BufferedReader reader=new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
        	PrintWriter writer = new PrintWriter(userSocket.getOutputStream());
        	while((message = reader.readLine()) != null) {        		
        		System.out.println("Server: user:"+message);
        		if(message.substring(0, 2).equals("00")){//“00”：login，“*”：分隔符，例如：00*admin*password
        			message=message.substring(3);
        			String[] s=message.split("_");
        			
        			boolean alreadyLogin=false;
        			for(String user:Server.loginUser){
        				if(user.equals(s[0])){
        					System.out.println(user);
        					alreadyLogin=true;
        				}
        			}
        			if(alreadyLogin){
        				writer.println(s[0]+" has already login");
        			}
        			else {
        				login(s[0], s[1]);
            			System.out.println("Server: login: "+message);
            			if(logIn){
            				writer.println("login success");
            			}
            			else {
            				writer.println("login fail");
    					}
					}
        			writer.flush();
        		}
        		else if (message.substring(0, 2).equals("01")) {//“01”：logout，“*”：分隔符，例如：01*admin
        			message=message.substring(3);
        			logout(message);
        			System.out.println("Server: logout: "+message);
        			if(logIn){
        				writer.println("logout fail");
        			}
        			else {
        				writer.println("logout success");
					}
        			writer.flush();
				}
        		else if (message.substring(0, 2).equals("10")) {//“10”：signup,“*”：分隔符,:例如：10*admin*password
        			message=message.substring(3);
        			String[] s=message.split("_");
        			login(s[0], s[1]);
        			System.out.println("Server: login: "+message);
        			if(signup(s[0], s[1])){
        				writer.println("signup success");
        			}
        			else {
        				writer.println("signup fail");
					}
        			writer.flush();
				}
        		else {
        			System.out.println("Server: unknown command: "+message.substring(0, 2));
				}
        		Thread.sleep(50);
        	}
        } catch (Exception ex) { ex.printStackTrace(); }

	}
	
	@Override
	public boolean login(String username, String password) throws RemoteException {
		if(logIn){
			return false;
		}	
		
		File adminFloder = new File("admin");
		if  (!adminFloder .exists()  && !adminFloder .isDirectory()){        
			adminFloder .mkdir();    
		}
		File database = new File("admin\\admin_database.txt");
		try {
			if  (!database .exists()  && !database .isDirectory()){        
				database .createNewFile();    
			}
			ArrayList<String> adminList=new ArrayList<String>();
			BufferedReader reader=new BufferedReader(new FileReader(database));
			String message="";
			while ((message=reader.readLine())!=null) {
				adminList.add(message);
			}
			for(String admin:adminList){
				String[] s=admin.split("_");
				if(s[0].equals(username)){
					if(s[1].equals(password)){
						this.username=username;
						logIn=true;
						Server.loginUser.add(username);
						return true;
					}
					else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		if(!logIn){
			return false;
		}
		logIn=false;
		this.username="";
		Server.loginUser.remove(username);
		return true;
	}
	
	public boolean signup(String username, String password) {
		File adminFloder = new File("admin");
		if  (!adminFloder .exists()  && !adminFloder .isDirectory()){        
			adminFloder .mkdir();    
		}
		File database = new File("admin\\admin_database.txt");
		try {
			if  (!database .exists()){        
				database .createNewFile();    
			}
			
			ArrayList<String> adminList=new ArrayList<String>();//检查是否有同名账户
			BufferedReader reader=new BufferedReader(new FileReader(database));
			String message="";
			while ((message=reader.readLine())!=null) {
				adminList.add(message);
			}
			for(String admin:adminList){
				String[] s=admin.split("_");
				if(s[0].equals(username)){
					return false;
				}
			}
			
			BufferedWriter writer=new BufferedWriter(new FileWriter(database,true));
			writer.append(username+"_"+password+"\n");
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
