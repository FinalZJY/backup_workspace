package Sword;

import EZ.GameIniInformation;
import EZ.Home;
import EZ.TurnInformation;
//此类用来保存棋盘中的每个格子的坐标以及值。
public class cell {
	public int row;
	public int col;
	public int value = -1;
	
	public void setValue(){
		
		if(row >=0 &&row<=14 && col>=0 && col<=14){
			value = TurnInformation.battleField[row][col];

		
			for(Home h:GameIniInformation.home){
				if(row == h.rowOfHome && col == h.colOfHome){
					value = -1;
				}
			}
		
		}
		
	}

}
