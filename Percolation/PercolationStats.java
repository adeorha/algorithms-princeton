/**
 * 
 */

/**
 * @author aditya
 *
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private int[] array_count;
	private int size;
	private int no_of_trials;
	
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	{
		if((n<=0) || (trials<=0))
			throw new java.lang.IllegalArgumentException();
		int row, col;
		Percolation[] Percolation_obj;
		size = n*n;
		no_of_trials = trials;
		array_count = new int[trials];
		Percolation_obj = new Percolation[trials];
		for(int i=0; i<trials; i++){
			Percolation_obj[i] = new Percolation(n);
			while(!Percolation_obj[i].percolates())
			{
				row = StdRandom.uniform(1, n+1);
				col = StdRandom.uniform(1, n+1);
				Percolation_obj[i].open(row, col);
			//	System.out.println(Percolation_obj[i].isFull(row, col) + "LALA" +i+""+row+""+col);
			}
			array_count[i] = Percolation_obj[i].numberOfOpenSites();
		}
	}
	public double mean()                          // sample mean of percolation threshold
	{
		return StdStats.mean(array_count)/size;
	}
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(array_count)/size;
	}
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		return mean() - ((1.96 * stddev()) / Math.sqrt(no_of_trials));
	}
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		return mean() + ((1.96 * stddev()) / Math.sqrt(no_of_trials));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello, World");
		int a, b;
		a = Integer.parseInt(args[0]);
		b = Integer.parseInt(args[1]);
		PercolationStats PercolationStatsObj = new PercolationStats(a,b);
		System.out.println("Mean is " + PercolationStatsObj.mean());
		System.out.println("Deviation is " + PercolationStatsObj.stddev());
		System.out.println("Confidence_Lo is " + PercolationStatsObj.confidenceLo());
		System.out.println("Confidence_Hi is " + PercolationStatsObj.confidenceHi());


	}

}
