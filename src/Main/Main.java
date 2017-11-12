package Main;

public class Main {
	
	public static void main(String[] args){
		Context context = new Context();
		context.readConfig("Config.csv");
		context.setRandom();
		context.readStudents("Students.csv");
		
		GA geneticAlgorithm = new GA(context);
		geneticAlgorithm.initialize();
		geneticAlgorithm.generate();
		
		//TODO: output
		Individual best = geneticAlgorithm.getIndividual(geneticAlgorithm.bestFitness());
		System.out.println(best.fullDebug());
	}
}
