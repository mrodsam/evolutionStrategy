package optimization_es;


import java.util.Scanner;

import es.*;

public class Menu {

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
				Main.sphereFunction = true;
				break;
			case 2:
				Main.sphereFunction = false;
				break;
			default:
				scInput.close();
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
				Main.oneStep = true;
				break;
			case 2:
				Main.oneStep = false;
				break;
			default:
				scInput.close();
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}

		System.out.println(
				"Introduzca el entero correspondiente al esquema de selección de supervivientes que desee utilizar: ");
		System.out.println("1. (mu, lambda)");
		System.out.println("2. (mu+lambda)");

		if (scInput.hasNextInt()) {
			input = scInput.nextInt();
			switch (input) {
			case 1:
				Main.selectionSchemeUnion = false;
				break;
			case 2:
				Main.selectionSchemeUnion = true;
				break;
			default:
				scInput.close();
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
				Main.localRecombination = true;
				break;
			case 2:
				Main.localRecombination = false;
				break;
			default:
				scInput.close();
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}

		System.out.println("Los valores de los parámetros por defecto son: ");
		System.out.println("Número de ejecuciones: " + Main.runs);
		System.out.println("Número de generaciones: " + Main.generations);
		System.out.println("Número de variables: " + Main.numberOfValues);
		System.out.println("Tamaño de la población: " + Main.populationSize);
		System.out.println("Número de desciendentes: " + Main.offspringSize);
		System.out.println("Épsilon: " + Mutation.epsilon);
		System.out.println("Tau: " + Mutation.tau);
		System.out.println("Tau': " + Mutation.taup);
		System.out.println();

		System.out.println(
				"Si desea cambiarlos, introduzca, en el mismo orden que se han mostrado, los nuevos valores separados por un espacio (todos los parámetros deben incluirse)");
		System.out.println("En caso contrario introduzca 'N'");

		if (scInput.hasNextInt()) {
			Main.runs = scInput.nextInt();
			Main.generations = scInput.nextInt();
			Main.numberOfValues = scInput.nextInt();
			Main.populationSize = scInput.nextInt();
			Main.offspringSize = scInput.nextInt();
			Mutation.epsilon = Double.valueOf(scInput.next());
			Mutation.tau = Double.valueOf(scInput.next());
			Mutation.taup = Double.valueOf(scInput.next());
		}
		scInput.close();
		return;
	}

}
