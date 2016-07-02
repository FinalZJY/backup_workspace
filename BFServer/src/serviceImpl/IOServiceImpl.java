package serviceImpl;

import java.io.*;
import java.net.Socket;

import Interpreter.Interpreter;
import service.IOService;
import serviceImpl.CodeServiceImpl.InputGetter;

public class IOServiceImpl implements IOService{
	
	private Socket IOSocket;
	private PrintWriter writer;
	private BufferedReader reader;
	public IOServiceImpl(Socket IOSocket,BufferedReader reader,PrintWriter writer){
		try {
			this.IOSocket=IOSocket;
			this.reader=reader;
			this.writer=writer;
        } catch (Exception ex) { ex.printStackTrace(); }
		
	}
	
	public BufferedReader getIOReader() {
		return reader;
	}
	
	public void initReader() {//弃用
		try {
			reader=new BufferedReader(new InputStreamReader(IOSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageToReader() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(IOSocket.getOutputStream());
			pw.println("end");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File("code\\"+userId + "\\" + fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		String content="";
		File f = new File("code\\"+userId + "\\" + fileName);
		try {
			FileReader fw = new FileReader(f);
			BufferedReader br=new BufferedReader(fw);
			while (br.ready()) {
				content=content+br.readLine();
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public String readFileList(String userId) {
		String list="";
		String filepath="code\\"+userId;
		File file = new File(filepath);
		String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "\\" + filelist[i]);
                if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath="
                                        + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());

                } else if (readfile.isDirectory()) {
                	list=list+readfile.getName()+"\n";
                }
        }
		return list;
	}
	
	public void getNextInput(Interpreter interpreter,InputGetter thread) {
		String message;
        try {      	
        	System.out.println("Server: inputReady");
        	while((message = reader.readLine()) != null && !thread.isInterrupted()) {
        		message=message+"\n";
        		System.out.println("Server: input:"+message);
        		interpreter.setInput(message);
        		thread.end=true;
        		break;
        	}
        } catch (Exception ex) { ex.printStackTrace(); }
		
	}
	
	public void sendOutput(String output) {
		writer.println(output);
        writer.flush();
        System.out.println("Server: output:"+output);
	}

}
