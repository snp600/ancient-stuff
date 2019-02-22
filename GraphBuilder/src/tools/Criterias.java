package tools;

import criterias.ByDelay;
import criterias.ByQuantity;
import criterias.ByRange;
import criterias.Equal;

public enum Criterias {

	 RANGE (new ByRange()),
	 DELAY (new ByDelay()), // vertex' weight
	 QUANTITY (new ByQuantity()),
	 EQUAL_SIGNIFICANCE (new Equal()); // utility function
	 
	 private Criteria c;
	 
	 Criterias(Criteria c) {
		 this.c = c;
	 }
	 
	 public Double getWeight(Double range, Double delay) {
		 return c.getWeight(range, delay);
	 }

}
