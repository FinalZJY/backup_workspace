package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import service.IOService;
import socket.Client;
import socket.RemoteHelper;
import ui.MainFrame;

public class ClientRunner {
	private RemoteHelper remoteHelper;
	private MainFrame mainFrame;
	private Client client;
	private Function function;
	public ClientRunner() {
		linkToServer();
		initGUI();
		initFuction();
	}
	
	private void linkToServer() {
		try {
			client=new Client();
			client.setUpNetworking();
			//remoteHelper = RemoteHelper.getInstance();
			//remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("Client:linked");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void initGUI() {
		mainFrame = new MainFrame(client);
		client.setFrame(mainFrame);
	}
	
	private void initFuction() {
		function=new Function(mainFrame.getCodeArea());
	}
	
	public void test(){
		try {
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("2", "admin", "testFile"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
		//cr.test();
	}
}
