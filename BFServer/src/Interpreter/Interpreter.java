package Interpreter;

public class Interpreter {
	private Memory memory=new Memory();
	private String code;
	private int ptr_code=0;
	private int ptr=0;
	private String input="";
	private String output="";
	
	public Interpreter(){
		memory.add(0);
	}
	
	public void run(String code) {
		this.code=code;
		while(ptr_code<code.length()){
			switch(code.charAt(ptr_code)){
				case '>':
					++ptr;
					if(ptr>=memory.size()){
						memory.add(0);
					}
					break;
				case '<':--ptr;break;
				case '+':memory.ADD(ptr);break;
				case '-':memory.SUB(ptr);break;
				case '.':
					
					out(""+(char)(int)memory.get(ptr));
					break;
				case ',':
					memory.set(ptr, (int)getInput());break;
				case '[':
					if(((int)memory.get(ptr))==0){
						toEndOfCircle();
					}
					break;
				case ']':
					if(((int)memory.get(ptr))!=0){
						toBeginOfCircle();
					}
					break;
				default:System.out.println("Unknown operator:"+code.charAt(ptr_code));
			}
			ptr_code++;
		}
	}

	public void toEndOfCircle() {
		int numOfCircle=0;
		while (true) {
			ptr_code++;
			if(code.charAt(ptr_code)=='['){
				numOfCircle++;
			}
			else if (code.charAt(ptr_code)==']') {
				if(numOfCircle==0){
					break;
				}
				else {
					numOfCircle--;
				}
			}
		}
		
	}
	
	private void toBeginOfCircle() {
		int numOfCircle=0;
		while (true) {
			ptr_code--;
			if(code.charAt(ptr_code)==']'){
				numOfCircle++;
			}
			else if (code.charAt(ptr_code)=='[') {
				if(numOfCircle==0){
					break;
				}
				else {
					numOfCircle--;
				}
			}
		}
		
	}
	
	public char getInput() {
		while (input.equals("")) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//等待用户输入，50毫秒轮询一次
		}
		char nextInput=input.charAt(0);
		if(input.length()==1){
			input="";
		}
		else {
			input=input.substring(1);
		}
		return nextInput;
	}
	
	public void setInput(String input) {
		this.input=input;
	}

	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output=output;
	}
	
	public void out(String output) {
		this.output=this.output+output;
	}
}
