
public class SimpleDotCom 
{
	private int[] locationCells;
	int numOfHits=0;
	public void setLocationCells(int[] loc)
	{
		locationCells=loc;
	}
	public String checkYourself(String stringGuess)
	{
		int guess=Integer.parseInt(stringGuess) ;
		String result="miss";
		for (int x=0;x<3;x++)
        {
            if (guess == locationCells[x]) 
            {
                result = "hit";
                numOfHits++;
                locationCells[x]=-1;
                break;
            }
        }
        if (numOfHits == locationCells.length)
        {
            result = "kill";
        }
        System.out.println(result);
        return result;
	}
}	
