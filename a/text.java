import java.util.Arrays;


public class text {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        int [] array1 = {1,2,3}; 
        int [] array2 = {3,2,1}; 
         Arrays.sort (array1); 
         Arrays.sort (array2); 
        if ( Arrays.equals (array1, array2)) { 
                System.out.println("���������е�Ԫ��ֵ��ͬ"); 
        } else { 
                System.out.println("���������е�Ԫ��ֵ����ͬ"); 
        } 
	}

}
