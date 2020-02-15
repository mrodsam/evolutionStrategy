package optimization_es;

import java.util.LinkedList;
import java.util.Random;

import es.*;

/**
 * Clase principal donde se encuentran la definición de los valores de los
 * parámetros del algoritmo y la secuencia de pasos a seguir para la ejecución y
 * evaluación del mismo.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Main {

	public static Boolean sphereFunction = true; // true: esfera, false: Schwefel
	public static Boolean oneStep = true; // True:1 paso, False: n pasos
	public static Boolean selectionSchemeUnion = false; // true:mu+lambda, false:mu,lambda
	public static Boolean localRecombination = false; // true:local, false:global

	public static Integer runs = 20;
	public static Integer generations = 2000;

	public static Integer numberOfValues = 10;
	public static Integer populationSize = 30;
	public static Integer offspringSize = 200;

	public static Double domain_min;
	public static Double domain_max;

	public static Double[] stepSize;
	public static Double defaultStepSize = 1.0;

	public static LinkedList<Individual> population;
	public static LinkedList<Individual> offspring;

	public static void main(String[] args) {

		/*
		 * En caso de no introducir ningún argumento al ejecutar el programa se
		 * utilizarán los valores por defecto. En otro caso se mostrará un menú en el
		 * terminal permitiendo realizar una configuración personalizada.
		 */
		if (args.length == 1) {
			Menu.menu();
		} else {
			System.out.println("Se utilizará la siguiente configuración: ");
			System.out.println();
			System.out.println("Función esfera desplazada");
			System.out.println("Mutación no correlacionada de paso único");
			System.out.println("Selección de supervivientes (mu,lambda)");
			System.out.println("Recombinación global");
			System.out.println("Número de ejecuciones: " + runs);
			System.out.println("Número de generaciones: " + generations);
			System.out.println("Número de variables: " + numberOfValues);
			System.out.println("Tamaño de la población: " + populationSize);
			System.out.println("Número de desciendentes: " + offspringSize);
			System.out.println("Tamaño de paso de mutación: " + defaultStepSize);
			System.out.println("Épsilon: " + Mutation.epsilon);
		}

		initParams();

		for (int i = 0; i < runs; i++) {
			System.out.println("Ejecución: " + i);
			Double solution = null;
			boolean first = true;

			long tStart = System.currentTimeMillis();
			initPopulation();

			/* Secuencia de ejecución de la estrategia evolutiva */
			for (int j = 0; j < generations; j++) {
				FileManagement.writeProgressCurveValues(getSolution().getFitness(), j, i);
				generateOffspring();
				if (selectionSchemeUnion)
					SurvivorSelection.muPlusLambda();
				else
					SurvivorSelection.muLambda();
				if (isZero(getSolution().getFitness())) {
					if (first) {
						FileManagement.writeAESValues(j);
						solution = getSolution().getFitness();
						first = false;
						System.out.println("Se ha alcanzado la solución " + solution + " en la generación: " + j);
					} else if (getSolution().getFitness() < solution) {
						solution = getSolution().getFitness();
						System.out.println(
								"Se ha alcanzado una solución mejor (" + solution + ") en la generación: " + j);
					}

				}
			}
			long tEnd = System.currentTimeMillis();
			long difference = tEnd - tStart;
			System.out.println("La ejecución ha durado " + difference / 1000.0 + " segundos.");
			System.out.println();

			if (solution == null)
				FileManagement.writeTimes(difference, getSolution().getFitness());
			else
				FileManagement.writeTimes(difference, solution);

		}
		/* Evaluación de los resultados de todas las ejecuciones. */
		double SR = Evaluation.computeSuccessRate();
		double MBF = Evaluation.computeMBF();
		double AES = Evaluation.computeAES();
		FileManagement.writeEvaluationFile(SR, MBF, AES);

		System.out.println("Evaluación de las " + runs + " ejecuciones:");
		System.out.println("SR: " + SR * 100 + "%");
		System.out.println("MBF: " + MBF);
		System.out.println("AES: " + AES);

	}

	/**
	 * Método para inicializar los valores de los parámetros de la estrategia
	 * evolutiva.
	 */
	public static void initParams() {

		if (sphereFunction) {
			domain_min = -100.0;
			domain_max = 100.0;
			defaultStepSize = 1.0;
		} else {
			domain_min = -500.0;
			domain_max = 500.0;
			defaultStepSize = 5.0;
		}

		if (oneStep) {
			stepSize = new Double[1];
			stepSize[0] = defaultStepSize;
			if (Mutation.tau == null)
				Mutation.tau = 1.0 / Math.sqrt(Main.numberOfValues);
			System.out.println("Tau: " + Mutation.tau);
			System.out.println();
		} else {
			stepSize = new Double[Main.numberOfValues];
			for (int i = 0; i < stepSize.length; i++) {
				stepSize[i] = defaultStepSize;
				if (Mutation.tau == null) {
					Mutation.tau = 1.0 / Math.sqrt(2 * Math.sqrt(Main.numberOfValues));
				}
				if (Mutation.taup == null) {
					Mutation.taup = 1.0 / Math.sqrt(2 * Main.numberOfValues);
				}
				System.out.println("Tau: " + Mutation.tau);
				System.out.println("Taup: " + Mutation.tau);
				System.out.println();
			}
		}

	}

	/**
	 * Método para crear de forma aleatoria los individuos que conforman la
	 * población inicial.
	 */
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

	}

	/**
	 * Método para crear la descendencia.
	 * 
	 * En primer lugar se genera un hijo, utilizando el método de recombinación
	 * elegido. Posteriormente se muta, según el esquema de mutación escogido. Este
	 * proceso se repite lambda veces.
	 */
	public static void generateOffspring() {

		offspring = new LinkedList<Individual>();
		Individual parent1;
		Individual parent2;
		Individual child;

		for (int i = 0; i < offspringSize; i++) {

			if (localRecombination) {
				parent1 = getRandomParent();
				parent2 = getRandomParent();

				child = Recombination.localRecombinationDI(parent1, parent2);
			} else {
				child = Recombination.globalRecombinationDI();
			}

			if (oneStep)
				Mutation.uncorrelatedMutationWOneStepSize(child);
			else
				Mutation.uncorrelatedMutationWNStepSizes(child);

			offspring.add(child);

		}

	}

	/**
	 * Elección aleatoria de un individuo de la población actual para utilizarlo en
	 * la recombinación.
	 * 
	 * @return Individuo padre
	 */
	public static Individual getRandomParent() {
		Random rand = new Random();
		return population.get(rand.nextInt(population.size()));
	}

	/**
	 * Búsqueda del individuo mejor adaptado entre la población actual.
	 * 
	 * @return Individuo con el mejor (mínimo) valor de adaptación
	 */
	public static Individual getSolution() {
		int count = 0;
		Individual best = new Individual(sphereFunction);
		for (Individual individual : population) {
			count++;
			if (count == 1 || individual.getFitness() < best.getFitness()) {
				best = individual;
			}

		}

		return best;
	}

	/**
	 * Comparación del valor obtenido en la función objetivo con cierto umbral
	 * especificado para determinar si se considera una solución válida para el
	 * problema propuesto.
	 * 
	 * @param solution Valor de adaptación del individuo mejor adaptado de la
	 *                 población actual
	 * @return true si el valor es una solución o false en caso contrario
	 */
	public static boolean isZero(Double solution) {
		Double threshold = null;
		if (sphereFunction) {
			threshold = 0.0001;
		} else {
			threshold = 0.01;
		}

		return solution >= 0 && solution <= threshold;

	}

}
