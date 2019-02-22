package algo;

/**
 * Algorithm is based on "finite differences"
 * @author Karachev Alexey
 */
import java.util.StringTokenizer;

public class Algorithm {
	private double[][] pyramid;
	private double[]   approximation;
	private int        N;
	
	public Algorithm(String sequence) {
		StringTokenizer numbers = new StringTokenizer(sequence, " \t\n\r");
		N = numbers.countTokens();
		pyramid = new double[N][];
		for (int i = 0; i < N; i++) {
			pyramid[i] = new double[N - i];
			pyramid[0][i] = Double.parseDouble(numbers.nextToken());
		}
		coverFiniteDifference();

	}
	public Algorithm(double[] sequence) {
		N = sequence.length;
		pyramid = new double[N][];
		for (int i = 0; i < N; i++) 
			pyramid[i] = new double[N - i];
		pyramid[0] = sequence;
		coverFiniteDifference();
	}
	public void coverFiniteDifference() {
		for (int i = 1; i < N; i++)
			for (int j = 0; j < N - i; j++)
				pyramid[i][j] = pyramid[i - 1][j + 1] - pyramid[i - 1][j];
	}
    public double[][] coverCnK(int N) { //without division on k! 
        double[][] cnk = new double[N][]; //triangular array
        for (int i = 0; i < N; i++) {
            cnk[i] = new double[i + 2];
            cnk[i][0] = 1;
            cnk[i][i + 1] = 0;
        }
        for (int i = 1; i < N; i++)
            for (int j = 1; j < i + 1; j++)
                cnk[i][j] = cnk[i - 1][j] - i * cnk[i - 1][j - 1];
        return cnk;
    }
    
    public double[] makeApproximation() { // d == pyramid
        double[] approximation = new double[N + 1];
        double k = 1; //factorial
        double[][] cnk = coverCnK(N);
        approximation[0] = pyramid[0][0];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++)
                approximation[i - j] += pyramid[i][0] * cnk[i - 1][j] / k; 
            k *= (i + 1);
        }
        this.approximation = approximation;
        return approximation;
    }
    
	public double[][] getPyramid() {
		return pyramid;
	}
	public double[] getApproximation() {
		return approximation;
	}

}
