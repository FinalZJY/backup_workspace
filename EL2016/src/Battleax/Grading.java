package Battleax;
import java.util.ArrayList;

import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
/*斧头武士的评分系统
 * v1.0
 * v2.0: 调整了代码风格，不直接调用GameIniInformation类和TurnInformation类的数据
 * by 俊毅
 */
//注意：分数为0表示这个action不成立
public class Grading {
	
	private BattleaxAi battleaxAi;
	private BattleaxAi battleaxAi_backup;
	
	public Grading(BattleaxAi battleaxAi){
		battleaxAi_backup=battleaxAi;
		this.battleaxAi=cloneBattleaxAi(battleaxAi_backup);
		this.battleaxAi.setBattleField(battleaxAi_backup.getBattleField());
		this.battleaxAi.setMe(battleaxAi_backup.getMe());
	}
	
	public BattleaxAi cloneBattleaxAi(BattleaxAi battleaxAi) {
		BattleaxAi newBattleaxAi=new BattleaxAi();
		try{
			newBattleaxAi=(BattleaxAi)battleaxAi.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newBattleaxAi;
	}
	
	public int[] maxScore(ArrayList<int[]> allActions) {                //将最高分步骤以int数组返回
		ArrayList<StepAndScore> steps=new ArrayList<StepAndScore>();
		for(int[] action:allActions){
			steps.add(new StepAndScore(action,getScore(action)));
			this.battleaxAi=cloneBattleaxAi(battleaxAi_backup);
			this.battleaxAi.setBattleField(battleaxAi_backup.getBattleField());
			this.battleaxAi.setMe(battleaxAi_backup.getMe());
		}
		
		if(steps.size()==0){
			return new int[]{0};
		}
		StepAndScore maxStep=steps.get(0);
		for(StepAndScore step:steps){
			if(step.score>maxStep.score){
				maxStep=step;
			}
			else if (step.score==maxStep.score) {//如果分数相同，随机取一个
				int i=(int)(Math.random()*2);
				maxStep= i==0?maxStep:step;
			}
		}
		return maxStep.step;
	}
	
	public int getScore(int[] actions) {
		int kill=0;
		int score=0;
		
		for(int action:actions){
			if(action==0){
				continue;
			}
			else if(action>=1 && action<=4){
				if(battleaxAi.attact(action)){
					for(int[]i:newFieldAfterOccupy(action)){
						kill=kill+isKill(i[0], i[1]);
					}
				}
				else{
					return 0;
				}
			}
			else if (action>=5 && action<=8) {
				if(battleaxAi.move(action)){
					
				}
				else{
					return 0;
				}
			}
			else if (action==9) {
				if(battleaxAi.hide()){
					
				}
				else{
					return 0;
				}
			}
			else if (action==10) {
				if(battleaxAi.show()){
					
				}
				else{
					return 0;
				}
			}
		
			else{
				return 0;
			}
			
		}
		
		int riskFromEnemyHome=1000;    //暂定回合结束时靠近敌方大本营距离3以内减1000分
		int riskFactor=0;//危险系数：周围有可能出现原来是隐身的敌人攻击我，这样的可能的敌人所在的格子，每有一个危险系数+1
		
		score=score+kill*10000;                            //暂定杀人加10000分
		for(int[] i:battleaxAi.getBattleField()){
			for(int j:i){
				if(j>=0 && j<=2){
					score=score+600;                      //暂定每有一块地加600分
					if(battleaxAi.turnNum>=battleaxAi.totalRounds-6){
						score=score+3000;                //暂定最后一回合每有一块地再加3000分
					}
				}
			}
		}
		for(int i=Math.max(battleaxAi.getMyRow()-1, 0);i<=Math.min(battleaxAi.getMyRow()+1, 14);i++){
			for(int j=Math.max(battleaxAi.getMyCol()-1, 0);j<=Math.min(battleaxAi.getMyCol()+1, 14);j++){
				if(battleaxAi.getBattleField()[i][j]>2){
					score=score+400;                   //暂定周围格子中每有一块不是自己的地盘就加400分
				}
			}
		}
		if(battleaxAi.mustEscape()){
			if(battleaxAi.getState()==0){
				score=score-9000;                        //暂定回合结束时会被杀减9000分
			}
			else if (battleaxAi.getState()==1) {
				score=score-700;
			}
			
		}
		if(battleaxAi.turnNum>GameIniInformation.totalRounds*2/3){//后期偷家加分
			if(distanceFromHome(battleaxAi.getAllSamurai().get(0))<=4 && distanceFromHome(battleaxAi.getAllSamurai().get(1))<=8){
				int x=Math.abs((battleaxAi.getTeamID()==0?0:14)-battleaxAi.getMyRow());//南北方向上与边界的差
				int y=Math.abs((battleaxAi.getTeamID()==0?14:0)-battleaxAi.getMyCol());//东西方向上与边界的差
				if(x<=5){
					score=score+160*x*(5-x);//加分函数为f(x)=160x(5-x)
				}
				else if(y<=5){
					score=score+160*y*(5-y);//加分函数为f(x)=160x(5-x)
				}
				riskFromEnemyHome=0;
			}
		}
		for(Home home:battleaxAi.getAllHome()){
			if(home.teamID==battleaxAi.getTeamID()){
				continue;
			}
			if(battleaxAi.distantFromMe(home.rowOfHome, home.colOfHome)<=3){
				score=score-riskFromEnemyHome;        //暂定回合结束时靠近敌方大本营距离3以内减1000分
				break;
			}
		}
		for(int row=Math.max(battleaxAi.getMyRow()-5, 0);row<=Math.min(battleaxAi.getMyRow()+5, 14);row++){
			for(int col=Math.max(battleaxAi.getMyCol()-5, 0);col<=Math.min(battleaxAi.getMyCol()+5, 14);col++){
				if(row==battleaxAi.getMyRow()){
					if(battleaxAi.getBattleField()[row][col]>=3 && battleaxAi.getBattleField()[row][col]<=5){
						riskFactor=riskFactor+1;//周围有可能出现原来是隐身的敌人攻击我，这样的可能的敌人所在的格子，每有一个危险系数+1
					}
				}
				else if (row==battleaxAi.getMyRow()-1 || row==battleaxAi.getMyRow()+1){
					if(col>=battleaxAi.getMyCol()-4 && col<=battleaxAi.getMyCol()+4){
						if(battleaxAi.getBattleField()[row][col]>=3 && battleaxAi.getBattleField()[row][col]<=5){
							riskFactor=riskFactor+1;
						}
					}
				}
				else if((row>=battleaxAi.getMyRow()+2 && row<=battleaxAi.getMyRow()+4) || (row>=battleaxAi.getMyRow()-4 && row<=battleaxAi.getMyRow()-2)){
					if(col>=battleaxAi.getMyCol()-1 && col<=battleaxAi.getMyCol()+1){
						if(battleaxAi.getBattleField()[row][col]>=3 && battleaxAi.getBattleField()[row][col]<=5){
							riskFactor=riskFactor+1;
						}
					}
				}
				else if(row==battleaxAi.getMyRow()+5 || row==battleaxAi.getMyRow()-5){
					if(col==battleaxAi.getMyCol()){
						if(battleaxAi.getBattleField()[row][col]>=3 && battleaxAi.getBattleField()[row][col]<=5){
							riskFactor=riskFactor+1;
						}
					}
				}
			}
		}
		score=score-riskFactor*30;//暂定回合结束时每个危险系数减30分
		if(battleaxAi.getState()==1){
			score=score+100+riskFactor*40;                             //暂定回合结束时隐身加分策略
		}
		return score;
	}
	
	public ArrayList<int[]> newFieldAfterOccupy(int direction) {
		if(direction==1){
			return newFieldAfterOccupy("southward");
		}
		else if (direction==2) {
			return newFieldAfterOccupy("eastward");
		}
		else if (direction==3) {
			return newFieldAfterOccupy("northward");
		}
		else if (direction==4) {
			return newFieldAfterOccupy("westward");
		}
		return null;
	}
	
	public ArrayList<int[]> newFieldAfterOccupy(String direction){
		ArrayList<int[]> field=new ArrayList<int[]>();
		if(direction.equals("southward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){//棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow()-1)&&(col==battleaxAi.getMyCol())){
						continue;
					}
					for(Home home:battleaxAi.getAllHome()){              
						if(home.rowOfHome==row && home.colOfHome==col){
							continue;
						}
					}
					field.add(new int[]{row,col});
				}
			}
		}
		else if(direction.equals("eastward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol()-1)){
						continue;
					}
					for(Home home:battleaxAi.getAllHome()){              
						if(home.rowOfHome==row && home.colOfHome==col){
							continue;
						}
					}
					field.add(new int[]{row,col});
				}
			}
		}
		else if(direction.equals("northward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow()+1)&&(col==battleaxAi.getMyCol())){
						continue;
					}
					for(Home home:battleaxAi.getAllHome()){             
						if(home.rowOfHome==row && home.colOfHome==col){
							continue;
						}
					}
					field.add(new int[]{row,col});
				}
			}
		}
		else if(direction.equals("westward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol()+1)){
						continue;
					}
					for(Home home:battleaxAi.getAllHome()){              
						if(home.rowOfHome==row && home.colOfHome==col){
							continue;
						}
					}
					field.add(new int[]{row,col});
				}
			}
		}
		return field;
	}
	
	public int isKill(int i,int j) {                 //若(i,j)有敌人且能被杀死，则杀敌数+1且返回true，否则false
		for(Samurai enemy:battleaxAi.getAllSamurai()){
			if(enemy.team!=battleaxAi.getMe().team && enemy.row==i && enemy.col==j && enemy.state==0){ //恢复中的敌人（enemy.state==-1）当成没看见
				return 1;
			}
		}
		return 0;
	}

	public int distanceFromHome(Samurai samurai) {//返回该武士距离自己大本营的距离
		Home home=battleaxAi.getAllHome().get(samurai.weapon+(samurai.team==battleaxAi.getTeamID()?0:3));
		return Math.abs(home.rowOfHome-samurai.row)+Math.abs(home.colOfHome-samurai.col);
	}
}
