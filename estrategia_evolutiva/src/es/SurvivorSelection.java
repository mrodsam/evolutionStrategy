package es;

import java.util.LinkedList;

import optimization_es.*;

/**
 * Incluye dos métodos correspondientes a la implementación de los dos esquemas
 * posibles de selección de supervivientes, $(\mu,\lambda)$ y $(\mu+\lambda)$.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class SurvivorSelection {

	/**
	 * Implementación del esquema de selección de supervivientes (mu,lambda): se
	 * escogen los mu individuos con mejor valor de adaptación de entre los lambda
	 * descendientes.
	 */
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

		Main.population = newPopulation;

	}

	/**
	 * Implementación del esquema de selección de supervivientes (mu+lambda): se
	 * escogen los mu individuos con mejor valor de adaptación de entre la unión de
	 * los mu individuos de la población actual y los lambda descendientes.
	 */
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

		Main.population = newPopulation;

	}

}
