package serviceImpl;

import java.io.*;
import java.rmi.*;

import Interpreter.Interpreter;
import service.*;

public class CodeServiceImpl implements CodeService{
	private Interpreter interpreter;
	private BufferedReader reader;
	private IOService IOService;
	private String result;
	public CodeServiceImpl(BufferedReader reader,IOService IOService) throws RemoteException{
		this.reader=reader;
		this.IOService=IOService;
		String code=readCode();
		
	}
	
	public String runCode(String code){
		interpreter.run(code);
		return interpreter.getOutput();
	}
	
	public String readCode() {
		String code="";
    	String message;
        try {
        	while ((message=reader.readLine())!=null) {
				code=message;
				System.out.println("Server: code: "+message);
				interpreter=new Interpreter();
				InputGetter inputThread = new InputGetter();
				inputThread.start();
				result=runCode(code);
				System.out.println("Server: code result: "+result);
				IOService.sendOutput(result);
				if(!inputThread.end){
					((IOServiceImpl)IOService).sendMessageToReader();
				}
				inputThread.interrupt();
			}
    		
        } catch (Exception ex) { ex.printStackTrace(); }
        return code;
    }
	
	public String getResult() {
		return result;
	}
	
	public class InputGetter extends Thread {
		boolean end=false;
		public void run() {
            try {
				IOService.getNextInput(interpreter,this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
		
		public boolean isInterrupted() {
			return super.interrupted();
		}
	}
}
