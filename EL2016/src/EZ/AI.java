package EZ;

import java.util.ArrayList;
import java.util.List;
/* 3个武士AI的父类，包括了一些通用的方法
 * 放在EZ包里
 * Ver1.1:  补上了不能在非自己领地隐身的考虑
 * Ver1.2:  补上了不能移动到敌人所在格子的考虑，修改使用TurnInformation里的信息
 * Ver2.0:  加上了大本营的考虑，调整了一些代码，使得方法里不会直接调用GameIniInformation类和TurnInformation类的数据
 * by 俊毅
 */

public class AI {
	public  AI() {
		
	}
	public int turnNum=TurnInformation.turnNum;
	public int totalRounds=GameIniInformation.totalRounds;
	protected int samuraiID;//武士ID                                //1.2版本修改的部分
	protected int teamID;
	protected int weapon;
	protected Samurai me;
	protected String actions="";//最终的武士行为
	protected int leftPoint=7;  //剩余行动力
	protected int[][] battleField=TurnInformation.battleField;
	protected List<Samurai> allSamurai=TurnInformation.nowAllSamurai;
	protected List<Home> allHome=GameIniInformation.home;
	
	public int getTeamID() {
		return teamID;
	}
	public Samurai getMe() {
		return me;
	}	
	public int getMyRow() {
		return me.row;
	}	
	public int getMyCol() {
		return me.col;
	}	
	public int getState() {
		return me.state;
	}
	public int getLeftPoint() {
		return leftPoint;
	}
	public int[][] getBattleField() {
		return battleField;
	}
	public List<Samurai> getAllSamurai() {
		return allSamurai;
	}
	public List<Home> getAllHome() {
		return allHome;
	}
	
	public void run(){                               //根据不同的子类自己覆盖，AI开始计算的主入口
		
	}
	
	public boolean occupy(int direction){
		if(direction==1){
			return occupy("southward");
		}
		else if (direction==2) {
			return occupy("eastward");
		}
		else if (direction==3) {
			return occupy("northward");
		}
		else if (direction==4) {
			return occupy("westward");
		}
		return false;
	}
	
	public boolean occupy(String direction){       //在行动中添加占领，成功就返回true，否则返回false
		if(leftPoint<4 || me.state!=0){
			return false;
		}
		if(direction.equals("southward")){
			actions=actions+"1 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("eastward")){
			actions=actions+"2 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("northward")){
			actions=actions+"3 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else if(direction.equals("westward")){
			actions=actions+"4 ";
			leftPoint=leftPoint-4;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean move(int direction) {          //1.2版本增加
		if(direction==5){
			return move("southward");
		}
		else if (direction==6) {
			return move("eastward");
		}
		else if (direction==7) {
			return move("northward");
		}
		else if (direction==8) {
			return move("westward");
		}
		return false;
	}
	
	public boolean move(String direction){         //在行动中添加移动，成功就返回true，否则返回false
		if(leftPoint<2){
			return false;
		}
		if((direction.equals("southward"))&&(me.row!=14)&&(!existSamurai(me.row+1,me.col))){//2.0版修改的部分
			if((battleField[me.row+1][me.col]>2)&&(me.state==1)){//不能隐身时移动到非己方格子
				return false;
			}
			for(Home home:allHome){                        //1.2.2新加，不能移动到别人的大本营
				if((me.row+1)==home.rowOfHome && me.col==home.colOfHome && !(me.team==home.teamID && me.weapon==home.weapon)){
					return false;
				}
			}
			actions=actions+"5 ";
			me.row++;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("eastward"))&&(me.col!=14)&&(!existSamurai(me.row,me.col+1))){//2.0版修改的部分
			if((battleField[me.row][me.col+1]>2)&&(me.state==1)){ //不能隐身时移动到非己方格子
				return false;
			}
			for(Home home:allHome){                        //1.2.2新加，不能移动到别人的大本营
				if(me.row==home.rowOfHome && (me.col+1)==home.colOfHome && !(me.team==home.teamID && me.weapon==home.weapon)){
					return false;
				}
			}
			actions=actions+"6 ";
			me.col++;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("northward"))&&(me.row!=0)&&(!existSamurai(me.row-1,me.col))){//2.0版修改的部分
			if((battleField[me.row-1][me.col]>2)&&(me.state==1)){//不能隐身时移动到非己方格子
				return false;
			}
			for(Home home:allHome){                        //1.2.2新加，不能移动到别人的大本营
				if((me.row-1)==home.rowOfHome && me.col==home.colOfHome && !(me.team==home.teamID && me.weapon==home.weapon)){
					return false;
				}
			}
			actions=actions+"7 ";
			me.row--;
			leftPoint=leftPoint-2;
			return true;
		}
		else if((direction.equals("westward"))&&(me.col!=0)&&(!existSamurai(me.row,me.col-1))){//2.0版修改的部分
			if((battleField[me.row][me.col-1]>2)&&(me.state==1)){//不能隐身时移动到非己方格子
				return false;
			}
			for(Home home:allHome){                        //1.2.2新加，不能移动到别人的大本营
				if(me.row==home.rowOfHome && (me.col-1)==home.colOfHome && !(me.team==home.teamID && me.weapon==home.weapon)){
					return false;
				}
			}
			actions=actions+"8 ";
			me.col--;
			leftPoint=leftPoint-2;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean hide(){                         //在行动中添加隐身，成功就返回true，否则返回false
		if(leftPoint<1 || me.state!=0){
			return false;
		}
		if(battleField[me.row][me.col]<=2 && !existEnemy(me.row,me.col)){  //2.0版修改的部分
			actions=actions+"9 ";
			me.state=1;
			leftPoint=leftPoint-1;
			return true;
		}
		return false;
	}
	
	public boolean show(){                         //在行动中添加现身，成功就返回true，否则返回false
		if(leftPoint<1 || me.state!=1 || existSamurai(me.row,me.col)){
			return false;
		}
		actions=actions+"10 ";
		me.state=0;
		leftPoint=leftPoint-1;
		return true;
	}
	
	public boolean canAttactEnemy(){    //根据不同的子类自己覆盖
		
		return false;
	}
	
	public boolean existEnemy(){         //己方武士视野中有敌人就返回true，否则false，恢复中的敌人（state==-1）当成没看见
		for(Samurai enemy:allSamurai){
			if(enemy.state==0 && enemy.team!=me.team){
				return true;
			}
		}
		return false;
	}
	public boolean existEnemy (int row,int col) {             //1.2版本添加，(row,col)中有敌人就返回true，否则false
		for(Samurai enemy:allSamurai){
			if(enemy.team!=me.team && enemy.row==row && enemy.col==col && enemy.state!=1){
				return true;
			}
		}
		return false;
	}
	
	public boolean existSamurai (int row,int col) {       //2.0版本添加
		for(Samurai samurai:allSamurai){
			if(samurai.row==row && samurai.col==col && samurai.state!=1){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<int[]> enemyPosition(){ //将看见的敌人的位置放进positions里传回，int[0]是row，int[1]是col
		ArrayList<int[]> positions=new ArrayList<int[]>();
		for(Samurai enemy:allSamurai){
			if(enemy.state==0 && enemy.team!=me.team){   //恢复中的敌人（state==-1）当成没看见
				int[] position ={enemy.row,enemy.col};
				positions.add(position);
			}
		}
		return positions;
	}
	
	public int distantFromMe(int row,int col){   //返回(row,col)距离我这个武士的曼哈顿距离
		return Math.abs(me.row-row)+Math.abs(me.col-col);
	}
	
	public int[] minusMyCoordinate(int row,int col) { //目标的坐标减我这个武士的坐标，返回记录了坐标差的数组
		int[] result={row-me.row,col-me.col};
		return result;
	}
	
	public boolean moveTo(int row,int col){        //在行动中添加移动到(row,col)的步骤，成功就返回true，否则返回false
		if(leftPoint<distantFromMe(row, col)*2){   
			return false;
		}
		
		if((me.row==row)||(me.col==col)){          //1.1版修改的部分
			int steps=0;
			while(me.row>row){
				move("northward");
				steps++;
				if(steps>3){
					return false;
				}
			}
			while(me.row<row){
				move("southward");
				steps++;
				if(steps>3){
					return false;
				}
			}
			while(me.col>col){
				move("westward");
				steps++;
				if(steps>3){
					return false;
				}
			}
			while(me.col<col){
				move("eastward");
				steps++;
				if(steps>3){
					return false;
				}
			}
			return true;
		}
		else if(Math.abs(me.row-row)==1 && Math.abs(me.col-col)==1){
			String xDirection="";
			String yDirection="";
			if(me.row==row+1){
				xDirection="northward";
			}
			else if(me.row==row-1){
				xDirection="southward";
			}
			if(me.col==col+1){
				yDirection="westward";
			}
			else if(me.row==row-1){
				yDirection="eastward";
			}
			
			if(move(xDirection)){
				if(move(yDirection)){
					return true;
				}
				else {
					cancelLastMove();
				}
			}
			if (move(yDirection)) {
				if(move(xDirection)){
					return true;
				}
				else {
					cancelLastMove();
				}
			}
			return false;
		}
		
		return false;
	}
	
	public boolean cancelLastMove() {              //2.1版修改的部分  //删掉上一个move，成功返回true，否则false
		if(actions=="" || actions==" "){
			return false;
		}
		String[] newActions=actions.split(" ");
		actions="";
		for(int i=0;i<newActions.length;i++){
			if(i!=newActions.length-1){
				actions=actions+newActions[i]+" ";
			}
			else if (i==newActions.length-1) {
				switch(newActions[i]){
					case"5":me.row--;break;
					case"6":me.col--;break;
					case"7":me.row++;break;
					case"8":me.col++;break;
					default:return false;
				}
				
			}
		}
		leftPoint=leftPoint+2;
		return true;
	}
}
