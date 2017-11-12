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
	public int getLevel(){
		return level;
	}
}
