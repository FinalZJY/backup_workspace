package EZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * Turn information of game
 * 存储每回合游戏信息：回合编号，恢复周期，武士状态，战场状态
 */

public class TurnInformation {
	
	public static int turnNum;//回合编号
	public static int myRecoverRound;//恢复周期
	public static List<Samurai> nowAllSamurai=new ArrayList<>();//武士状态
	public static int [][] battleField=new int[GameIniInformation.widthOfBf][GameIniInformation.heightOfBf];//战场状态
	public static void initi(){
		Samurai s=new Samurai(0, 0);
		for(int i=0;i<6;i++){
			nowAllSamurai.add(s);
		}
		
	}
	
	public static void SetTurnInformation(int[]a,int[][]b){//信息均以数组方式传递，地图以二位数组传递
		turnNum=a[0];
		myRecoverRound=a[1];	
		
			
		
		Samurai[] allSamurai={
				new Samurai(a[3],a[2],a[4],0,GameIniInformation.teamID),
				new Samurai(a[6],a[5],a[7],1,GameIniInformation.teamID),
				new Samurai(a[9],a[8],a[10],2,GameIniInformation.teamID),
				new Samurai(a[12],a[11],a[13],0,GameIniInformation.teamID==0?1:0),
				new Samurai(a[15],a[14],a[16],1,GameIniInformation.teamID==0?1:0),
				new Samurai(a[18],a[17],a[19],2,GameIniInformation.teamID==0?1:0),
		};
		
		for(int i=0;i<6;i++){
			nowAllSamurai.set(i,allSamurai[i]);	
		}
		
		
		for(int i=0;i<battleField.length;i++){
			for(int j=0;j<battleField[i].length;j++){
				battleField[i][j]=b[i][j];   
				
			}
		}
	}
}
