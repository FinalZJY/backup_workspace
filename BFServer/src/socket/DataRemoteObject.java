package socket;

import java.io.*;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import Interpreter.Interpreter;
import service.*;
import serviceImpl.*;
import serviceImpl.CodeServiceImpl.InputGetter;

public class DataRemoteObject extends UnicastRemoteObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService IOService;
	private UserServiceImpl userService;
	private CodeService codeService;
	private FileServiceImpl FileService;
	
	
	protected DataRemoteObject(Socket userSocket,BufferedReader codereader,Socket IOSocket,BufferedReader IOreader,PrintWriter IOwriter,
			BufferedReader fileReader,PrintWriter fileWriter) throws RemoteException {
		Thread userThread = new Thread(new UserHandler(userSocket));
		userThread.start();
		Thread codeThread = new Thread(new Handler(codereader,IOSocket,IOreader,IOwriter));
		codeThread.start();
		Thread fileThread = new Thread(new FileHandler(fileReader,fileWriter));
		fileThread.start();
		
	}

	public DataRemoteObject()  throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	public class Handler implements Runnable {
		BufferedReader codereader;
		PrintWriter IOwriter;
		BufferedReader IOreader;
		Socket IOSocket;
		public Handler(BufferedReader codereader,Socket IOSocket,BufferedReader IOreader,PrintWriter IOwriter) {
			this.codereader=codereader;
			this.IOreader=IOreader;
			this.IOwriter=IOwriter;
			this.IOSocket=IOSocket;
		}
		public void run() {
			System.out.println("Server: IOHandler's working");
			IOService=new IOServiceImpl(IOSocket,IOreader,IOwriter);
			System.out.println("Server: CodeHandler's working");
			try {
				codeService =new CodeServiceImpl(codereader,IOService);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
	}
	
	public class UserHandler implements Runnable {
		private Socket userSocket;
		public UserHandler(Socket userSocket) {
			this.userSocket=userSocket;
			
		}
		public void run() {
			System.out.println("Server: UserHandler's working");
			userService = new UserServiceImpl(userSocket);
        }
	}
	
	public class FileHandler implements Runnable {
		private PrintWriter fileWriter;
		private BufferedReader fileReader;
		public FileHandler(BufferedReader fileReader,PrintWriter fileWriter) {
			this.fileReader=fileReader;
			this.fileWriter=fileWriter;
		}
		public void run() {
			System.out.println("Server: FileHandler's working");
			FileService=new FileServiceImpl(fileReader,fileWriter);
        }
	}

}
