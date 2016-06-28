
public class Test {

	public static void main(String[] args) {
		ALU alu=new ALU();
		
		String s1=alu.floatDivision("00111110111000000","00111111000000000", 8, 8);
		System.out.println(s1);
	}

}
