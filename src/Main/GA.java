package Main;

public class GA {
	
	private Context context;
	private int generations;
	private int tournamentSize;
	private int crossoverChance;
	
	private Individual[] population;
	private Double[] fitness;
	
	public GA(Context context) {
		this.context = context;
	}
	
	public void initialize(){
		generations = context.getInt("GENERATIONS");
		tournamentSize = context.getInt("TOURNAMENTSIZE");
		crossoverChance = context.getInt("CROSSOVERCHANCE");
		
		population = new Individual[context.getInt("POPULATION")];
		fitness = new Double[context.getInt("POPULATION")];
		
		buildPopulation();
	}
	
	private void buildPopulation() {
		for(int i = 0; i < population.length; i++){
			population[i] = context.generateIndividual();
			fitness[i] = population[i].setFitness();
		}

	}

	public void generate(){
		for(int currentGen = 1; currentGen <= generations; currentGen++){
			for (int pairs = 0; pairs < population.length; pairs += 2) {
				Individual[] children = new Individual[2];
				int parent1 = tournament(+1);
				int parent2 = tournament(+1);

				int random = context.random.nextInt(100);
				if (random <= crossoverChance) {
					children = population[parent1].crossover(context, population[parent2]);
				} else {
					children[0] = population[parent1].mutate(context);
					children[1] = population[parent2].mutate(context);
				}

				int offspring1 = tournament(-1);
				int offspring2 = tournament(-1);

				population[offspring1] = children[0];
				population[offspring2] = children[1];
				fitness[offspring1] = children[0].setFitness();
				fitness[offspring2] = children[1].setFitness();
			}
			debugOutput(currentGen);
		}
	}
	

	void debugOutput(int currentGen) {
		out("Generation: " + currentGen);
		int best = bestFitness();
		out("\tBiggest: " + population[best].setFitness());
	}

	public int bestFitness() {
		double max = Integer.MIN_VALUE;
		int best = -1;
		double average = 0;

		for (int i = 0; i < fitness.length; i++) {
			if (fitness[i] > max) {
				max = fitness[best = i];
			}
			average += fitness[i];
		}
		out("\tAverage: " + average / population.length);
		return best;
	}

	private int tournament(int abs) {
		double max = Double.NEGATIVE_INFINITY;
		int argMax = -1;
		for (int i = 0; i < tournamentSize; i++) {
			int individual = context.random.nextInt(population.length);
			if (abs * fitness[individual] > max) {
				max = abs * fitness[argMax = individual];
			}
		}
		return argMax;
	}
	
	private void out(String string) {
		System.out.println(string);
	}

	public Individual getIndividual(int index) {
		return population[index];
	}
}
