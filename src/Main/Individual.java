package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Individual {
	
	private Group[][] groups;
	private Double fitness;
	
	public Individual(Group[][] groups){
		this.groups = groups;
	}
	
	/*
	 * Changes of the groups at random
	 */
	public Individual mutate(Context context){
		// Make a deep copy of this individual
		Group[][] offspringGroups = deepCopyIndividual(this);
		
		// Mutate one of the group sets at random
		// ignoring stored groups
		offspringGroups[context.random.nextInt(groups.length-context.getInt("PREVIOUSGROUPSAMOUNT"))+context.getInt("PREVIOUSGROUPSAMOUNT")] = context.buildGroupSet();
		
		Individual offspring = new Individual(offspringGroups);
		return offspring;
	}
	
	/*
	 * Swaps a group set from each individual
	 */
	public Individual[] crossover(Context context, Individual other){
		int crossoverIndex = context.random.nextInt(context.random.nextInt(groups.length-context.getInt("PREVIOUSGROUPSAMOUNT"))+context.getInt("PREVIOUSGROUPSAMOUNT"));
		
		Group[][] offspring1Groups = deepCopyIndividual(this);
		offspring1Groups[crossoverIndex] = deepCopyGroupSet(other.groups[crossoverIndex]);
		
		Group[][] offspring2Groups = deepCopyIndividual(other);	
		offspring2Groups[crossoverIndex] = deepCopyGroupSet(this.groups[crossoverIndex]);
		
		Individual offspring1 = new Individual(offspring1Groups);
		Individual offspring2 = new Individual(offspring2Groups);
		return new Individual[]{offspring1, offspring2};
	}

	/*
	 * Calculates and sets the fitness for the individual
	 */
	public Double setFitness(){
		double runningFitness = 0.0d;
		Map<Integer, List<Integer>> studentMap = new HashMap<Integer, List<Integer>>();
		
		for(Group[] groupSet : groups){
			
			for(Group group : groupSet){
				if(group.hasLevel5()){
					runningFitness+= 10;
				}
				if(group.hasProgrammer()){
					runningFitness+= 5;
				}
				
				for(Student stu : group.getMembers()){
					List<Integer> studentMembers = studentMap.get(stu.getId());
					if(studentMembers == null){
						studentMembers = new ArrayList<Integer>();
						studentMap.put(stu.getId(), studentMembers);
					}
					
					for(Student groupMember : group.getMembers()){
						if(stu.getId() != groupMember.getId()){
							studentMembers.add(groupMember.getId());
						}
					}
				}
			}
		}
		
		for(List<Integer> something : studentMap.values()){
			Set<Integer> set = new HashSet<Integer>(something);

			if(set.size() == something.size()){
				runningFitness += 2;
			}
		}
		
		this.fitness = runningFitness;
		return fitness;
	}
	
	/*
	 *  Outputs a full display of the groups in this individual
	 */
	public String fullDebug(){
		String ret = "";
		for(int i = 0; i < groups.length; i++){
			for(int j = 0; j < groups[i].length; j++){
				for(Student student : groups[i][j].getMembers()){
					ret +=  "GroupSet" + i  + "," + "Group" + j + "," + student.getId() + "," + student.getLevel() + "," + student.getCourseString() + "\n";
				}
			}
		}
		return ret;
	}
	
	private Group[][] deepCopyIndividual(Individual ind) {
		Group[][] offspringGroups = new Group[ind.groups.length][];
		for(int i = 0; i<groups.length; i++){
			List<Group> copytemp = new ArrayList<Group>();
			for(Group grp : ind.groups[i]){
				copytemp.add(new Group(grp));
			}
			offspringGroups[i] = (Group[]) copytemp.toArray(new Group[copytemp.size()]);
		}
		return offspringGroups;
	}
	
	private Group[] deepCopyGroupSet(Group[] groupsToCopy) {
		List<Group> returnGroups = new ArrayList<Group>();
		for(int i = 0; i < groupsToCopy.length; i++){
			returnGroups.add(new Group(groupsToCopy[i]));
		}
		return returnGroups.toArray(new Group[returnGroups.size()]);
	}
}
