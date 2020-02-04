package estrategia_evolutiva;

import java.util.LinkedList;
import java.util.Random;

public class Main {

	static Integer numberOfValues = 10;
	static Integer populationSize = 30;
	static Integer offspringSize = 200;
	Double sphere_min = 0.0;
	static Double sphere_domain_min = -100.0;
	static Double sphere_domain_max = 100.0;
	static Double[] stepSize = { 1.0 };

	static LinkedList<Individual> population;
	static LinkedList<Individual> offspring;

	public static void main(String[] args) {
		initPopulation();
	}

	public static void initPopulation() {

		population = new LinkedList<Individual>();

		for (int i = 0; i < populationSize; i++) {
			Individual ind = new Individual();
			Double values[] = new Double[numberOfValues];

			for (int j = 0; j < numberOfValues; j++) {
				values[j] = Math.random() * (sphere_domain_max - sphere_domain_min) + sphere_domain_min;
			}
			ind.setValues(values);
			ind.setStepSizes(stepSize);
			population.add(ind);
		}

		population.forEach(System.out::println);

	}

	public static void getOffspring() {
		
		offspring = new LinkedList<Individual>();
		Individual parent1;
		Individual parent2;
		
		for (int i = 0; i < offspringSize; i++) {
			parent1 = getRandomParent();
			parent2 = getRandomParent();
			
			offspring.add(Recombination.globalRecombination(parent1, parent2));
		}
		
	}

	public static Individual getRandomParent() {
		Random rand = new Random();
		return population.get(rand.nextInt(population.size()));
	}

}
