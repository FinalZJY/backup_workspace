package runner;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class Function {
	private static ArrayList<String> codeHistory=new ArrayList<String>();
	private JTextArea codeArea;//BFFrame's codeArea
	private static int ptr=0;
	public Function(JTextArea codeArea) {
		this.codeArea=codeArea;
		codeHistory.add("code");
		Thread codeHistoryThread=new Thread(new CodeHistoryHandeler());
		codeHistoryThread.start();
	}
	
	public static String undo() throws NoLastVersionException{	
		if(ptr>0){
			ptr--;
			String code=codeHistory.get(ptr);
			return code;
		}
		else {
			throw new NoLastVersionException();
		}
	}
	
	public static String redo() throws NoLaterVersionException{	
		if(ptr<codeHistory.size()-1){
			ptr++;
			String code=codeHistory.get(ptr);
			return code;
		}
		else {
			throw new NoLaterVersionException();
		}
	}
	
	class CodeHistoryHandeler implements Runnable {
		public void run() {
			try {
				while (true) {
					String code=codeArea.getText();
					if(code.equals(codeHistory.get(ptr))){
						Thread.sleep(2000);
					}
					else {
						for(int i=ptr+1;i<codeHistory.size();i++){
							codeHistory.remove(ptr+1);
						}
						codeHistory.add(code);
						ptr++;
					}
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
