package optimization_es;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Evaluation {
	/**
	 * Cálculo de la tasa de éxito
	 * 
	 * @param optimalSolution Solución obtenida al finalizar la ejecución del
	 *                        algoritmo
	 * @return Tasa de éxito
	 */
	public static Double computeSuccessRate() {
		Double sr;

		File file = new File(Paths.get("./Evaluation") + "/times.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		Double runs = 0.0;
		Double successfulRuns = 0.0;
		while (sc.hasNextLine()) {
			if (!sc.hasNext())
				break;
			runs++;
			sc.next();
			if (Main.isZero(Double.valueOf(sc.next())))
				successfulRuns++;
		}
		sr = (successfulRuns / runs);

		return sr;
	}

	/**
	 * Cálculo del mejor fitness promedio a partir de las soluciones obtenidas en
	 * varias ejecuciones del algoritmo
	 * 
	 * @return Mejor fitness promedio
	 */
	public static Double computeMBF() {
		Double mbf;

		File file = new File(Paths.get("./Evaluation") + "/times.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		Double total = 0.0;
		Double runs = 0.0;
		while (sc.hasNextLine()) {
			if (!sc.hasNext())
				break;
			runs++;
			sc.next();
			total += Double.valueOf(sc.next());
		}
		mbf = total / runs;

		return mbf;
	}

	/**
	 * Cálculo del AES (Average number of Evaluations to a Solution)
	 * 
	 * @return AES
	 */
	public static Double computeAES() {

		Double aes;

		File file = new File(Paths.get("./Evaluation") + "/aes.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			return 0.0;
		}

		double successfulRuns = 0;
		double generations = 0;
		while (sc.hasNextLine()) {
			if (!sc.hasNext())
				break;
			generations += Double.parseDouble(sc.next());
			successfulRuns++;
		}
		aes = (generations * Main.offspringSize) / successfulRuns;
		sc.close();
		return aes;
	}
}
