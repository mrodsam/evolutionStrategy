package optimization_es;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import es.*;

public class FileManagement {
	/**
	 * Almacenamiento en un archivo de texto del tiempo que tarda en realizarse cada
	 * ejecución del algoritmo y del mejor valor de adaptación obtenido en dicha
	 * ejecución.
	 * 
	 * @param executionTime Duración de una ejecución
	 * @param bestFitness       Mejor valor de adaptación al finalizar la ejecución
	 */
	public static void writeTimes(long executionTime, Double bestFitness) {
		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/times.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(executionTime + "\t");
			pw.println(bestFitness);
			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de tiempos: " + e.getMessage());
		}

	}

	/**
	 * Almacenamiento en un archivo de texto de la tasa de éxito y el mejor fitness
	 * promedio. También se incluyen la solución óptima conocida para el problema y
	 * los parámetros utilizados para la ejecución del algoritmo
	 * 
	 * @param sr  Tasa de éxito
	 * @param MBF Mejor fitness promedio
	 */
	public static void writeEvaluationFile(Double sr, Double MBF, Double aes) {

		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/evaluation.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sr + "\t");
			pw.print(MBF + "\t");
			pw.print(aes + "\t");
			pw.print(Main.getSolution() + "\t");
			if(Main.sphereFunction) {
				pw.print("esfera" + "\t");
			}else {
				pw.print("Schwefel" + "\t");
			}
			pw.print(Main.populationSize + "\t");
			pw.print(Main.offspringSize + "\t");
			pw.print(Main.defaultStepSize + "\t");
			if(Main.oneStep) {
				pw.print("oneStep" + "\t");
				pw.print("tau:"+Mutation.tau+ "\t");
			}else {
				pw.print("NSteps" + "\t");
				pw.print("tau:"+Mutation.tau+ "\t");
				pw.print("taup:"+Mutation.taup+ "\t");
			}
			pw.print("epsilon:"+Mutation.epsilon+"\t");
				
			if(Main.selectionSchemeUnion) {
				pw.print("muUnionLambda" + "\n");
			}else {
				pw.print("mu,Lambda" + "\n");
			}
			
			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de evaluación: " + e.getMessage());
		}

	}

	/**
	 * Almacenamiento en varios archivos de texto (uno por ejecución) del mejor
	 * valor de adaptación obtenido en cada generación para representar
	 * posteriormente las curvas de progreso.
	 * 
	 * @param fitness    Mejor valor de adaptación
	 * @param generation Número de generaciones
	 * @param execution  Número de ejecución
	 */
	public static void writeProgressCurveValues(Double fitness, int generation, int execution) {
		try {
			File file = new File(Paths.get("./Evaluation") + "/progressCurves" + execution + ".txt");
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(generation + "\t");
			pw.print(fitness);
			pw.println();

			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de curvas de progreso: " + e.getMessage());
		}
	}

	/**
	 * Almacenamiento en un archivo de texto del número de generaciones hasta
	 * alcanzar el óptimo global para el cálculo del AES
	 * 
	 * @param generations Número de generaciones
	 */
	public static void writeAESValues(int generations) {
		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/aes.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(generations);
			pw.println();

			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo aes.txt: " + e.getMessage());
		}
	}
}
