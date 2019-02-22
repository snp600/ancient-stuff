package output;

public class OutputMethods {
	public static double highEps = 1000.0; //for higher degrees => eps = 0.001
	public static double lowEps = 100.0; //for lower degrees => eps = 0.01
	
	public static String increasingOrder(double[] c) {
		String output = new String();
		for (int i = 0; i < c.length/2; i++)
			c[i] = Math.round(c[i]*lowEps)/lowEps;
		for (int i = c.length/2; i < c.length; i++)
			c[i] = Math.round(c[i]*highEps)/highEps;
		if (Math.abs(c[0]) > 1.0/lowEps)
			output = Double.toString(c[0]);
		if (Math.abs(c[1]) > 1.0/lowEps) {
			if (c[1] > 0)
				output += "+";
			if (Math.abs(c[1] - 1.0) > 1.0/lowEps)
				output += Double.toString(c[1]); 
			output += "x";
		}
		for (int i = 2; i < c.length; i++) {
			if (i > c.length/2) {
				if (Math.abs(c[i]) > 1.0/highEps) {
					if (c[i] > 0)
						output += "+";
					if (Math.abs(c[i] - 1.0) > 1.0/highEps)
						output += Double.toString(c[i]); 
					output += "x^" + Integer.toString(i);
				}
			}
			else {
				if (Math.abs(c[i]) > 1.0/lowEps) {
					if (c[i] > 0)
						output += "+";
					if (Math.abs(c[i] - 1.0) > 1.0/lowEps)
						output += Double.toString(c[i]); 
					output += "x^" + Integer.toString(i);
				}
			}
		}
		if (output.startsWith("+")) 
			output = output.substring(1, output.length());
		return output;
	}
}
