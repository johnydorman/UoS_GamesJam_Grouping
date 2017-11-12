package Main;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private Student[] members;
	
	public Group(Student...students ){
		this.members = students;
	}
	
	// Used for deepcopy
	public Group(Group grp) {
		List<Student> newList = new ArrayList<Student>();
		for(Student stud : grp.members){
			newList.add(stud);
		}
		this.members = (Student[]) newList.toArray(new Student[newList.size()]);
	}

	public Student[] getMembers(){
		return members;
	}

	public boolean hasLevel5(){
		for(Student stu : members){
			if(stu.getLevel() == 5)
				return true;
		}
		return false;
	}
	public boolean hasProgrammer(){
		for(Student stu : members){
			if(stu.getCourse() == 1)
				return true;
		}
		return false;
	}

}
