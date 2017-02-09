import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner;
import java.io.*; 
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item>
{
	private QNode<Item> head;
	private int N = 0;
	
	// construct an empty randomized queue
	public RandomizedQueue()
	{
	}
	
	// is the queue empty?
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	// return the number of items on the queue
	public int size()
	{
		return N;
	}
	
	// add the item
	public void enqueue(Item item)
	{
		QNode<Item> newNode = new QNode<Item>(item);
		if (N == 0)
		{
			// First node
			head = newNode;
			N++;
		}
		else
		{
			QNode<Item> it = head;
			while (it.next != null) it = it.next;
			it.next = newNode;
			N++;
		}
	}
	
	// remove and return a random item
	public Item dequeue()
	{
		if (size() == 0)
			throw new java.util.NoSuchElementException();
		int r = StdRandom.uniform(N);
		
		Item result = null;
		if (r == 0)
		{
			// Corner case, remove head
			result = (Item) head.item;
			head = head.next;			
		}
		else
		{
			QNode<Item> node = head;
			QNode<Item> prevNode = head;
			for (int i = 0; i < r; i++)
			{
				prevNode = node;
				node = node.next;				
			}
			prevNode.next = node.next;
			result = (Item) node.item;
		}	
		
		N--;
		return result;
	}
	
	// return (but do not remove) a random item
	public Item sample()
	{
		if (size() == 0)
			throw new java.util.NoSuchElementException();
		
		int r = StdRandom.uniform(N);
		QNode<Item> node = head;
		for (int i = 0; i < r; i++)
		{
			node = node.next;
		}
		return (Item)node.item;
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator()
	{
		return new RndQueueIterator<Item>(head);	
	}
	
	// Inner class for QNode
	private class QNode<Item>
	{
		Item item;
		QNode<Item> next;
		public QNode(Item item)
		{
			this.item = item;
			next = null;
		}
	}
	
	
	// DequeIterator class
    private class RndQueueIterator<Item> implements Iterator<Item>
    {    	
    	QNode<Item> current;
    	
    	public RndQueueIterator(QNode<Item> head)
    	{
    		current = head;
    	}
    	
        public boolean hasNext() { return current != null; }
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next()
        {
            if (!hasNext())            
            	// No more elements to return
            	throw new java.util.NoSuchElementException();
            int r = StdRandom.uniform(N);
    		QNode<Item> node = current;
    		QNode<Item> prevNode;
    		for (int i = 0; i < r; i++)
    		{
    			prevNode = node;
    			node = node.next;    			
    		}
    		return (Item)node.item;
            
        }
    }
	
	// unit testing (optional)
	public static void main(String[] args)
	{
		RandomizedQueue rq = new RandomizedQueue();		
		
		rq.enqueue(34);
		System.out.println(rq.isEmpty());//     ==> false
		System.out.println(rq.size());//        ==> 1
        rq.enqueue(12);
        System.out.println(rq.dequeue());//     ==> 34
        rq.enqueue(14);
        rq.enqueue(18);
        System.out.println(rq.size());//        ==> 3
        System.out.println(rq.dequeue());//     ==> 34
		
        Iterator it = rq.iterator();
        System.out.println(it.next());
        
        
        
        
        /*
    	System.out.println("Size: " + rq.size()); // 6
    	System.out.println(rq.sample());
    	System.out.println("Size: " + rq.size()); // 6
    	System.out.println(rq.dequeue());
    	System.out.println(rq.iterator().next());
    	System.out.println("Size: " + rq.size()); // 4
    	*/
    	
	}
}
