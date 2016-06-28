package Battleax;
//SimulateActions类用来存储步骤和分数的
public class StepAndScore {
	int[] step={0,0,0,0};
	int score=0;
	public StepAndScore(int[] step,int score){
		this.step=step.clone();
		this.score=score;
	}
}
