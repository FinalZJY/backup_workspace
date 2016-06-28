package EZ;

import Spear.SpearAi;

public class Spear {
	private SpearAi spearAi;
	public Spear(){//构造方法
		int teamID=GameIniInformation.teamID;
		this.spearAi=new SpearAi(teamID);// new a spear base on teamID
	}
	
	public void spearAiRun(){//运行spearai	
        if(spearAi.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
				System.out.println("0");
		}
        else {		
        	spearAi.type=spearAi.onClothes();//analyse his type1234
        	if(TurnInformation.turnNum<45){//when the turnnum is smaller than 30
        		spearAi.iniAction();
        	}else{
        		spearAi.action();
        	}
	
			
			if (spearAi.energy>=1&&spearAi.state==0&&spearAi.canHide()) {
				spearAi.order=spearAi.order+"9 ";
				spearAi.energy-=1;
			}
			System.out.println(spearAi.order+"0");//输出指令到控制台，manager自己读取命令
		}
	}
}
