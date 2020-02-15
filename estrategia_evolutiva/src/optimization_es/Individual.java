package optimization_es;

import java.util.Arrays;

/**
 * Representación de un individuo de la población.
 * 
 * Cada individuo se define por un conjunto de n variables reales, 1 o n tamaños
 * de paso de mutación y la función objetivo que determinará su valor de
 * adaptación.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Individual {

	private Double[] values;
	private Double[] stepSizes;
	private Boolean sphereFunction;

	public Individual(Boolean sphereFunction) {
		this.sphereFunction = sphereFunction;
	}

	public Individual(Double[] values, Double[] stepSizes) {
		this.values = values;
		this.stepSizes = stepSizes;
	}

	public Double[] getValues() {
		return values;
	}

	public void setValues(Double[] values) {
		this.values = values;

	}

	public Double[] getStepSizes() {
		return stepSizes;
	}

	public void setStepSizes(Double[] stepSizes) {
		this.stepSizes = stepSizes;
	}

	/**
	 * Cálculo del valor de adaptación.
	 * 
	 * Depende de la función objetivo escogida.
	 * 
	 * @return Valor de adaptación
	 */
	public Double getFitness() {
		if (sphereFunction)
			return displacedSphere();
		else
			return SchwefelFunction();

	}

	/**
	 * Implementación de la función de esfera desplazada.
	 * 
	 * @return Evaluación de la función para un cierto conjunto de variables.
	 */
	public Double displacedSphere() {

		double sum = 0;

		for (Double x : this.values) {
			sum += Math.pow(x - 10, 2);
		}

		return sum;
	}

	/**
	 * Implementación de la función de Schwefel.
	 * 
	 * @return Evaluación de la función para un cierto conjunto de variables.
	 */
	public Double SchwefelFunction() {
		Double alpha = 418.9829;
		double sum = 0;

		for (Double x : this.values) {
			sum += (-x * Math.sin(Math.sqrt(Math.abs(x))));
		}

		return alpha * Main.numberOfValues + sum;
	}

	@Override
	public String toString() {
		return "Individual [values=" + Arrays.toString(values) + ", stepSizes=" + Arrays.toString(stepSizes) + "]";
	}

}
