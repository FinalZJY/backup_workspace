
public class MessageHandling {
	String command="Unknown";
	int courseDay=-1;
	int[]courseTime={0,0,0,0,0,0,0,0,0,0,0,0};
	String courseName="Unknown";
	String courseRoom="Unknown";
	
	public static final String[] days = {"Monday","Tuesday","Wendsday","Thursday","Friday"};
	public static final String[] days_Chinese={"星期一","星期二","星期三","星期四","星期五"};
	public static final String[] Time={"first","second","third","forth","fifth","sixth","seventh","eighth",
			"nighth","tenth","eleventh","twelveth"};
	public static final String[] Time_Chinese={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	
	MessageHandling(String Cmd,int Day,int[]Time,String Name,String Room){
		command=Cmd;
		courseDay=Day;
		courseTime=Time;
		courseName=Name;
		courseRoom=Room;
	}
	
	
	
	public static String getCourseTime(int[]courseTime) {
		String stringOfCourseTime="The";
		for(int x=0;x<courseTime.length;x++){
			if(courseTime[x]==1){
				stringOfCourseTime=stringOfCourseTime+" "+Time[x]+" and";
			}
		}
		stringOfCourseTime=stringOfCourseTime.substring(0,stringOfCourseTime.length()-3)+"class(es).";
		return stringOfCourseTime;
	}
	
	static int indexOf(String[] Array,String s){                      //return the index of s in the Array.
		int index=-1;
		for(int i=0;i<Array.length;i++){
			if(Array[i].equals(s)){
				index=i;
				break;
			}
		}
		return index;
	}
	
	static MessageHandling getMessage(String line){
		if(line.equals("Show")){
			int[] i={0,0,0,0,0,0,0,0,0,0,0,0};
			MessageHandling m = new MessageHandling("Show",-1,i,"Unknown","Unknown");
			return m;
		}
		//split the string and put the items into a Message object then return.
		String [] commands = line.split("；|;");
		String [] commandAndDay=commands[0].split(" ");
		String command=commandAndDay[0].trim();
		int courseDay = indexOf(MessageHandling.days,commandAndDay[1].trim());
		if(courseDay==-1){
			courseDay=indexOf(MessageHandling.days_Chinese,commandAndDay[1]);
		}
		int[] courseTime={0,0,0,0,0,0,0,0,0,0,0,0};
		for(int x=0;x<=MessageHandling.Time.length-1;x++){
			if(commands[1].trim().contains(MessageHandling.Time[x])){
				courseTime[x]=1;
			}
		}
		for(int x=0;x<=MessageHandling.Time_Chinese.length-1;x++){
			if(commands[1].trim().contains(MessageHandling.Time_Chinese[x])){
				courseTime[x]=1;
			}
		}
		String courseName= "toBeFound";
		String courseRoom= "toBeFound";
		if(!command.equals("Find")){
			courseName=commands[2].trim();
			courseRoom=commands[3].trim();
		}
		MessageHandling message = new MessageHandling(command,courseDay,courseTime,courseName,courseRoom);
		return message;
	}
}
