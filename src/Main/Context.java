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
	public Map<Integer,Student> students;
	public Random random;
	
	Group[][] storedGroups;
	
	public Context(){
		this.students = new HashMap<Integer,Student>();
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
			students.put(newStudent.getId(),newStudent);
		}
	}
	
	/*
	 * Reads-in the students previous groups from file
	 */
	public void readPreviousGroups(String fileName){
		BufferedReader br = new BufferedReader(Utils.Utils.getDataFile(fileName));
		CSVIterator csvIteration = new CSVIterator(br, ",", 1);
		
		Map<Integer, List<Student>> grouping1 = new HashMap<Integer, List<Student>>();
		Map<Integer, List<Student>> grouping2 = new HashMap<Integer, List<Student>>();
		
		for(String[] row : csvIteration){
			/*
			row[0] = student id
			row[1] = jam 1
			row[2] = jam 2
			*/
			Student stud = studentFromId(Integer.parseInt(row[0]));
			
			if(!row[1].equals("")){
				Integer groupOneId = Integer.parseInt(row[1]);
				List<Student> groupOne = grouping1.get(groupOneId);
				if(groupOne == null){
					groupOne = new ArrayList<Student>();
					grouping1.put(groupOneId, groupOne);
					
				}
				groupOne.add(stud);
			}
			
			if(!row[2].equals("")){
				Integer groupTwoId = Integer.parseInt(row[2]);
				List<Student> groupTwo = grouping2.get(groupTwoId);
				if(groupTwo == null){
					groupTwo = new ArrayList<Student>();
					grouping2.put(groupTwoId, groupTwo);
				}
				groupTwo.add(stud);
			}
		}
		
		storedGroups = new Group[2][];
		storedGroups[0] = new Group[grouping1.keySet().size()];
		storedGroups[1] = new Group[grouping2.keySet().size()];
		
		int iterator = 0;
		for(int grp : grouping1.keySet()){
			List<Student> students = grouping1.get(grp);
			storedGroups[0][iterator] = new Group(students.toArray(new Student[students.size()]));
			iterator++;
		}
		
		iterator = 0;
		for(int grp : grouping2.keySet()){
			List<Student> students = grouping2.get(grp);
			storedGroups[1][iterator] = new Group(students.toArray(new Student[students.size()]));
			iterator++;
		}
	}
	
	/*
	 *  Returns a student from an Id
	 */
	public Student studentFromId(int id){
		return students.get(id);
	}
	
	/* 
	 * Generates Individual /w previous assigned groups
	 */
	public Individual generateIndividualFromFile(){
		Group[][] groups = new Group[getInt("GROUPAMOUNT")][];
		
		for(int i = 0; i < storedGroups.length; i++){
			List<Group> newSet = new ArrayList<Group>();
			for(Group grp : storedGroups[i]){
				newSet.add(new Group(grp));
			}
			groups[i] = newSet.toArray(new Group[newSet.size()]);
		}
		
		for(int groupSets = storedGroups.length; groupSets < getInt("GROUPAMOUNT"); groupSets++){
			groups[groupSets] = buildGroupSet();
		}
		Individual ind = new Individual(groups);
		return ind;
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
		List<Student> studentSet = new ArrayList<Student>(students.values());
		List<Group> groupSet = new ArrayList<Group>();
		
		while(studentSet.size() != 0){
			//Create a list of students to be put into a group
			List<Student> studentsGroup = new ArrayList<Student>();
			
			// Loop between the min and max of students in a group
			for(int groupMember = 0; groupMember < 4; groupMember++){
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
