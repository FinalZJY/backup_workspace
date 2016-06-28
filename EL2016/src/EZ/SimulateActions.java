package EZ;
import java.util.ArrayList;
import java.util.Arrays;

import Battleax.Grading;
import Battleax.StepAndScore;
//将所有可以执行的步骤存入this.steps
public class SimulateActions {
	public ArrayList<int[]> steps=new ArrayList<int[]>();
	public SimulateActions(){
		
	}
	
	public ArrayList<int[]> AllActions() {
		int[] action={0,0,0,0};
		for(int i=1;i<=4;i++){                                  //首先执行占领的情况
			action[0]=i;
			steps.add(action.clone());				
			for(int j=5;j<=8;j++){                              //首先执行占领+移动的情况
				action[1]=j;
				steps.add(action.clone());
                                                                //执行占领+移动+隐形的情况
				action[2]=9;
				steps.add(action.clone());
				action[2]=0;
			}
			action[1]=0;
                                                                //执行占领+隐形的情况
			action[1]=9;
			steps.add(action.clone());

			action[1]=0;
			action[0]=0;
		}
		
		for(int i=5;i<=8;i++){                                  //首先执行移动的情况
			action[0]=i;
			steps.add(action.clone());
			for(int a=5;a<=8;a++){                              //首先执行移动+移动的情况
				action[1]=a;
				steps.add(action.clone());
				for(int b=5;b<=8;b++){                          //首先执行移动+移动+移动的情况
					action[2]=b;
					steps.add(action.clone());
					action[3]=9;
					steps.add(action.clone());
					action[3]=0;
					action[2]=0;
				}
				action[2]=9;
				steps.add(action.clone());
				action[2]=0;
				
				action[1]=0;
			}
				
			for(int j=1;j<=4;j++){                              //首先执行移动+占领的情况
				action[1]=j;
				steps.add(action.clone());
                                                                //执行移动+占领+隐形的情况
				action[2]=9;
				steps.add(action.clone());
				action[2]=0;
				
				action[1]=0;
			}
			
			action[1]=9;                                        //执行移动+隐形的情况
			steps.add(action.clone());
			action[1]=0;
			
            action[0]=0;
		}
		
		if(TurnInformation.nowAllSamurai.get(GameIniInformation.weapon).state==1){ //首先执行显形的情况
			int i=10;
			action[0]=i;
			steps.add(action.clone());
			for(int a=5;a<=8;a++){                              //首先执行显形+移动的情况
				action[1]=a;
				steps.add(action.clone());
				for(int j=1;j<=4;j++){                          //首先执行显形+移动+占领的情况
					action[2]=j;
					steps.add(action.clone());
					action[2]=0;
				}
				for(int b=5;b<=8;b++){                          //首先执行显形+移动+移动的情况
					action[2]=b;
					steps.add(action.clone());
					for(int c=5;c<=8;c++){                      //执行显形+移动+移动+移动的情况
						action[3]=c;
						steps.add(action.clone());
						action[3]=0;
					}
					
					action[3]=9;                                 //执行显形+移动+移动+隐形的情况
					steps.add(action.clone());
					action[3]=0;
					
					action[2]=0;
				}
				
				action[1]=0;
			}
			
			for(int a=1;a<=4;a++){                               //首先执行显形+占领的情况
				action[1]=a;
				steps.add(action.clone());
				for(int b=5;b<=8;b++){                           //执行显形+占领+移动的情况
					action[2]=b;
					steps.add(action.clone());
					action[2]=0;
				}
				action[2]=9;                                     //执行显形+占领+隐形的情况
				steps.add(action.clone());
				action[2]=0;
				
				action[1]=0;
			}
			action[0]=0;
		}
		return steps;
	}
	
	
}

