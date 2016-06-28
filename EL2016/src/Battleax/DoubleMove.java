package Battleax;

import java.util.ArrayList;
//二连动算法类
public class DoubleMove {
	private BattleaxAi battleaxAi;
	private BattleaxAi battleaxAi_backup;
	
	public DoubleMove(BattleaxAi battleaxAi){
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
	
	public int[] bestMove(ArrayList<int[]> allActions) {                
		ArrayList<int[]> feasibleActions=new ArrayList<int[]>();//将第一步走完后第二步能杀斧头的行动装进feasibleActions
		for(int[] actions:allActions){
			if(feasible(actions)){
				feasibleActions.add(actions);
			}
			this.battleaxAi=cloneBattleaxAi(battleaxAi_backup);
			this.battleaxAi.setBattleField(battleaxAi_backup.getBattleField());
			this.battleaxAi.setMe(battleaxAi_backup.getMe());
		}
		
		Grading grading=new Grading(battleaxAi);
		int[] step=grading.maxScore(feasibleActions);
		
		return step;
	}
	
	public boolean feasible(int[] actions) {
		
		for(int action:actions){
			if(action==0){
				continue;
			}
			else if(action>=1 && action<=4){
				if(battleaxAi.attact(action)){
					
				}
				else{
					
				}
			}
			else if (action>=5 && action<=8) {
				if(battleaxAi.move(action)){
					
				}
				else{
					return false;
				}
			}
			else if (action==9) {
				if(battleaxAi.hide()){
					
				}
				else{
					return false;
				}
			}
			else if (action==10) {
				if(battleaxAi.show()){
					
				}
				else{
					return false;
				}
			}	
			else{
				return false;
			}		
		}
		if(battleaxAi.canAttactEnemy() && !mustEscape()){
			return true;
		}
		return false;
	}
	
	public boolean mustEscape() {
		int[] fromEnemySpear=battleaxAi.minusMyCoordinate(battleaxAi.getAllSamurai().get(3).row,battleaxAi.getAllSamurai().get(3).col);
		int[] fromEnemySword=battleaxAi.minusMyCoordinate(battleaxAi.getAllSamurai().get(4).row,battleaxAi.getAllSamurai().get(4).col);
		if(battleaxAi.getAllSamurai().get(3).row>=0 && battleaxAi.getAllSamurai().get(3).col>=0){
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
		if(battleaxAi.getAllSamurai().get(4).row>=0 && battleaxAi.getAllSamurai().get(4).col>=0){
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
		return false;
	}
}
