
import java.util.ArrayList;
import java.util.Arrays;
/*
* 7��������
* 0��1��2��3��Ӧ���ҡ��ϡ���ռ�죬�ķ�4������
* 4��5��6��7��Ӧ���ҡ��ϡ����ƶ����ķ�2������
* 8��9��Ӧ�������Σ��ķ�1������
*/
public class test {
	public static void main(String[] args) {
		
		step[] st={
		new step("��ռ��",4),
		new step("��ռ��",4),
		new step("��ռ��",4),
		new step("��ռ��",4),
		new step("���ƶ�",2),
		new step("���ƶ�",2),
		new step("���ƶ�",2),
		new step("���ƶ�",2),
		new step("����",1),
		new step("����",1)
		};
		
		ArrayList<step> list=new ArrayList<step>();
		list.addAll(Arrays.asList(st));
		ArrayList<String> Steps=new ArrayList<String>();
		
			for(int i=0;i<=3;i++){                                  //����ִ��ռ������
				Steps.add(list.get(i).name+" "+list.get(i).cost);				
				for(int j=4;j<=7;j++){                              //����ִ��ռ��+�ƶ������
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost);
                                                                    //ִ��ռ��+�ƶ�+���ε����
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost+" "
							+list.get(8).name+" "+list.get(8).cost);
				}
                                                                    //ִ��ռ��+���ε����
				Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost);
				for(int j=4;j<=7;j++){ 
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost
							+" "+list.get(j).name+" "+list.get(j).cost);
				}
			}
			
			for(int i=4;i<=7;i++){                                  //����ִ���ƶ������
				for(int a=4;a<=5;a++){                              //ֻ�ƶ������
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
					
				for(int j=0;j<=3;j++){                              //����ִ���ƶ�+ռ������
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost);
                                                                    //ִ���ƶ�+ռ��+���ε����
					Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(j).name+" "+list.get(j).cost+" "
							+list.get(8).name+" "+list.get(8).cost);
				}
				
				for(int j=8;j<=9;j++){                              //����ִ���ƶ�+�ԣ������ε����
					for(int a=4;a<=5;a++){                          //ִֻ���ƶ�+�ԣ������ε����
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
				
                for(int j=0;j<=3;j++){                                //ִ���ƶ�+����+ռ������
				Steps.add(list.get(i).name+" "+list.get(i).cost+" "+list.get(8).name+" "+list.get(8).cost+" "
						+list.get(j).name+" "+list.get(j).cost);
                }
			}
			
			{                                                         //����ִ�����ε����
				Steps.add(list.get(8).name+" "+list.get(8).cost);
				
				for(int a=4;a<=5;a++){                                //ִֻ������+�ƶ������
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
			
			{                                                         //����ִ�����ε����
				Steps.add(list.get(9).name+" "+list.get(9).cost);
				
				for(int a=4;a<=5;a++){                                //ִֻ������+�ƶ������
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
				
				for(int a=4;a<=7;a++){                                //ִ������+�ƶ�+ռ������
					for(int i=0;i<=3;i++){
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(a).name+" "+list.get(a).cost
								+" "+list.get(i).name+" "+list.get(i).cost);
					}
				}
				
				for(int i=0;i<=3;i++){                                //����ִ������+ռ������
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost);
					Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost
							+" "+list.get(8).name+" "+list.get(8).cost);
					for(int a=4;a<=7;a++){                            //ִ������+ռ��+�ƶ������
						Steps.add(list.get(9).name+" "+list.get(9).cost+" "+list.get(i).name+" "+list.get(i).cost
								+" "+list.get(a).name+" "+list.get(a).cost);
					}
				}
			}
			
			for(String cases:Steps){                                  //����������
				System.out.println(cases);
			}
			System.out.println("��"+Steps.size()+"�����");

	}
}
	
	

	
	




 
	
