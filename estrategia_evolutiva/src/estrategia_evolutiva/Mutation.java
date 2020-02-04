package estrategia_evolutiva;

import java.util.Random;

public class Mutation {

	static Double threshold = 0.001;

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

}
