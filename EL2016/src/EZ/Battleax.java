package EZ;

import Battleax.BattleaxAi;

public class Battleax {
	private BattleaxAi battleaxAi;
	String actions="";//actions中保存末尾加0的最终actions
	public Battleax(){
		this.battleaxAi=new BattleaxAi();
	}
	public void battleaxAiRun(){
		if(TurnInformation.myRecoverRound!=0){  //先判断自己是否死亡
			actions="0";
		}
		else{
			battleaxAi.run();
			this.actions=battleaxAi.actions.trim()+" 0";
		}
	}
}
