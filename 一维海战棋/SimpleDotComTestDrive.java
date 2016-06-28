
public class SimpleDotComTestDrive {

	public static void main(String[] args) {
		int numOfGuess=0;
		GameHelper helper=new GameHelper();
		SimpleDotCom dot=new SimpleDotCom();  //创建棋局
		
		int randomNum=(int)(Math.random()*5);
		int[] locations={randomNum,randomNum+1,randomNum+2}; //随机摆放战舰
		boolean isAlive=true;             //棋局是否继续
		dot.setLocationCells(locations);
		
		while(isAlive==true)
		{
			String guess=helper.getUserInput("Enter a number"); //取得玩家输入
			String result=dot.checkYourself(guess);   //执行规则
			numOfGuess++;
			if(result.equals("kill"))
			{
				isAlive=false;
				System.out.println("You took "+numOfGuess+" guesses.");
			}
		}
		
	}

}
