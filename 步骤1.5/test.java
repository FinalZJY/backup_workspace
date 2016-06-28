
import java.util.ArrayList;
import java.util.Arrays;
/*
* 7点体力内
* 0、1、2、3对应左、右、上、下占领，耗费4点体力
* 4、5、6、7对应左、右、上、下移动，耗费2点体力
* 8、9对应隐身，现形，耗费1点体力
*/
public class test {
	public static void main(String[] args) {
		
		step[] st={
		new step("左占领",4),
		new step("右占领",4),
		new step("上占领",4),
		new step("下占领",4),
		new step("左移动",2),
		new step("右移动",2),
		new step("上移动",2),
		new step("下移动",2),
		new step("隐身",1),
		new step("现形",1)
		};
		
		ArrayList<step> list=new ArrayList<step>();
		list.addAll(Arrays.asList(st));
		ArrayList<String> Steps=new ArrayList<String>();
		
			for(int i=0;i<=3;i++){                                  //首先执行占领的情况
				Steps.add(list.get(i).name+" "+list.get(i).cost);				
				for(int j=4;j<=7;j++){                              //首先执行占领+移动的情况
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost);
                                                                    //执行占领+移动+隐形的情况
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost+" "
							+list.get(8).name+" "+list.get(8).cost);
				}
                                                                    //执行占领+隐形的情况
				Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost);
				for(int j=4;j<=7;j++){ 
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost
							+" "+list.get(j).name+" "+list.get(j).cost);
				}
			}
			
			for(int i=4;i<=7;i++){                                  //首先执行移动的情况
				for(int a=4;a<=5;a++){                              //只移动的情况
					Steps.add(list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost
							+" "+list.get(a).name+" "+list.get(a).cost);
					for(int b=6;b<=7;b++){
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost
								+" "+list.get(b).name+" "+list.get(b).cost);
					}
				}
					
				for(int j=0;j<=3;j++){                              //首先执行移动+占领的情况
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost);
                                                                    //执行移动+占领+隐形的情况
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost+" "
							+list.get(8).name+" "+list.get(8).cost);
				}
				
				for(int j=8;j<=9;j++){                              //首先执行移动+显（隐）形的情况
					for(int a=4;a<=5;a++){                          //只执行移动+显（隐）形的情况
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(j).name+" "+list.get(j).cost);
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(j).name+" "+list.get(j).cost);
						Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(a).name+" "+list.get(a).cost+" "+list.get(j).name+" "+list.get(j).cost);
						for(int b=6;b<=7;b++){
							Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost
									+" "+list.get(j).name+" "+list.get(j).cost);
							Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost
									+" "+list.get(b).name+" "+list.get(b).cost+" "+list.get(j).name+" "+list.get(j).cost);
							Steps.add(list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost
									+" "+list.get(b).name+" "+list.get(b).cost+" "+list.get(j).name+" "+list.get(j).cost);
						}
					}
				}
				
                for(int j=0;j<=3;j++){                                //执行移动+显形+占领的情况
				Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost+" "
						+list.get(j).name+" "+list.get(j).cost);
                }
			}
			
			{                                                         //首先执行隐形的情况
				Steps.add(list.get(8).name+" "+list.get(8).cost);
				
				for(int a=4;a<=5;a++){                                //只执行隐形+移动的情况
					Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost
							+" "+list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost
							+" "+list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost);
					for(int b=6;b<=7;b++){
						Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(8).name+" "+list.get(8).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(b).name+" "+list.get(b).cost+" "+list.get(b).name+" "+list.get(b).cost);
					}
				}
			}
			
			{                                                         //首先执行显形的情况
				Steps.add(list.get(9).name+" "+list.get(9).cost);
				
				for(int a=4;a<=5;a++){                                //只执行显形+移动的情况
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
							+" "+list.get(a).name+" "+list.get(a).cost);
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
							+" "+list.get(a).name+" "+list.get(a).cost+" "+list.get(a).name+" "+list.get(a).cost);
					for(int b=6;b<=7;b++){
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(a).name+" "+list.get(a).cost+" "+list.get(b).name+" "+list.get(b).cost);
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(b).name+" "+list.get(b).cost+" "+list.get(b).name+" "+list.get(b).cost);
					}
				}
				
				for(int a=4;a<=7;a++){                                //执行显形+移动+占领的情况
					for(int i=0;i<=3;i++){
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(i).name+" "+list.get(i).cost);
					}
				}
				
				for(int i=0;i<=3;i++){                                //首先执行显形+占领的情况
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost);
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost
							+" "+list.get(8).name+" "+list.get(8).cost);
					for(int a=4;a<=7;a++){                            //执行显形+占领+移动的情况
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost
								+" "+list.get(a).name+" "+list.get(a).cost);
					}
				}
			}
			
			for(String cases:Steps){                                  //输出所有情况
				System.out.println(cases);
			}
			System.out.println("共"+Steps.size()+"种情况");

	}
}
	
	

	
	




 
	
