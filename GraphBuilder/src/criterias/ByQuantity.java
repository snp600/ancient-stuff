package criterias;

import tools.Criteria;

public class ByQuantity extends Criteria {
	@Override
	public double getWeight(Double range, Double delay) {
		return (double) 1.0;
	}
}