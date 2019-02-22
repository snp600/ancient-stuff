package criterias;

import tools.Criteria;

public class ByDelay extends Criteria {

	@Override
	public double getWeight(Double range, Double delay) {
		return delay;
	}

}
