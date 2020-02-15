package es;

import java.util.Random;

public class Mutation {

	static Double epsilon = 0.001;
	static Double tau;
	static Double taup;

	// Uncorrelated Mutation with One Step Size
	public static void uncorrelatedMutationWOneStepSize(Individual ind) {
		tau = 1.0 / Math.sqrt(Main.numberOfValues);
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
			
			if(newValues[i] < Main.domain_min)
				newValues[i] = Main.domain_min;
			if(newValues[i] > Main.domain_max)
				newValues[i] = Main.domain_max;
		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);
		
	}
	
	public static void uncorrelatedMutationWNStepSizes(Individual ind) {
		
		tau = 2.0 / Math.sqrt(2 * Math.sqrt(Main.numberOfValues));
		taup = 1.0 / Math.sqrt(2 * Main.numberOfValues);
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
			
			if(newValues[i] < Main.domain_min)
				newValues[i] = Main.domain_min;
			if(newValues[i] > Main.domain_max)
				newValues[i] = Main.domain_max;
			
			
		}
		ind.setStepSizes(newSigma);
		ind.setValues(newValues);
		
	}

}
