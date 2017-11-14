package Main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		Context context = new Context();
		context.readConfig("Config.csv");
		context.setRandom();
		context.readStudents("Students.csv");
		
		if(context.getInt("PREVIOUSGROUPS") == 1)
			context.readPreviousGroups("PreviousGroups.csv");
		
		GA geneticAlgorithm = new GA(context);
		geneticAlgorithm.initialize();
		geneticAlgorithm.generate();
		
		Individual best = geneticAlgorithm.getIndividual(geneticAlgorithm.bestFitness());
		
		// Output the groups
		PrintWriter tripStopPathWriter = new PrintWriter("./output/JamGroups.csv");
		tripStopPathWriter.println("Games Jam, Group Id, Student Id, Level, Course");
		tripStopPathWriter.println(best.fullDebug());
		tripStopPathWriter.close();
	}
}
