package Main;

public class Student {
	
	private int id;
	private int course;
	private int level;
	
	public Student(int id, int course, int level){
		this.id = id;
		this.course = course;
		this.level = level;
	}
	
	public int getId(){
		return id;
	}
	public int getCourse(){
		return course;
	}
	public String getCourseString(){
		if(course == 0)
			return "DESIGN";
		else 
			return "PROGRAMMING";
	}
	
	public int getLevel(){
		return level;
	}
}
