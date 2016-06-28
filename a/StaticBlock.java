import org.omg.CORBA.PUBLIC_MEMBER;

class StaticBlock {
  static final int c = 3;
  static final int d;
  static int e = 5;
  static{
	  d=8;
  }
  boolean t=true;
  StaticBlock() {
  System.out.println("Building");
  
  
  }
  
 }