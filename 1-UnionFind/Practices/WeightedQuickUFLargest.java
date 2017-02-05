/*
 * Union-find with specific canonical element. Add a method findLargest() to the union-find data type so that 
 * findLargest(i) returns the largest element in the connected component containing i. The operations, union(), 
 * connected(), and findLargest() should all take logarithmic time or better.
 * 
 * For example, if one of the connected components is {1,2,6,9}, then the find() method should return 9 
 * for each of the four elements in the connected components.
 */

import java.io.*;

public class WeightedQuickUFLargest
{
    int N;           // capacity
    int [] data;     // data array
    int [] roots;    // roots of data, i.e. component identifiers
    int [] sizes;    // sizes of the components
    int [] largests; // largest elements in each component
    
    public WeightedQuickUFLargest(int capacity, int [] input)
    {
        if (capacity <= 0)
        {
            // Capacity can only be positive
            throw new java.lang.IllegalArgumentException();
        }
        this.data = new int[capacity];
        this.roots = new int[capacity];
        this.sizes = new int[capacity];
        this.largests = new int[capacity];
        this.N = capacity;
        
        // Initialize
        for (int i = 0; i < capacity; i++)
        {
            data[i] = input[i];
            roots[i] = i;
            largests[i] = i;
            sizes[i] = 1;
        }
    }
    
    // Check if p and q are connected
    public boolean Connected(int p, int q)
    {
        return this.Find(p) == this.Find(q);
    }
    
    // Return the number of connected components
    public int Count()
    {
        return N;        
    }
    
    // Find the index of the root for element p
    public int Find(int p)
    {
        while (this.roots[p] != p)
        {
            this.roots[p] = this.roots[this.roots[p]];
            this.largests[p] = this.largests[this.roots[p]];
            p = this.roots[p];
        }
        return p;
    }
    
    // Return the largest element in component that the given element belongs to
    public int FindLargestElementInComponent(int e)
    {
        return this.largests[this.Find(e)];
    }
    
    // Union p and q
    public void Union(int p, int q)
    {
        int rootp = this.Find(p);
        int rootq = this.Find(q);
        if (rootp == rootq) return;
        
        // Update largest element
        if (p > q)
        {
            this.largests[rootq] = p;
        }
        else
        {
            this.largests[rootp] = q;
        }
        
        // Merge to the smaller tree
        if (this.sizes[rootp] > this.sizes[rootq])
        {
            this.roots[rootp] = rootq;            
            this.sizes[rootq] += this.sizes[rootp];
        }
        else
        {
            this.roots[rootq] = rootp;
            this.sizes[rootp] += this.sizes[rootq];
        }
        
        N--;
    }
    
    public static void main(String[] args)
    {
        // Simple test case
        int N = 7;
        int [] input = new int[N];
        for (int i = 0; i < N; i++)
        {
            input[i] = i;
        }
                
        WeightedQuickUFLargest uf = new WeightedQuickUFLargest(N, input);
        uf.Union(1,2);
        uf.Union(1,3);
        uf.Union(5,6);
        uf.Union(0,6);
        System.out.println(uf.Connected(2,3));
        System.out.println(uf.FindLargestElementInComponent(2));
        System.out.println(uf.FindLargestElementInComponent(0));
    }
    
}

