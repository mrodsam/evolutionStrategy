package es;

import optimization_es.*;

public class Recombination {

	public static Individual localRecombinationDI(Individual parent1, Individual parent2) {
		Individual child = new Individual(Main.sphereFunction);
		Double x;
		Double y;
		Double[] z = new Double[Main.numberOfValues];
		Double[] sigma = new Double[parent1.getStepSizes().length];

		// Discreta
		for (int i = 0; i < Main.numberOfValues; i++) {
			double r = Math.random();
			x = parent1.getValues()[i];
			y = parent2.getValues()[i];

			if (r < 0.5) {
				z[i] = x;
			} else {
				z[i] = y;
			}

		}
		child.setValues(z);

		// Intermedia
		for (int i = 0; i < parent1.getStepSizes().length; i++) {
			x = parent1.getStepSizes()[i];
			y = parent2.getStepSizes()[i];

			sigma[i] = (x + y) / 2;
		}

		child.setStepSizes(sigma);

		return child;
	}

	public static Individual globalRecombinationDI() {

		Individual child = new Individual(Main.sphereFunction);
		Double[] z = new Double[Main.numberOfValues];
		Double[] sigma;
		if (Main.oneStep) {
			sigma = new Double[1];
		} else {
			sigma = new Double[Main.numberOfValues];
		}

		for (int i = 0; i < Main.numberOfValues; i++) {
			Individual parent1 = Main.getRandomParent();
			Individual parent2 = Main.getRandomParent();

			// Discreta
			double r = Math.random();
			Double x = parent1.getValues()[i];
			Double y = parent2.getValues()[i];

			if (r < 0.5) {
				z[i] = x;
			} else {
				z[i] = y;
			}

			// Intermedia
			if (i == 0 || parent1.getStepSizes().length > 1) {

				x = parent1.getStepSizes()[i];
				y = parent2.getStepSizes()[i];

				sigma[i] = (x + y) / 2;
			}

		}
		child.setValues(z);
		child.setStepSizes(sigma);

		return child;
	}

}
