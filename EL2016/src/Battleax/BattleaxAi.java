package Battleax;

import java.util.ArrayList;
import java.util.Arrays;
import EZ.AI;
import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
import EZ.SimulateActions;
import EZ.TurnInformation;
/*斧头武士AI
 * v1.0
 * v2.0： 修改了逃跑方法和评分系统的接口
 * by 俊毅
 * 备注：尚未考虑有关大本营的所有情况
 */
public class BattleaxAi extends AI implements Cloneable {
	public  BattleaxAi() {
		samuraiID=GameIniInformation.samuraiID;
		teamID=GameIniInformation.teamID;
		weapon=GameIniInformation.weapon;
		me=TurnInformation.nowAllSamurai.get(weapon);
		
	}
	
	public void run(){                     //Battleax的AI开始计算
		if(turnNum>=totalRounds-6){//如果是最后一轮，强制执行评分算法
			actions="";
		}
		
		else if(canAttactEnemy()){   //如果能杀人，就杀
			if(me.state==1){  //如果隐身了，就先现形
				show();
			}
			for(int[] position:enemyPosition()){  //找到enemyPosition中第一个能杀的杀掉
				if(attact(position[0], position[1])){
					hide();
					break;
				}
				if(moveThenAttact(position[0], position[1])){
					hide();
					break;
				}
			}	
		}			
		
		else if(mustEscape()){
			escape();
		}
		
		else if (noFieldToOccupy()) {
			int fromCentre_row=minusMyCoordinate(7, 7)[0];
			int fromCentre_col=minusMyCoordinate(7, 7)[1];
			if(Math.abs(fromCentre_row)>Math.abs(fromCentre_col)){
				if(fromCentre_row>0){
					moveTo(me.row+3, me.col);	
				}
				else if (fromCentre_row<0) {
					moveTo(me.row-3, me.col);
				}
			}
			else {
				if(fromCentre_col>0){
					moveTo(me.row, me.col+3);
				}
				else if(fromCentre_col<0){
					moveTo(me.row, me.col-3);
				}
			}
			hide();
		}
		
		else if (canDoubleMove()) {
			SimulateActions SA=new SimulateActions();
			DoubleMove doubleMove=new DoubleMove(this);
			int[] step=doubleMove.bestMove(SA.AllActions());
			for(int i:step){
				if(i!=0){
					actions=actions+i+" ";
				}
			}
		}
		
		if(actions.length()==0){                    //其他情况采用评分算法
			SimulateActions SA=new SimulateActions();
			Grading grading=new Grading(this);
			int[] step=grading.maxScore(SA.AllActions());
			for(int i:step){
				if(i!=0){
					actions=actions+i+" ";
				}
			}
		}
	}

	public boolean canAttactEnemy(){                          //如果视野中有能攻击到的敌人，则返回true，否则false
		for(int[] position:enemyPosition()){
			for(Home home:allHome){              //如果这个格子是大本营，那么不可以杀人
				if(home.rowOfHome==position[0] && home.colOfHome==position[1]){
					return false;
				}
			}
			if(distantFromMe(position[0], position[1])<=3){  //position[0]是敌人的row，position[1]是敌人的col
				if(!(Math.abs(position[0]-me.row)==3 || Math.abs(position[1]-me.col)==3)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean attact(int row,int col) {                  //攻击(row,col)，成功则返回true，否则false
		if(minusMyCoordinate(row, col)[0]==1 && minusMyCoordinate(row, col)[1]>=-1 && minusMyCoordinate(row, col)[1]<=1){
			return attact("southward");
		}
		else if(minusMyCoordinate(row, col)[0]==-1 && minusMyCoordinate(row, col)[1]>=-1 && minusMyCoordinate(row, col)[1]<=1){
			return attact("northward");
		}
		else if(minusMyCoordinate(row, col)[1]==1 && minusMyCoordinate(row, col)[0]>=-1 && minusMyCoordinate(row, col)[0]<=1){
			return attact("eastward");
		}
		else if(minusMyCoordinate(row, col)[1]==-1 && minusMyCoordinate(row, col)[0]>=-1 && minusMyCoordinate(row, col)[0]<=1){
			return attact("westward");
		}
		else{
			return false;
		}
	}
	
	public boolean attact(int direction) {
		if(direction==1){
			return attact("southward");
		}
		else if (direction==2) {
			return attact("eastward");
		}
		else if (direction==3) {
			return attact("northward");
		}
		else if (direction==4) {
			return attact("westward");
		}
		return false;
	}
	
	public boolean attact(String direction) {
		if(!occupy(direction)){
			return false;
		}
		if(direction.equals("southward")){
			for(int row=Math.max(me.row-1, 0);row<=Math.min(me.row+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(me.col-1, 0);col<=Math.min(me.col+1, 14);col++){
					if((row==me.row)&&(col==me.col)){
						continue;
					}
					if((row==me.row-1)&&(col==me.col)){
						continue;
					}
					battleField[row][col]=weapon;
					for(Home home:allHome){              //如果这个格子是大本营，那么不可以改变所有者
						if(home.rowOfHome==row && home.colOfHome==col){
							battleField[row][col]=8;
						}
					}
				}
			}
			return true;
		}
		else if(direction.equals("eastward")){
			for(int row=Math.max(me.row-1, 0);row<=Math.min(me.row+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(me.col-1, 0);col<=Math.min(me.col+1, 14);col++){
					if((row==me.row)&&(col==me.col)){
						continue;
					}
					if((row==me.row)&&(col==me.col-1)){
						continue;
					}
					battleField[row][col]=weapon;
					for(Home home:allHome){              //如果这个格子是大本营，那么不可以改变所有者
						if(home.rowOfHome==row && home.colOfHome==col){
							battleField[row][col]=8;
						}
					}
				}
			}
			return true;
		}
		else if(direction.equals("northward")){
			for(int row=Math.max(me.row-1, 0);row<=Math.min(me.row+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(me.col-1, 0);col<=Math.min(me.col+1, 14);col++){
					if((row==me.row)&&(col==me.col)){
						continue;
					}
					if((row==me.row+1)&&(col==me.col)){
						continue;
					}
					battleField[row][col]=weapon;
					for(Home home:allHome){              //如果这个格子是大本营，那么不可以改变所有者
						if(home.rowOfHome==row && home.colOfHome==col){
							battleField[row][col]=8;
						}
					}
				}
			}
			return true;
		}
		else if(direction.equals("westward")){
			for(int row=Math.max(me.row-1, 0);row<=Math.min(me.row+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(me.col-1, 0);col<=Math.min(me.col+1, 14);col++){
					if((row==me.row)&&(col==me.col)){
						continue;
					}
					if((row==me.row)&&(col==me.col+1)){
						continue;
					}
					battleField[row][col]=weapon;
					for(Home home:allHome){              //如果这个格子是大本营，那么不可以改变所有者
						if(home.rowOfHome==row && home.colOfHome==col){
							battleField[row][col]=8;
						}
					}
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean moveThenAttact(int row,int col) {      //移动一步，再攻击，攻击完成就返回true，无法攻击就返回fasle。
		if(distantFromMe(row, col)>3){
			return false;
		}
		if(leftPoint>=6){
			for(int i=Math.max(row-1, 0);i<=Math.min(row+1, 14);i++){  //看看敌人的周围8个格子中能移动到哪个
				for(int j=Math.max(col-1, 0);j<=Math.min(col+1, 14);j++){
					if((i==row)&&(j==col)){                     //不移动到敌人那块地
						continue;
					}
					if(distantFromMe(i, j)<=1){
						moveTo(i, j);                         //移动到这
						attact(row, col);
						return true;
					}
					
				}
			}
		}
		else if(leftPoint>=4){
			return attact(row, col);
		}
		
		return false;
	}
	
	public boolean mustEscape() {    //如果下一步会被杀，就返回true，否则false
		int[] fromEnemySpear=minusMyCoordinate(allSamurai.get(3).row,allSamurai.get(3).col);
		int[] fromEnemySword=minusMyCoordinate(allSamurai.get(4).row,allSamurai.get(4).col);
		if(allSamurai.get(3).row>=0 && allSamurai.get(3).col>=0){
		    //逃离矛的攻击
			if(fromEnemySpear[0]==0){
				if((fromEnemySpear[1]>=-5 && fromEnemySpear[1]<=5)){
					return true;
				}
			}
			else if (fromEnemySpear[0]==-1 || fromEnemySpear[0]==1){
				if((fromEnemySpear[1]>=-4 && fromEnemySpear[1]<=4)){
					return true;
				}
			}
			else if((fromEnemySpear[0]>=2 && fromEnemySpear[0]<=4) || (fromEnemySpear[0]>=-4 && fromEnemySpear[0]<=-2)){
				if(fromEnemySpear[1]>=-1 && fromEnemySpear[1]<=1){
					return true;
				}
			}
			else if(fromEnemySpear[0]==5 || fromEnemySpear[0]==-5){
				if(fromEnemySpear[1]==0){
					return true;
				}
			}
		}
		if(allSamurai.get(4).row>=0 && allSamurai.get(4).col>=0){
             //逃离剑的攻击
			if(fromEnemySword[0]==0){
				if(fromEnemySword[1]>=-3 && fromEnemySword[1]<=3){
					return true;
				}
			}
			else if(fromEnemySword[0]==-1 || fromEnemySword[0]==1){
				if(fromEnemySword[1]>=-2 && fromEnemySword[1]<=2){
					return true;
				}
			}
			else if(fromEnemySword[0]==2 || fromEnemySword[0]==-2){
				if(fromEnemySword[1]>=-1 && fromEnemySword[1]<=1){
					return true;
				}
			}
			else if (fromEnemySword[0]==-3 || fromEnemySword[0]==3) {
				if(fromEnemySword[1]==0){
					return true;
				}
			}
		}
		if(distantFromMe(allSamurai.get(5).row, allSamurai.get(5).col)<=3){
			//逃离斧头的攻击
			if(allSamurai.get(5).row==me.row || allSamurai.get(5).col==me.col){
				if(Math.abs(allSamurai.get(5).row-me.row)==3 || Math.abs(allSamurai.get(5).col-me.col)==3){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean escape() {               //逃跑的方法
		String direction;  //随机产生一个方向逃跑，具体逃跑路径和方法可后续设置;
		ArrayList<String> directions=new ArrayList<String>();
		directions.addAll(Arrays.asList("southward","eastward","northward","westward"));
		while(directions.size()!=0){
			int i=(int)(Math.random()*directions.size());
			direction =directions.get(i);
			if(move(direction)){
				if(move(direction)){
					if(move(direction)){
						if(!mustEscape()){
							hide();                  //建议逃跑后隐藏
							return true;
						}
						cancelLastMove();
					}
					cancelLastMove();
				}
				cancelLastMove();
			}
			directions.remove(direction);
		}
		if(actions.length()==0){
			for(int i=5;i<=8;i++){
				for(int j=5;j<=8;j++){
					if(move(i)){
						if(move(j)){
							if(!mustEscape()){
								hide();                  //建议逃跑后隐藏
								return true;
							}
							cancelLastMove();
						}
						cancelLastMove();
					}
				}
			}
		}
		return false;
	}
	
	public boolean noFieldToOccupy() {
		int myField=0;
		int fieldAround=0;
		for(int row=Math.max(me.row-3,0);row<=Math.min(me.row+3,14);row++){
			for(int col=Math.max(me.col-3,0);col<=Math.min(me.col+3,14);col++){
				if(distantFromMe(row, col)<=3 && !(Math.abs(row-me.row)==3 || Math.abs(col-me.col)==3)){
					fieldAround++;
					if(battleField[row][col]>=0 && battleField[row][col]<=2){
						myField++;
					}
				}
			}
		}
		if(myField>=fieldAround-2){
			return true;
		}		
		return false;
	}
	
	public boolean canDoubleMove(){//能对对方斧头二连动并杀掉它就返回true，否则false
		int turn=turnNum;
		while(turn>11){ //先判断是不是二连动允许回合
			turn=turn-12;
		}
		if(turn<=5 && samuraiID==2){
			return false;
		}
		else if (turn>=6 && samuraiID==5) {
			return false;
		}
		
		if(allSamurai.get(5).row<0 || allSamurai.get(5).col<0){//再判断敌方斧头在不在我自己一个人视野内
			return false;
		}
		else if (allSamurai.get(5).row==allHome.get(5).rowOfHome && allSamurai.get(5).col==allHome.get(5).colOfHome) {
			return false;
		}
		else if (distantFromMe(allSamurai.get(5).row, allSamurai.get(5).col)>5) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public void setBattleField(int[][]battleField) {
		this.battleField=new int [battleField.length][battleField[0].length];
		for(int row=0;row<battleField.length;row++){
			for(int col=0;col<battleField[0].length;col++){
				this.battleField[row][col]=battleField[row][col];
			}
		}
	}
	
	public void setMe(Samurai me) {
		this.me=new Samurai(me.row, me.col, me.state, me.weapon, me.team);
	}
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
