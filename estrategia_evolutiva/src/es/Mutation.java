package es;

import java.util.Random;

public class Mutation {

	static Double threshold = 0.01;

	// Uncorrelated Mutation with One Step Size
	public static void uncorrelatedMutationWOneStepSize(Individual ind) {
		Double tau = 1.0 / Math.sqrt(Main.numberOfValues);
		Random r = new Random();

		Double[] currentValues = ind.getValues();
		Double[] newValues = new Double[Main.numberOfValues];

		Double currentSigma = ind.getStepSizes()[0];
		Double[] newSigma = new Double[1];
		newSigma[0] = currentSigma * Math.exp(tau * r.nextGaussian());

		if (newSigma[0] < threshold) {
			newSigma[0] = threshold;
		}

		for (int i = 0; i < currentValues.length; i++) {
			newValues[i] = currentValues[i] + newSigma[0] * r.nextGaussian();
		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);
		
	}
	
	public static void uncorrelatedMutationWNStepSizes(Individual ind) {
		
		Double tau = 1.0 / Math.sqrt(Main.numberOfValues);
		Double taup = 1.0 / Math.sqrt(2 * Math.sqrt(Main.numberOfValues));
		Random r = new Random();

		Double[] currentValues = ind.getValues();
		Double[] newValues = new Double[Main.numberOfValues];

		Double[] currentSigma = ind.getStepSizes();
		Double[] newSigma = new Double[Main.numberOfValues];
		
		Double taupProduct = taup * r.nextGaussian();
		

		for (int i = 0; i < Main.numberOfValues; i++) {
			newSigma[i] = currentSigma[i] * Math.exp(taupProduct + tau * r.nextGaussian());
			if (newSigma[i] < threshold) {
				newSigma[i] = threshold;
			}
			newValues[i] = currentValues[i] + newSigma[i] * r.nextGaussian();
			
			
		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);
		
	}

}
