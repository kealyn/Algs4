/*
 * Given a composite system comprised of randomly distributed insulating and metallic materials: 
 * what fraction of the materials need to be metallic so that the composite system is an electrical conductor? 
 * Given a porous landscape with water on the surface (or oil below), under what conditions will the water 
 * be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined 
 * an abstract process known as percolation to model such situations.
 * 
 * This program creates a single virtual root on top of the matrix and another root on bottom of the matrix,
 * checking for percolation is then equivalently to check whether the two roots are connected.
 * 
 * This program has dependencies on the libraries developed by Princeton and are kept in edu.princeton.cs.algs4 package.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner; 
import java.io.*; 

public class Percolation 
{      
    private boolean[][] m;                  // Stats matrix of the two-dimensional sites system
    private final int n;                    // dimension    
    private WeightedQuickUnionUF uf;        // union-find
    private WeightedQuickUnionUF ufIsFull;  // union-find for isFull
    private int top;                        // index of the top virtual root
    private int bottom;                     // index of the bottom virtual root
    private int openSites;                  // number of open sites
        
    // Create n-by-n grid, with all sites blocked
    public Percolation(int n)                
    {
        if (n<=0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        // Initialize all sites to be blocked (false).
        // Extra one column and row are left blank intentionally for indexing purposes
        this.m = new boolean[n+1][n+1]; 
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= n; j++)
                this.m[i][j] = false;       
       
        this.openSites = 0;
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n*n+2); // The extra two are for two virtual roots
        this.ufIsFull = new WeightedQuickUnionUF(n*n+1);
        this.top = 0;
        this.bottom = n*n + 1;  
    }
    
    // Converter that converts a two-dimensional coordinates to the array index
    private int indexConverter(int p, int q)
    {
        return (p-1)*n + q;
    }
    
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {       
       if (posOutOfBounds(row, col))
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       
       // Check adjacent sites and create unions if they are open as well
       if (this.m[row][col] == false)
       {
           this.openSites++;
           this.m[row][col] = true;
       }
       else
       {
           return;
       }
       
       //System.out.println("["+row+","+col+"]" + " is open.");
       
       // Build virtual links
       if (row == 1)
       {
           //System.out.println("Connecting root top.");
           this.uf.union(top, indexConverter(row,col));
           this.ufIsFull.union(top, indexConverter(row,col));
       }   
       
       if (row == n)
       {
           //System.out.println("Connecting root bottom.");
           this.uf.union(bottom, indexConverter(row,col));
       }
       
       // Explore left neighbors
       if (col > 1 && this.isOpen(row, col - 1))
       {
           //System.out.println("3->Union(["+row+","+col+"]" + "["+row+","+(col-1)+"])");
           this.uf.union(indexConverter(row,col), indexConverter(row,col-1));
           this.ufIsFull.union(indexConverter(row,col), indexConverter(row,col-1));
       }
               
       // Explore right neighbors
       if (col < n && this.isOpen(row, col + 1))
       {
           //System.out.println("4->Union(["+row+","+col+"]" + "["+row+","+(col+1)+"])");
           this.uf.union(indexConverter(row,col), indexConverter(row,col+1));
           this.ufIsFull.union(indexConverter(row,col), indexConverter(row,col+1));
       }
       
       // Explore upper neighbors
       if (row > 1 && this.isOpen(row - 1, col))
       {
           //System.out.println("1->Union(["+row+","+col+"]" + "["+(row-1)+","+col+"])");
           this.uf.union(indexConverter(row,col), indexConverter(row-1,col));
           this.ufIsFull.union(indexConverter(row,col), indexConverter(row-1,col));
       }   
       
       // Explore lower neighbors
       if (row < n && this.isOpen(row + 1, col))
       {       
           //System.out.println("2->Union(["+row+","+col+"]" + "["+(row+1)+","+col+"])");
           this.uf.union(indexConverter(row,col), indexConverter(row+1,col));
           this.ufIsFull.union(indexConverter(row,col), indexConverter(row+1,col));
       }          
       
       
   }
   
   // is site (row, col) open?
   public boolean isOpen(int row, int col)  
   {
       if (posOutOfBounds(row, col))
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       return this.m[row][col] == true;
   }
   
   // is site (row, col) full?
   public boolean isFull(int row, int col)  
   {       
       if (posOutOfBounds(row, col))
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       return this.ufIsFull.connected(top, indexConverter(row, col));
   }
   
   // Method that checks whether the coordinate is out of bound
   private boolean posOutOfBounds(int row, int col)
   {
       return row <= 0 || col <= 0 || row > n || col > n;
   }
   
   // number of open sites
   public int numberOfOpenSites()       
   {
       return this.openSites;
   }
   
   // does the system percolate?
   public boolean percolates()              
   {
       return this.uf.connected(top, bottom);
   }
   
   public static void main(String[] args) throws FileNotFoundException,IOException    // test client (optional)
   {       
       // Test cases
       Scanner scanner = new Scanner(new File("input6.txt"));
       int n = scanner.nextInt();
       Percolation p = new Percolation(n);  
       while(scanner.hasNextInt()){
           int x = scanner.nextInt();
           int y = scanner.nextInt();
           p.open(x,y);
       }
       
       System.out.println(p.isFull(2,1)); 
       
       Percolation p2 = new Percolation(3);  
       p2.open(1,3);
       p2.open(2,3);
       p2.open(3,3);
       p2.open(3,1);
       System.out.println(p2.percolates());
       System.out.println(p2.isFull(3,1));           
   }
   
}

