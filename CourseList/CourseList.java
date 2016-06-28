
import java.util.*;

public class CourseList {
	static ArrayList<Course> allCourses=new ArrayList<Course>();
	
	public static void main(String[] args) {
		InputAndOutput.promptUser();
		while(true){
			String s=InputAndOutput.GetInput();
			MessageHandling message=MessageHandling.getMessage(s);
			handle(message);
		}
	}
	
	public static void handle(MessageHandling message) {
		if(message.command.equals("Show")){
			ReadAndWriteFile.writeCourseList();
			Course.showCourses();
		}
		else if(message.command.equals("Add")){
			Course.addCourses(message.courseDay,message.courseTime,message.courseName,message.courseRoom);
			InputAndOutput.output("Add succeed.");
		}
		else if(message.command.equals("Remove")){
			Course.removeCourses(message.courseDay,message.courseTime,message.courseName);
			InputAndOutput.output("Remove succeed.");
		}
		else if(message.command.equals("Update")){
			Course.updateCourses(message.courseDay, message.courseTime, message.courseName, message.courseRoom);
			InputAndOutput.output("Update succeed.");
		}
		else if(message.command.equals("Find")){
			Course.findCourses(message.courseDay, message.courseTime);
		}
		else{
			InputAndOutput.output("Your command can not be read.Please try again:");
		}
	}
	
	
}
