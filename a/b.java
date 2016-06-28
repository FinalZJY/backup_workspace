
public class b extends ReadAndWriteFile implements c {
	public static String SetRandom() {
		String[] line={"A","B","C","D","E","F","G"};
		int randomNum3=(int)(Math.random()*6);
		String LineCharacter=line[randomNum3];
		int randomNum4=(int)(Math.random()*6);
		String[] list={"0","1","2","3","4","5","6"};
		String ListCharacter=list[randomNum4];
		return LineCharacter+ListCharacter;
	}

	public void text() {
		System.out.print("asd");
		
	}


}
