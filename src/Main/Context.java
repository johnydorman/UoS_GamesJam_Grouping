package Main;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Utils.CSVIterator;
import Utils.StudentFactory;

public class Context {
	
	public Map<String, Integer> config;
	public List<Student> students;
	public Random random;
	
	public Context(){
		this.students = new ArrayList<Student>();
		this.config = new HashMap<String, Integer>();
	}
	
	public int getInt(String key) {
		return config.get(key);
	}
	
	public void setRandom() {
		this.random = new Random();
	}
	
	/*
	 *  Reads-in the configuration
	 */
	public void readConfig(String fileName) {		
		BufferedReader br = new BufferedReader(Utils.Utils.getDataFile(fileName));
		CSVIterator csvIteration = new CSVIterator(br, ",", 1);
		
		for(String[] row : csvIteration){
			config.put(row[0], Integer.parseInt(row[1]));
		}
	}
	
	/*
	 *  Reads-in the students file
	 */
	public void readStudents(String fileName){
		StudentFactory stdFactory = new StudentFactory();
		
		BufferedReader br = new BufferedReader(Utils.Utils.getDataFile(fileName));
		CSVIterator csvIteration = new CSVIterator(br, ",", 1);
		
		for(String[] row : csvIteration){
			Student newStudent = stdFactory.create(row);
			students.add(newStudent);
		}
	}
	
	/*
	 * Generates a random Individual
	 */
	public Individual generateIndividual(){
		Group[][] groups = new Group[getInt("GROUPAMOUNT")][];
		
		for(int groupSets = 0; groupSets < getInt("GROUPAMOUNT"); groupSets++){
			groups[groupSets] = buildGroupSet();
		}
		Individual ind = new Individual(groups);
		return ind;
	}

	/*
	 * Generates a group set
	 */
	public Group[] buildGroupSet() {
		List<Student> studentSet = new ArrayList<Student>(students);
		List<Group> groupSet = new ArrayList<Group>();
		
		while(studentSet.size() != 0){
			//Create a list of students to be put into a group
			List<Student> studentsGroup = new ArrayList<Student>();
			
			// Loop between the min and max of students in a group
			for(int groupMember = 0; groupMember < getGroupRange(); groupMember++){
				Student currentStudent = studentSet.get(random.nextInt(studentSet.size()));
				studentsGroup.add(currentStudent);
				
				// remove student from the student set
				studentSet.remove(currentStudent);
				
				if(studentSet.size() == 0)
					break;
			}
			Group group = new Group((Student[])studentsGroup.toArray(new Student[studentsGroup.size()]));
			groupSet.add(group);
		}
		return (Group[])groupSet.toArray(new Group[groupSet.size()]);
	}

	/*
	 * Returns the a range with the size of the group
	 */
	private int getGroupRange() {
		int range = getInt("GROUPMAX")-getInt("GROUPMIN");
		int ret = random.nextInt(range);
		ret += getInt("GROUPMIN");
		return ret;
	}
}
