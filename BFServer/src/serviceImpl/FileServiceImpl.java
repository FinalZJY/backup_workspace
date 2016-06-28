package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileServiceImpl {

	private PrintWriter fileWriter;
	private BufferedReader fileReader;
	
	public FileServiceImpl(BufferedReader fileReader,PrintWriter fileWriter){
		this.fileReader=fileReader;
		this.fileWriter=fileWriter;
		
		waitCommand();
	}
	
	public void waitCommand() {
		String message;
        try {      	
        	System.out.println("Server: fileService: waiting command");
        	while((message = fileReader.readLine()) != null) {
        		
        		System.out.println("Server: file:"+message);
        		if(message.substring(0, 2).equals("00")){//“00”：写文件，“01”：读文件，“*”：分隔符
        			String[] s=message.split("\\*");
        			writeFile(s[3], s[1], s[2]);
        		}
        		else if (message.substring(0, 2).equals("01")) {
        			String[] s=message.split("\\*");
        			String code=readFile(s[1], s[2]);
        			System.out.println("Server: send code: "+code);
        			fileWriter.println(code);
        			fileWriter.flush();
				}
        		else {
        			System.out.println("Server: unknown command: "+message.substring(0, 2));
				}
        		Thread.sleep(50);
        	}
        } catch (Exception ex) { ex.printStackTrace(); }
	}
	
	public boolean writeFile(String file, String userId, String fileName) {
		int version=1;
		File floder=new File("code");
		File userFloder = new File("code\\"+userId);
		File f = new File("code\\"+userId + "\\" + fileName);
		
		try { 
			if  (!floder .exists()  && !floder .isDirectory()){        
				floder .mkdir();    
			}
			if  (!userFloder .exists()  && !userFloder .isDirectory()){        
				userFloder .mkdir();    
			}
			if (f.exists()) {
				String[] versionList = f.list();
				for(String version_string:versionList){
					if(Integer.parseInt(version_string.substring(8,version_string.length()-4))>version){
						version=Integer.parseInt(version_string.substring(8,version_string.length()-4));
					}
				}
				version++;
			}
			else {
				f.mkdir();
			}
			File newestVersion = new File("code\\"+userId + "\\" + fileName+"\\"+"_version"+version+".txt");
			newestVersion.createNewFile();
			FileWriter fw = new FileWriter(newestVersion, false);
			fw.write(file);
			fw.flush();
			fw.close();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String readFile(String userId, String fileName) {
		int version=1;		
		String content="";
		File userFloder = new File("code\\"+userId + "\\" + fileName);
		if(!userFloder.exists()){
			content="not found!\n*the end\n";
		}
		else {
			String[] versionList = userFloder.list();
			for(String version_string:versionList){
				version=Integer.max(version, Integer.parseInt(version_string.substring(8,version_string.length()-4)));
			}
			
			try {
				for(int i=version;i>0;i--){
					File thisVersion = new File("code\\"+userId + "\\" + fileName+"\\"+"_version"+i+".txt");
					FileReader fr = new FileReader(thisVersion);
					BufferedReader br=new BufferedReader(fr);
					while (br.ready()) {
						content=content+br.readLine();
					}
					content=content+"\n*the end\n";
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		content=version+"\n"+content;
		return content;
	}

	public String readFileList(String userId) {
		String list="";
		File file = new File("code\\"+userId);
		String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
        	list=list+filelist[i]+"\n";
        }
		return list;
	}
}
