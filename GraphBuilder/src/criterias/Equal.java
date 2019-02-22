package criterias;

import tools.Criteria;

public class Equal extends Criteria {
	@Override
	public double getWeight(Double range, Double delay) {
		return range + delay;
	}
}
