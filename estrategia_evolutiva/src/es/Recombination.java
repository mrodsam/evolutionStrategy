package es;

import optimization_es.*;

/**
 * Incluye dos métodos correspondientes a la implementación de los dos esquemas
 * posibles de recombinación, global y local.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Recombination {

	/**
	 * Implementación del método de recombinación local: se utilizan dos padres
	 * escogidos aleatoriamente para crear un hijo.
	 * 
	 * Recombinación discreta para la parte de variables del individuo hijo: para
	 * cada coordenada del hijo se copia aleatoriamente el valor de la misma
	 * coordenada de un padre u otro.
	 * 
	 * Recombinación itnermedia para la parte de parámetros del individuo hijo: para
	 * cada coordenada del hijo se promedian los valores de las coordenadas
	 * correspondientes de los padres.
	 * 
	 * @param parent1 Individuo que cumple la función del primer padre
	 * @param parent2 Individuo que cumple la función del segundo padre
	 * @return Individuo hijo, resultado de la recombinación de ambos padres
	 */
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

	/**
	 * Implementación del método de recombinación global: se escogen aleatoriamente
	 * dos padres para calcular cada coordenada del hijo.
	 * 
	 * Recombinación discreta para la parte de variables del individuo hijo: para
	 * cada coordenada del hijo se copia aleatoriamente el valor de la misma
	 * coordenada de un padre u otro.
	 * 
	 * Recombinación itnermedia para la parte de parámetros del individuo hijo: para
	 * cada coordenada del hijo se promedian los valores de las coordenadas
	 * correspondientes de los padres.
	 * 
	 * @return Individuo hijo, resultado de la recombinación de los padres
	 */
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
