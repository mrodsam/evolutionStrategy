package estrategia_evolutiva;

import java.util.Arrays;

public class Individual {

	Double[] values;
	Double[] stepSizes;
	Double fitness;

	public Individual() {

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
		this.fitness = displacedSphere();
	}

	public Double[] getStepSizes() {
		return stepSizes;
	}

	public void setStepSizes(Double[] stepSizes) {
		this.stepSizes = stepSizes;
	}
	
	public Double getFitness() {
		return fitness;
	}

	public Double displacedSphere() {

		double sum = 0;

		for (Double x : this.values) {
			sum += Math.pow(x - 10, 2);
		}

		return sum;
	}

	@Override
	public String toString() {
		return "Individual [values=" + Arrays.toString(values) + ", stepSizes=" + Arrays.toString(stepSizes) + "]";
	}

}
