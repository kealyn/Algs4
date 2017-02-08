import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner; 
import java.io.*; 
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{	
	private Item[] deque;
	private int tail;
	private int head;
	private int N = 10;
	
	// construct an empty deque
	public Deque()
	{
		deque = (Item[]) new Object[N];
		tail = N / 2;
		head = tail;
	}
	
	// is the deque empty?
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	// return the number of items on the deque
	public int size()
	{
		return tail - head;
	}
	
	
	// add the item to the end
	public void addLast(Item item)          
	{
        if (item == null) throw new java.lang.NullPointerException();
        
        // Check capacity
        if (tail == N - 1)
        {
        	// Double the capacity of the queue
        	Item[] tempDeque = (Item[]) new Object[N*2];
        	
        	// Copy the items into the new deque
        	for (int i = 0; i < N; i++)
        	{
        		tempDeque[i] = deque[i];
        	}
        	
        	// Update N
        	N = N*2;
        	deque = tempDeque;
        }
        
        deque[tail++] = item;
	}
	
	// add the item to the front
    public void addFirst(Item item)           
    {
        if (item == null) throw new java.lang.NullPointerException();
        
        // Check capacity
        if (head == 0)
        {
        	// Double the capacity of the queue
        	Item[] tempDeque = (Item[]) new Object[N*2];
        	
        	// Copy the items into the new deque
        	for (int i = 0; i < N; i++)
        	{
        		tempDeque[i+N] = deque[i];
        	}        	       	
        	
        	// Update head and tail
        	head += N;
        	tail += N;
        	
        	// Update N
        	N = N*2;
        	deque = tempDeque;
        }
        
        deque[--head] = item;
       
    }
    
    // remove and return the item from the front
    public Item removeFirst()                
    {
    	if (size() <= 0)    	
    		throw new java.util.NoSuchElementException();
    	Item result = deque[head++];
    	deque[head - 1] = null;
    	return result;
    }
    
    // remove and return the item from the end
    public Item removeLast()                 
    {
    	if (size() <= 0)
    		throw new java.util.NoSuchElementException();    	
    	Item result = deque[--tail];
    	return result;
    }
    
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()         
    {
        return new DequeIterator();
    }
    
    // DequeIterator class
    private class DequeIterator implements Iterator<Item>
    {
    	private int idx = head;
        public boolean hasNext() { return idx != tail; }
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next()
        {
            if (size() == 0)            
            	// No more elements to return
            	throw new java.util.NoSuchElementException();
            
            // Get from head
            Item result = deque[idx++];
        	return result;
        }
    }
    
    // Testings
    public static void main(String[] args)
    {
    	Deque deque = new Deque();
    	deque.addFirst(1);
        deque.addLast(2);
    	
        Iterator it1 = deque.iterator();
        
        
        deque.addLast(3);
        Iterator it2 = deque.iterator();
        while (it1.hasNext())
    	{
    		System.out.println(it1.next());    	
    	}
        
        
        
        
        
        
    	System.out.println("Size: " + deque.size()); // 5
    	
    	
    	/*deque.addLast(10);
    	deque.addLast(3);
    	deque.addLast(3);
    	deque.addLast(3);
    	deque.addLast(3);
    	deque.addLast(3);
    	deque.addLast(3);
    	deque.addLast(3);
    	
    	System.out.println(deque.removeFirst());
    	System.out.println(deque.removeLast());
    	System.out.println(deque.size()); // 11

    	System.out.println("====");
    	while (deque.iterator().hasNext())
    	{
    		System.out.println(deque.iterator().next());    	
    	}*/
    }
}