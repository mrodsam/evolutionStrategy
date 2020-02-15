package es;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	static Boolean sphereFunction = true; // true: esfera, false: Schwefel
	static Boolean oneStep = false; // True:1 paso, False: n pasos
	static Boolean selectionSchemeUnion = false; // true:mu+lambda, false:mu,lambda
	static Boolean localRecombination = false; // true:local, false:global

	static Integer runs = 20;
	static Integer generations = 2000;

	static Integer numberOfValues = 10;
	static Integer populationSize = 30;
	static Integer offspringSize = 200;

	static Double domain_min;
	static Double domain_max;

	static Double[] stepSize;
	static Double defaultStepSize;

	static LinkedList<Individual> population;
	static LinkedList<Individual> offspring;

	public static void main(String[] args) {

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
		} else {
			stepSize = new Double[Main.numberOfValues];
			for (int i = 0; i < stepSize.length; i++) {
				stepSize[i] = defaultStepSize;
			}
		}

		for (int i = 0; i < runs; i++) {
			System.out.println("Ejecución: " + i);
			Double solution = null;
			boolean first = true;
			long tStart = System.currentTimeMillis();
			initPopulation();
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
						System.out.println("Se ha alcanzado la solución: " + solution + " en la generación: " + j);
					} else if (getSolution().getFitness() < solution) {
						solution = getSolution().getFitness();
						System.out.println(
								"Se ha alcanzado una solución mejor (" + solution + ") en la generación: " + j);
					}

				}
			}
			long tEnd = System.currentTimeMillis();
			long difference = tEnd - tStart;
			System.out.println("La ejecución ha durado: " + difference / 1000 + " segundos.");
			
			if (solution == null)
				FileManagement.writeTimes(difference, getSolution().getFitness());
			else
				FileManagement.writeTimes(difference, solution);

		}
		double SR = Evaluation.computeSuccessRate();
		double MBF = Evaluation.computeMBF();
		double AES = Evaluation.computeAES();
		FileManagement.writeEvaluationFile(SR, MBF, AES);
		
		System.out.println("Evaluación de las "+runs+" ejecuciones:");
		System.out.println("SR: "+SR*100);
		System.out.println("MBF: "+MBF);
		System.out.println("AES: "+AES);

	}
	
	public static void menu() {
		int input;
		
		System.out.println("Introduzca el entero correspondiente al problema de optimización que desee resolver: ");
		System.out.println("1. Función de esfera desplazada");
		System.out.println("2. Función de Schwefel");
		
		Scanner scInput = new Scanner(System.in);
		if (scInput.hasNextInt()) {
			input = scInput.nextInt();
			switch (input) {
			case 1:
				sphereFunction = true;
				break;
			case 2:
				sphereFunction = false;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
		System.out.println("Introduzca el entero correspondiente al tipo de mutación que desee utilizar: ");
		System.out.println("1. Mutación no correlacionada de paso único");
		System.out.println("2. Mutación no correlacionada de N pasos");
		
		if (scInput.hasNextInt()) {
			input = scInput.nextInt();
			switch (input) {
			case 1:
				oneStep = true;
				break;
			case 2:
				oneStep = false;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
		System.out.println("Introduzca el entero correspondiente al esquema de selección de supervivientes que desee utilizar: ");
		System.out.println("1. (mu, lambda)");
		System.out.println("2. (mu+lambda)");
		
		if (scInput.hasNextInt()) {
			input = scInput.nextInt();
			switch (input) {
			case 1:
				selectionSchemeUnion = false;
				break;
			case 2:
				selectionSchemeUnion = true;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
		System.out.println("Introduzca el entero correspondiente al tipo de recombinación que desee utilizar: ");
		System.out.println("1. Recombinación local");
		System.out.println("2. Recombinación global");
		
		if (scInput.hasNextInt()) {
			input = scInput.nextInt();
			switch (input) {
			case 1:
				localRecombination = true;
				break;
			case 2:
				localRecombination = false;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
//		System.out.println("Los valores de los parámetros por defecto son: ");
//		System.out.println("Número de ejecuciones: "+runs);
//		System.out.println("Número de generaciones: "+generations);
//		System.out.println("Número de variables: "+numberOfValues);
//		System.out.println("Tamaño de la población: "+populationSize);
//		System.out.println("Número de desciendentes: "+offspringSize);
//		System.out.println("Épsilon: "+Mutation.epsilon);
//		System.out.println("Tau: "+Mutation.tau);
//		System.out.println("Tau': "+Mutation.taup);
//		System.out.println();
//		
//		System.out.println("Si desea cambiarlos, introduzca, en el mismo orden que se han mostrado, los nuevos valores separados por un espacio (todos los parámetros deben incluirse)");
//		
//		if(scInput.hasNext()) {
//			runs = scInput.nextInt();
//			generations = scInput.nextInt();
//			numberOfValues = scInput.nextInt();
//			populationSize = scInput.nextInt();
//			offspringSize = scInput.nextInt();
//		}
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

	}

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

	public static Individual getRandomParent() {
		Random rand = new Random();
		return population.get(rand.nextInt(population.size()));
	}

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
