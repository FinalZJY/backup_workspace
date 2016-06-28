import java.util.*;


public class Homework6 {

	public static void main(String[] args) {		
		String list2;
		List<String> Alist=Arrays.asList("neal", "s", "stu","j","rich","bob");
		list2=Alist.stream().
		filter(words -> words.length()>1)
	    .map(words -> words.substring(0, 1).toUpperCase()+words.substring(1)+",")
	    .reduce("", String::concat);	   
		
		System.out.println(list2.substring(0,list2.length()-1));

	}

}

