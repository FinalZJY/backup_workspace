package EZ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	public File file;
	public FileWriter fw;
	public BufferedWriter bw;
	
	public Log(String name) throws IOException{
		file=new File(name);
		fw=new FileWriter(file);
		bw=new BufferedWriter(fw);
		
	}
	
	
	public void outputTurnInformation() throws IOException{
		bw.write("manager的操作:");
		bw.newLine();
		bw.write("回合数:"+TurnInformation.turnNum+" ");
		bw.newLine();
		bw.write("恢复期:"+TurnInformation.myRecoverRound+" ");
		bw.newLine();
		bw.write("武士状态:");
		bw.newLine();
		for(int i=0;i<TurnInformation.nowAllSamurai.size();i++){
			
		bw.write(TurnInformation.nowAllSamurai.get(i).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(i).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(i).state+"");
		bw.newLine();
		}
//		bw.write(TurnInformation.nowAllSamurai.get(1).col);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(1).row);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(1).state);
//		bw.newLine();
//		bw.write(TurnInformation.nowAllSamurai.get(2).col);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(2).row);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(2).state);
//		bw.newLine();
//		bw.write(TurnInformation.nowAllSamurai.get(3).col);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(3).row);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(3).state);
//		bw.newLine();
//		bw.write(TurnInformation.nowAllSamurai.get(4).col);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(4).row);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(4).state);
//		bw.newLine();
//		bw.write(TurnInformation.nowAllSamurai.get(5).col);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(5).row);
//		bw.write(" "+TurnInformation.nowAllSamurai.get(5).state);
//		bw.newLine();
		bw.write("战场:");
		bw.newLine();
		for(int m=0;m<GameIniInformation.heightOfBf;m++){
			for(int n=0;n<GameIniInformation.widthOfBf;n++){
				bw.write(TurnInformation.battleField[m][n]+" ");
			}
			bw.newLine();
		}
		
	}
	public void outputActions(String actions) throws IOException{
		bw.write("你的操作是:"+actions);
		bw.newLine();
	}
	
	public void outputGameInformation() throws IOException{
		bw.write("回合总数:"+GameIniInformation.totalRounds);
		bw.newLine();
		bw.write("队伍ID:"+GameIniInformation.teamID);
		bw.newLine();
		bw.write("武器:"+GameIniInformation.weapon);
		bw.newLine();
		bw.write("战场大小:"+"宽"+GameIniInformation.widthOfBf+" "+"高"+GameIniInformation.heightOfBf);
		bw.newLine();
		bw.write("恢复周期:"+GameIniInformation.recoverRound);
		bw.newLine();
		bw.write("大本营位置:");
		bw.newLine();
		for(int i=0;i<GameIniInformation.home.size();i++){
			bw.write(GameIniInformation.home.get(i).colOfHome+" "+GameIniInformation.home.get(i).rowOfHome);
			bw.newLine();
		}
	}
	
	public void close() throws IOException{
		bw.close();
		fw.close();
	}

}
