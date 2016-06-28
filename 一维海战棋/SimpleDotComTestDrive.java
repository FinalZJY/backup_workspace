
public class SimpleDotComTestDrive {

	public static void main(String[] args) {
		int numOfGuess=0;
		GameHelper helper=new GameHelper();
		SimpleDotCom dot=new SimpleDotCom();  //�������
		
		int randomNum=(int)(Math.random()*5);
		int[] locations={randomNum,randomNum+1,randomNum+2}; //����ڷ�ս��
		boolean isAlive=true;             //����Ƿ����
		dot.setLocationCells(locations);
		
		while(isAlive==true)
		{
			String guess=helper.getUserInput("Enter a number"); //ȡ���������
			String result=dot.checkYourself(guess);   //ִ�й���
			numOfGuess++;
			if(result.equals("kill"))
			{
				isAlive=false;
				System.out.println("You took "+numOfGuess+" guesses.");
			}
		}
		
	}

}
