package runner;

import java.util.Set;

import socket.RemoteHelper;
import socket.Server;

public class ServerRunner {
	
	public ServerRunner() {
		System.out.println("Sever : working");
		new Server();
	}
	
	public static void main(String[] args) {
		new ServerRunner();
		System.out.println("Sever exist");
	}
}
