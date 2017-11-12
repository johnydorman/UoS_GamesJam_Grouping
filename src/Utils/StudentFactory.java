package Utils;
import Main.Student;

public class StudentFactory {
	
	private int id;
	private int course;
	private int level;
	
	public StudentFactory(){
	}

	public Student create(String[] row) {
		try{
			 id = Integer.parseInt(row[0]);
			 level = Integer.parseInt(row[1]);
		} catch(NumberFormatException e){
			//Log Error
			return null;
		}
		
		 course = getCourse(row[2]);
		
		Student std = new Student(id, course, level);
		return std;
	}
	
	private int getCourse(String course){
		if(course.startsWith("D")){
			return 0;
		} else {
			return 1;
		}
	}
}
