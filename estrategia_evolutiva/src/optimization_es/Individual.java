package optimization_es;



import java.util.Arrays;

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
	
	public Double getFitness() {
		if (sphereFunction)
			return displacedSphere();
		else
			return SchwefelFunction();
		
	}

	public Double displacedSphere() {

		double sum = 0;

		for (Double x : this.values) {
			sum += Math.pow(x - 10, 2);
		}

		return sum;
	}
	
	public Double SchwefelFunction() {
		Double alpha = 418.9829;
		double sum = 0;
		
		for (Double x : this.values) {
			sum += ( - x * Math.sin(Math.sqrt(Math.abs(x)))); 
		}
		
		return alpha*Main.numberOfValues+sum;
	}

	@Override
	public String toString() {
		return "Individual [values=" + Arrays.toString(values) + ", stepSizes=" + Arrays.toString(stepSizes) + "]";
	}

}
