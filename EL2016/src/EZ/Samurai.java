package EZ;

public class Samurai {
	public int team;
	public int weapon;
	public int row;//行
	public int col;//列
	public int rank;//排名
	public int score;//得分
	public int state;//状态
	//
	public Samurai(int rank,int score){
		this.rank=rank;
		this.score=score;
		
	}
	//
	public Samurai(int row,int col,int state){
		this.row=row;
		this.col=col;
		this.state=state;		
	}
	public Samurai(int row,int col,int state,int weapon,int team){  //注意：以后全部用这个构造方法  by：俊毅
		this.row=row;
		this.col=col;
		this.state=state;
		this.weapon=weapon;
		this.team=team;
	}

}
