package Sword;

import java.util.ArrayList;
import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
import EZ.TurnInformation;

//对攻击距离分析，当剑无法攻击到时，只有敌方矛有可能攻击到剑，因此逃离方法中只分析矛的情况
//方向即是示例中的指令，1~4表示占领方向，5~8表示移动方向
//个人认为体力限制可通过执行完之后分析action或者在分析方法中实现，以下方法中没有具体的实现
//对于棋盘边界对于移动和占领的限制，只使用了if语句进行部分处理，还没有想出很完美的方法
public class NecessaryAct {
	public  String action="";  //行动指令
	public String whichAction = "";
	public static int strength;   //体力限制
	Samurai Sword,emSpear,emSword,emBattlex; //剑，敌方矛
	int intiCol;
	int intiRow;
	public boolean isKill = true;
	int myTeam=GameIniInformation.teamID;
	boolean canKill = false;
	public NecessaryAct(){ //构造方法，开始时得到剑和敌方矛的信息
		for(Samurai samurai:TurnInformation.nowAllSamurai){
			if(samurai.weapon == 1 && samurai.team==myTeam){  //我自行在Samurai里添加的ID，便于区分武士
				Sword=samurai;
			    
			}
			if(samurai.weapon == 0 && samurai.team!=myTeam){
				emSpear = samurai;
			}
			if(samurai.weapon == 1 && samurai.team!=myTeam){
				emSword = samurai;
			}
			if(samurai.weapon == 2 && samurai.team!=myTeam){
				emBattlex = samurai;
			}
		}
		 intiCol = Sword.col;  //保存回合开始时的位置
		 intiRow = Sword.row;
	}
	
	public ArrayList<cell> Occupy(int direction){ //传入方向，返回占领的棋盘数组
		
		
		ArrayList<cell> occupyList = new ArrayList<cell>();
		
			for(int i=1;i<=2;i++){
				if(direction==4 || direction == 3){
					cell C = new cell();
					if(Sword.col-i>=0){
					C.col = Sword.col - i;
					C.row = Sword.row;
					C.setValue();
					occupyList.add(C);
					}
				}
				if(direction == 2 || direction == 1){
					cell C = new cell();
					if(Sword.col+i>=0){
					C.col = Sword.col + i;
					C.row = Sword.row;
					C.setValue();
					if(C.col >=0 && C.col<=14 && C.row>=0 && C.row <=14)
					occupyList.add(C);
					}
				}
				if(direction == 1 || direction == 4){
					cell C = new cell();
					if(Sword.row+i>=0){
					C.col = Sword.col;
					C.row = Sword.row + i;
					C.setValue();
					if(C.col >=0 && C.col<=14 && C.row>=0 && C.row <=14)
					occupyList.add(C);
					}
				}
				if(direction == 2 || direction == 3){
					cell C = new cell();
					if(Sword.row-i>=0){
					C.col = Sword.col;
					C.row = Sword.row - i;
					C.setValue();
					if(C.col >=0 && C.col<=14 && C.row>=0 && C.row <=14)
					occupyList.add(C);
					}
				}
			}
			
				cell C1 = new cell();
				//cell C2 = new cell();
				//cell C3 = new cell();
				if(direction == 4){
					
					C1.col = Sword.col-1;
					C1.row = Sword.row+1;
					//C2.col = Sword.col-1;
					//C2.row = Sword.row+2;
					//C3.col = Sword.col-2;
					//C3.row = Sword.row+1;
				}
				else if(direction == 2){
					C1.col = Sword.col+1;
					C1.row = Sword.row-1;
					//C1.col = Sword.col+2;
					//C1.row = Sword.row-1;
					//C1.col = Sword.col+1;
					//C1.row = Sword.row-2;
				}
				else if(direction == 3){
					C1.col = Sword.col-1;
					C1.row = Sword.row-1;
					//C2.col = Sword.col-1;
					//C2.row = Sword.row-2;
					//C3.col = Sword.col-2;
					//C3.row = Sword.row-1;
				}
				else if(direction == 1){
					C1.col = Sword.col+1;
					C1.row = Sword.row+1;
					//C2.col = Sword.col+1;
					//C2.row = Sword.row+2;
					//C3.col = Sword.col+2;
					//C3.row = Sword.row+1;
				}
				C1.setValue();
				//C2.setValue();
				//C3.setValue();
				if(C1.col>=0 && C1.row>=0 && C1.col <=14 && C1.row<=14)
					occupyList.add(C1);
				/*if(C2.col>=0 && C2.row>=0 && C2.col <=14 && C2.row<=14)
					occupyList.add(C2);
				if(C3.col>=0 && C3.row>=0 && C3.col <=14 && C3.row<=14)
					occupyList.add(C3);*/
				
			
		
		return occupyList;
		
	}
	public int ValueOfOccupy(ArrayList<cell> cells){ //占领的棋盘中可以加分的数目
		int value = 0;
		for(cell c:cells){
			if(c.value == 8||c.value == 9||c.value == 3 || c.value == 4 || c.value ==5){
			value++;	
			}
		}
		if(MustEscape() && TurnInformation.turnNum<185){
			value = -1;
		}
		int Swordcol = Math.abs(Sword.col - emSword.col);
		int Swordrow = Math.abs(Sword.row - emSword.row);
		int Battlexcol = Math.abs(Sword.col - emBattlex.col);
		int Battlexrow = Math.abs(Sword.row - emBattlex.row);
		
		//if((Battlexcol <=2 && Battlexrow <= 1) || (Battlexcol <= 1 && Battlexrow <= 2) || (Swordcol <= 2 && Swordrow ==1) || (Swordcol == 1&& Swordrow <=2)|| (Swordcol <= 3 &&Swordrow == 0)||(Swordcol == 0 && Swordrow <=3)){
			//if(TurnInformation.turnNum<185)
			//value = -1;
		//}
		return value;
	}
	public int Move(int direction){   //移动方法
		
		
		switch (direction) {
		case 5:
			
			Sword.row++;
			break;
		case 6:
			Sword.col++;
			break;
		case 7:
			Sword.row--;
			break;
		case 8:
			Sword.col--;
			break;

		default:
			break;
		}
		for(Home h:GameIniInformation.home){
			if((Sword.row == h.rowOfHome && Sword.col == h.colOfHome)){
				Sword.row = intiRow;
				Sword.col = intiCol;
				break;
			}
		}
		for(Samurai s:TurnInformation.nowAllSamurai){
			if(Sword.row == s.row && Sword.col == s.col && Sword.col !=intiCol && Sword.row !=intiRow && s.state == 0){
				Sword.row = intiRow;
				Sword.col = intiCol;
				break;
			}
		}
		if(!isKill){
		if(MustEscape()){
			Sword.row = intiRow;
			Sword.col = intiCol;
		}
		int Swordcol = Math.abs(Sword.col - emSword.col);
		int Swordrow = Math.abs(Sword.row - emSword.row);
		int Battlexcol = Math.abs(Sword.col - emBattlex.col);
		int Battlexrow = Math.abs(Sword.row - emBattlex.row);
		
		if((Battlexcol <=2 && Battlexrow <= 1) || (Battlexcol <= 1 && Battlexrow <= 2) || (Swordcol <= 2 && Swordrow ==1) || (Swordcol == 1&& Swordrow <=2)|| (Swordcol <= 3 &&Swordrow == 0)||(Swordcol == 0 && Swordrow <=3)){
			Sword.row = intiRow;
			Sword.col = intiCol;
		}
		}
		return 2;
	}
	public int Hide(){  //隐身方法
		action = action + "9 ";
		Sword.state = 1;
		return 1;
	}
	public int Show(){  //现身方法
		action = action + "10 ";
		Sword.state = 0;
		return 1;
	}
	public int Kill(){  //击杀方法，若能击杀，返回方向，若不能，返回-1
		for(int i=1;i<=4;i++){
			ArrayList<cell> temp = this.Occupy(i);
			for(cell c:temp){
				for(Samurai s:TurnInformation.nowAllSamurai){
					if(c.col == s.col && c.row == s.row && s.team != myTeam && !isHome(s)){
						return i;
					}
				}
			}
			
		}
		return 0;
	}
	public int MustKill(){//必杀方法，尝试行动一步或者不动的击杀，若能返回true，不能返回false
		isKill = true;
		if(Kill()!=0){  //原地能击杀
			Show();
			Occupy(Kill());
			action = action+Kill()+" ";
			Hide();
			whichAction = whichAction +"kill";
			
			
			return 1;
		}
		else{  //行动后击杀
		for(int i=5;i<=8;i++){ 
				Move(i);  //尝试移动
				if(Kill()!=0){
					//energy = energy + Move(i)+4;
					if(Sword.state == 1) //若隐身，首先现身
						Show();
					Occupy(Kill());
					action = action + i+" ";
					action = action+Kill()+" ";
					Hide();
					whichAction = whichAction+"movekill";
					return 1;
				
			}
			Sword.col = intiCol; //恢复初始位置，重新尝试
			Sword.row = intiRow;
		}
		}
		return 0;
		
	}
	public boolean MustEscape(){  //判断是否会被敌方矛击杀
		int colDis = Math.abs(Sword.col-emSpear.col);//与敌方矛之间的距离
		int rowDis = Math.abs(Sword.row-emSpear.row);
		

		if((colDis<=4 && rowDis<=1) || (rowDis<=4 && colDis<=1)||(colDis <= 5 && rowDis==0)||(rowDis<=5 && colDis==0) || (rowDis <= 3 && colDis==1) ||(rowDis ==1 && colDis<=3)){
			return true;
		}
		else {
			return false;
		}
	}
	public boolean Escape(){
		isKill=true;
		if(TurnInformation.turnNum >=185)
			return false;//逃跑方法
		if(MustEscape()){
			
			moveToKill();
			
			if(!canKill){
			int i;
			int j;
			int k;
			for(;;){
				
				j = (int)(4+Math.random()*5);//随机产生三个4——8的整数，4表示不走，4——8表示方向，从而达到走一到三步的目的。
				k = (int)(4+Math.random()*5);
				i = (int)(4+Math.random()*5); 
				if(i!=4)
				Move(i);
				if(j!=4)
					Move(j);
				if(k!=4)
					Move(k);
				
				if(!MustEscape()){
					if(i!=4){
					action = action+i+" ";
					whichAction = whichAction + "escape";
					}
					if(k!=4){
						action = action+k+" ";
					    whichAction = whichAction + "escape";
					}
					if(j!=4){
						action = action +j+" ";
						whichAction = whichAction + "escape";
					}
					Hide();   //建议逃跑后隐藏
					return true;
				}
				Sword.col=intiCol;
				Sword.row=intiRow;
			}
			}
		}
		if(canKill)
			return true;
		return false;
	}
	
	public void ShouldOccupy(){ 
		isKill = false;
		boolean notMove = false;
		for(Samurai s:TurnInformation.nowAllSamurai){
			if(Math.abs(s.col-Sword.col) == 2 && Math.abs(s.row - Sword.row)==2){
				notMove = true;
				break;
			}
		}
		
		int [][]Va =new int[5][4];  //Va储存不同行动后的收益，共20种
		for(int i=0;i<=4;i++){
			for(int j=0;j<=3;j++){
				if(i==0){  //不移动，直接占领的收益
					Va[0][j]=ValueOfOccupy(Occupy(j+1));
					if(notMove){
						Va[0][j] = Va[0][j] +1;
					}
				}
				else if(notMove){
					Va[i][j] =-1;
				}
				else{
					Move(i+4);  //移动后占领的收益
					Va[i][j]=ValueOfOccupy(Occupy(j+1));
					Sword.col = intiCol; //恢复初始位置，重新尝试
					Sword.row = intiRow;
				}
			}
		}
		int max =Va[0][0];  //以下为取最大值算法，同时取出数组下标
		int Xmax =0;
		int Ymax =0;
		for(int a=0;a<=4;a++){
			for(int b=0;b<=3;b++){
				if(Va[a][b]>max){
					max=Va[a][b];
					Xmax = a;
					Ymax = b;
				}
			}
		}
		if(Va[Xmax][Ymax]==0){
			//action = action + ((int)(Math.random()*4)+5)+" "+((int)(Math.random()*4)+5)+" "+((int)(Math.random()*4)+5)+" ";
			moveToCentre();
			Hide();
		}
		else if(Xmax ==0 ){
			if(Sword.state == 1) //若隐身，首先现身
				Show();//在不需要逃跑和击杀时，发育的方法
			action = action + (Ymax+1)+" ";
			Hide();
			whichAction = whichAction + "occupy";
		}
		else {
			if(Sword.state == 1) //若隐身，首先现身
				Show();//在不需要逃跑和击杀时，发育的方法
			action = action + (Xmax+4)+" " + (Ymax+1)+" ";
			Hide();
			whichAction = whichAction + "Moveoccupy";
		}
		
	}
	public void moveToCentre(){
		
		
		if(Sword.col<7){
			action = action + "6 ";
		}
		if(Sword.col>=7){
			action = action + "8 ";
		}
		if(Sword.row<7){
			action = action + "5 ";
		}
		if(Sword.row>=7){
			action = action + "7 ";
		}
		if(Sword.col<7){
			action = action + "6 ";
		}
		else if(Sword.col>=7){
			action = action + "8 ";
		}
		else if(Sword.row<7){
			action = action + "5 ";
		}
		else if(Sword.row>=7){
			action = action + "7 ";
		}
		
	}
	public boolean isHome(Samurai sa){
		for(Home h:GameIniInformation.home){
			if(sa.col == h.colOfHome && sa.row == h.rowOfHome){
				return true;
			}
		}
		return false;
	}
	public void moveToKill(){
		int i=0,j = 0,k = 0;
		labela:
		for(i=4;i<=8;i++){
			for(j=4;j<=8;j++){
				for(k=4;k<=8;k++){
					if(i!=4)
						Move(i);
					if(j!=4)
						Move(j);
					if(k!=4)
						Move(k);
					int colDis = Math.abs(Sword.col-emSpear.col);//与敌方矛之间的距离
					int rowDis = Math.abs(Sword.row-emSpear.row);

					if(colDis == 2 && rowDis == 2){
						canKill = true;
						break labela;
					}
					Sword.col = intiCol; //恢复初始位置，重新尝试
					Sword.row = intiRow;
				}
			}
		}
		if(canKill){
		if(i!=4){
			action = action+i+" ";
			whichAction = whichAction + "escape";
			}
			if(k!=4){
				action = action+k+" ";
			    whichAction = whichAction + "escape";
			}
			if(j!=4){
				action = action +j+" ";
				whichAction = whichAction + "escape";
			}
			Hide();
		}
	}

}
