package criterias;

import tools.Criteria;

public class ByRange extends Criteria {

	@Override
	public double getWeight(Double range, Double delay) {
		return range;
	}

}
