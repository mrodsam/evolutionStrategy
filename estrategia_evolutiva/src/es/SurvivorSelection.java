package es;

import java.util.LinkedList;

public class SurvivorSelection {

	public static void muLambda() {

		LinkedList<Individual> newPopulation = new LinkedList<Individual>();

		while (newPopulation.size() < Main.populationSize) {
			int count = 0;

			Individual best = new Individual(Main.sphereFunction);
			for (Individual individual : Main.offspring) {
				count++;
				if (count == 1 || individual.getFitness() < best.getFitness()) {
					best = individual;
				}
			}
			newPopulation.add(best);
			Main.offspring.remove(best);
		}
		
//		newPopulation.forEach(value -> System.out.println(value.getFitness()));
		Main.population = newPopulation;

	}
	
	public static void muPlusLambda() {

		LinkedList<Individual> newPopulation = new LinkedList<Individual>();
		Main.offspring.addAll(Main.population);

		while (newPopulation.size() < Main.populationSize) {
			int count = 0;

			Individual best = new Individual(Main.sphereFunction);
			for (Individual individual : Main.offspring) {
				count++;
				if (count == 1 || individual.getFitness() < best.getFitness()) {
					best = individual;
				}
			}
			newPopulation.add(best);
			Main.offspring.remove(best);
		}
		
//		newPopulation.forEach(value -> System.out.println(value.getFitness()));
		Main.population = newPopulation;

	}

}
