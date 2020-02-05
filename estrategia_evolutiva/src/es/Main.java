package es;

import java.util.LinkedList;
import java.util.Random;

public class Main {

	static Boolean sphereFunction = false; // 0: esfera, 1: Schwefel
	static Boolean oneStep = true; // True:1 paso, False: n pasos
	static Boolean selectionSchemeUnion = false; //true:mu+lambda, false:mu,lambda

	static Integer runs = 20;
	static Integer generations = 2000;

	static Integer numberOfValues = 10;
	static Integer populationSize = 100; // 30
	static Integer offspringSize = 200; // 200

	static Double domain_min;
	static Double domain_max;
	
	static Double[] stepSize;

	static LinkedList<Individual> population;
	static LinkedList<Individual> offspring;

	public static void main(String[] args) {
		
		if (sphereFunction) {
			domain_min = -100.0;
			domain_max = 100.0;
		}else {
			domain_min = -500.0;
			domain_max = 500.0;
		}
		
		if (oneStep) {
			stepSize = new Double[1];
			stepSize[0] = 1.0;
		}else {
			stepSize = new Double[Main.numberOfValues];
			for (int i = 0; i < stepSize.length; i++) {
				stepSize[i] = 1.0;
			}
		}

		for (int i = 0; i < runs; i++) {
			System.out.println("Ejecución: " + i);
			initPopulation();
			for (int j = 0; j < generations; j++) {
				getOffspring();
				if(selectionSchemeUnion)
					SurvivorSelection.muPlusLambda();
				else
					SurvivorSelection.muLambda();
			}
			getSolution();

		}

	}

	public static void initPopulation() {

		population = new LinkedList<Individual>();

		for (int i = 0; i < populationSize; i++) {
			Individual ind = new Individual(sphereFunction);
			Double values[] = new Double[numberOfValues];

			for (int j = 0; j < numberOfValues; j++) {
				values[j] = Math.random() * (domain_max - domain_min) + domain_min;
			}
			ind.setValues(values);
			ind.setStepSizes(stepSize);
			population.add(ind);
		}

//		population.forEach(value -> System.out.println("Pop: "+value.getFitness()));

	}

	public static void getOffspring() {

		offspring = new LinkedList<Individual>();
		Individual parent1;
		Individual parent2;
		Individual child;

		for (int i = 0; i < offspringSize; i++) {
			parent1 = getRandomParent();
			parent2 = getRandomParent();

			child = Recombination.globalRecombination(parent1, parent2);
			
			if (oneStep)
				Mutation.uncorrelatedMutationWOneStepSize(child);
			else
				Mutation.uncorrelatedMutationWNStepSizes(child);

			offspring.add(child);

		}

//		offspring.forEach(value -> System.out.println("Off: "+value.getFitness()));

	}

	public static Individual getRandomParent() {
		Random rand = new Random();
		return population.get(rand.nextInt(population.size()));
	}

	public static void getSolution() {
		int count = 0;
		Individual best = new Individual(sphereFunction);
		for (Individual individual : population) {
			count++;
			if (count == 1 || individual.getFitness() < best.getFitness()) {
				best = individual;
			}

		}

		System.out.println(best.getFitness() + ": " + best.toString());
	}

}
