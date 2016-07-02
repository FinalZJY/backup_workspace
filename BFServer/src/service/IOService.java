//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Interpreter.Interpreter;
import serviceImpl.CodeServiceImpl.InputGetter;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException;
	
	public void getNextInput(Interpreter interpreter,InputGetter thread)throws RemoteException;
	
	public void sendOutput(String output)throws RemoteException;
}
