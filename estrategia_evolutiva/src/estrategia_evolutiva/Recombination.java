package estrategia_evolutiva;

public class Recombination {

	// Variables
	public static Individual globalRecombination(Individual parent1, Individual parent2) {
		Individual child = new Individual();
		Double x;
		Double y;
		Double[] z = new Double[Main.numberOfValues];
		Double[] sigma = new Double[1];

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
		double r = Math.random();
		x = parent1.getStepSizes()[0];
		y = parent2.getStepSizes()[0];

		sigma[0] = (x + y) / 2;
		
		child.setStepSizes(sigma);

		return child;
	}


}
