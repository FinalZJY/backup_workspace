package Sword;

public class SwordMain {
	
	public String act="";
	public String wAct="";
	NecessaryAct nAct =new NecessaryAct();
	public SwordMain(){
		if(nAct.MustKill()==0){
				if(!nAct.Escape()){
					nAct.ShouldOccupy();
					
				}
			}
		act = nAct.action+"0";
		wAct =nAct.whichAction;
		System.out.println(act);
		}
			
	}
	


