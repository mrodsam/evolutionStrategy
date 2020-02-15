package es;

import java.util.Random;

import optimization_es.*;

/**
 * Incluye dos métodos correspondientes a la implementación de los dos esquemas
 * posibles de mutación no correlacionada, paso único y N pasos.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Mutation {

	public static Double epsilon = 0.001;
	/*Los valores de ambos parámetros se inicializan en la clase principal*/
	public static Double tau = null;
	public static Double taup = null;

	/**
	 * Implementación de la mutación no correlacionada de paso único.
	 * 
	 * @param ind Individuo al que se le ha aplicado el operador de variación.
	 */
	public static void uncorrelatedMutationWOneStepSize(Individual ind) {
		Random r = new Random();

		Double[] currentValues = ind.getValues();
		Double[] newValues = new Double[Main.numberOfValues];

		Double currentSigma = ind.getStepSizes()[0];
		Double[] newSigma = new Double[1];
		newSigma[0] = currentSigma * Math.exp(tau * r.nextGaussian());

		if (newSigma[0] < epsilon) {
			newSigma[0] = epsilon;
		}

		for (int i = 0; i < currentValues.length; i++) {
			newValues[i] = currentValues[i] + newSigma[0] * r.nextGaussian();

			if (newValues[i] < Main.domain_min)
				newValues[i] = Main.domain_min;
			if (newValues[i] > Main.domain_max)
				newValues[i] = Main.domain_max;
		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);

	}

	/**
	 * Implementación de la mutación no correlacionada de N pasos.
	 * 
	 * @param ind Individuo al que se le ha aplicado el operador de variación.
	 */
	public static void uncorrelatedMutationWNStepSizes(Individual ind) {

		Random r = new Random();

		Double[] currentValues = ind.getValues();
		Double[] newValues = new Double[Main.numberOfValues];

		Double[] currentSigma = ind.getStepSizes();
		Double[] newSigma = new Double[Main.numberOfValues];

		Double taupProduct = taup * r.nextGaussian();

		for (int i = 0; i < Main.numberOfValues; i++) {
			newSigma[i] = currentSigma[i] * Math.exp(taupProduct + tau * r.nextGaussian());
			if (newSigma[i] < epsilon) {
				newSigma[i] = epsilon;
			}
			newValues[i] = currentValues[i] + newSigma[i] * r.nextGaussian();

			if (newValues[i] < Main.domain_min)
				newValues[i] = Main.domain_min;
			if (newValues[i] > Main.domain_max)
				newValues[i] = Main.domain_max;

		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);

	}

}
