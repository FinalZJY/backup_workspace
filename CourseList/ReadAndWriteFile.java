
import java.io.*;
public class ReadAndWriteFile {    
    public static String read(){
    	String content=null;
    	try{
    		File fileA =new File("A.txt");
    		if(!fileA.exists()){
    			System.out.println("Can not find the file");
    		}

    		FileReader fileReaderer = new FileReader(fileA.getName());
    		BufferedReader bufferedReader = new BufferedReader(fileReaderer);
    		content=bufferedReader.readLine();
    		bufferedReader.close();
    		System.out.println("Reading completed");
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		System.out.println("Reading file error");
    	}
    	return content;
    }
    
    public static void write(String data){
    	try{
    		File fileB =new File("CurriculumSchedule.txt");
    		if(!fileB.exists()){
    			fileB.createNewFile();
    		}

    		FileWriter fileWritter = new FileWriter(fileB.getName(),true);//true = append file
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    		bufferWritter.write(data);
    		bufferWritter.newLine();
    		bufferWritter.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		System.out.println("writing file error");
    	}
    }

    public static void writeCourseList(){
		for(int course=0;course<CourseList.allCourses.size()-1;course++){                         //change the order
			if(CourseList.allCourses.get(course).courseDay>CourseList.allCourses.get(course+1).courseDay){
				Course c=CourseList.allCourses.get(course);
				CourseList.allCourses.set(course, CourseList.allCourses.get(course+1));
				CourseList.allCourses.set(course+1, c);
			}
			if(CourseList.allCourses.get(course).courseDay==CourseList.allCourses.get(course+1).courseDay){
				for(int x=0;x<CourseList.allCourses.get(course).courseTime.length-1;x++){
					if((CourseList.allCourses.get(course).courseTime[x]==0)&&(CourseList.allCourses.get(course+1).courseTime[x]==1)){
						Course c=CourseList.allCourses.get(course);
						CourseList.allCourses.set(course, CourseList.allCourses.get(course+1));
						CourseList.allCourses.set(course+1, c);
						break;
					}
					if((CourseList.allCourses.get(course).courseTime[x]==1)&&(CourseList.allCourses.get(course+1).courseTime[x]==0)){
						break;
					}
				}
			}
		}
		
		try{                                          //delete the content of CurriculumSchedule.txt
			File fileB =new File("CurriculumSchedule.txt");
			if(!fileB.exists()){
				fileB.createNewFile();
			}

			FileWriter fileWritter = new FileWriter(fileB.getName());
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.newLine();
		}
    	catch(IOException e){
    		e.printStackTrace();
    		System.out.println("writing file error");
    	}
		
		for(Course c:CourseList.allCourses){
			ReadAndWriteFile.write(MessageHandling.days[c.courseDay]);
			ReadAndWriteFile.write(MessageHandling.getCourseTime(c.courseTime));
			ReadAndWriteFile.write(c.courseName);
			ReadAndWriteFile.write(c.courseRoom);
			ReadAndWriteFile.write("");
		}
	}
}
