
public class Course {
	int courseDay=0;                             //0 to 4 is Monday to Friday
	int[] courseTime={};
	String courseName="Unknown";
	String courseRoom="Unknown";
	
	public Course(int Day,int[] Time,String Name,String Room){
		courseDay=Day;
		courseTime=Time;
		courseName=Name;
		courseRoom=Room;
	}
	
	
	public static void addCourses(int courseDay,int[]courseTime,String courseName,String courseRoom){	
		CourseList.allCourses.add(new Course(courseDay,courseTime,courseName,courseRoom));
	}
	
	public static void removeCourses(int courseDay,int[]courseTime,String courseName){
		for(Course c:CourseList.allCourses){
			if((c.courseName.equals(courseName))&&(c.courseDay==courseDay)){
				boolean time=true;
				for(int x=0;x<courseTime.length;x++){
					if(c.courseTime[x]!=courseTime[x]){
						time=false;
					}
				}
				if(time){
					CourseList.allCourses.remove(c);
				}
			}
		if(CourseList.allCourses.size()==0){
			break;
		}
		}
	}
	
	public static void updateCourses(int courseDay,int[]courseTime,String courseName,String courseRoom){
		for(Course c:CourseList.allCourses){
			if(c.courseDay==courseDay){
				for(int x=0;x<courseTime.length;x++){
					if((courseTime[x]==1)&&(c.courseTime[x]==1)){
						CourseList.allCourses.remove(c);
						break;
					}
				}
			}
			if(CourseList.allCourses.size()==0){
				break;
			}
		}
		addCourses(courseDay,courseTime,courseName,courseRoom);
	}
	
	public static void findCourses(int courseDay, int[]courseTime) {
		boolean found=false;
		for(Course c:CourseList.allCourses){
			boolean exist=false;
			if(c.courseDay==courseDay){
				exist=true;
				for(int x=0;x<courseTime.length;x++){
					if(c.courseTime[x]!=courseTime[x]){
						exist=false;
					}
				}
			}
			if(exist){
				InputAndOutput.output("courseName = "+c.courseName);
				InputAndOutput.output("courseRoom = "+c.courseRoom);
				found=true;
				break;
			}
		}
		if(!found){
			InputAndOutput.output("not found.");
		}
	}
	
	public static void showCourses(){
		InputAndOutput.output("Your courses:");
		for(Course c:CourseList.allCourses){
			InputAndOutput.output("courseDay = "+MessageHandling.days[c.courseDay]);
			InputAndOutput.output("courseTime = "+MessageHandling.getCourseTime(c.courseTime));
			InputAndOutput.output("courseName = "+c.courseName);
			InputAndOutput.output("courseRoom = "+c.courseRoom);
			InputAndOutput.output("");
		}
	}
}
