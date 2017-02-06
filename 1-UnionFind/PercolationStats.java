import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double [] samples;
    private final int T;
    
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if (n <= 0 || trials <= 0)
       {
           throw new java.lang.IllegalArgumentException();
       }
       
       //System.out.println("Perform " + trials + " studies of " + n + " by " + n + " percolations.");
       this.samples = new double[trials];
       this.T = trials;
       for (int i = 0; i < trials; i++)
       {
           Percolation p = new Percolation(n);
           
           int counter = 0;
           while (true)
           {
               int x=StdRandom.uniform(n) + 1;
               int y=StdRandom.uniform(n) + 1;
               if (!p.isFull(x,y) && !p.isOpen(x,y))
               {
                   p.open(x,y);
                   counter++;
                   if (p.percolates())
                   {
                       this.samples[i] = (double)counter / (n*n);
                       break;
                   }
               }
           }
           
       }
   }
   public double mean()                          // sample mean of percolation threshold
   {
       return StdStats.mean(this.samples);
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(this.samples);
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return this.mean() - (1.96 * this.stddev() / Math.sqrt(T));
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return this.mean() + (1.96 * this.stddev() / Math.sqrt(T));
   }

   public static void main(String[] args)        // test client (described below)
   {
       PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       System.out.println("mean = " + ps.mean());
       System.out.println("stddev = " + ps.stddev());
       System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
   }
}