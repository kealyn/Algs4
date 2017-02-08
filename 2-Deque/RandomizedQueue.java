import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner;
import java.io.*; 
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item>
{
	private QNode head;
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
		QNode newNode = new QNode(item);
		if (N == 0)
		{
			// First node
			head = newNode;
			N++;
		}
		else
		{
			QNode it = head;
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
			QNode node = head;
			QNode prevNode = head;
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
		QNode node = head;
		for (int i = 0; i < r; i++)
		{
			node = node.next;
		}
		return (Item)node.item;
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator()
	{
		return new RndQueueIterator();	
	}
	
	// Inner class for QNode
	private class QNode<Item>
	{
		Item item;
		QNode next;
		public QNode(Item item)
		{
			this.item = item;
			next = null;
		}
	}
	
	
	// DequeIterator class
    private class RndQueueIterator implements Iterator<Item>
    {    	
        public boolean hasNext() { return size() > 0; }
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
    		QNode node = head;
    		QNode prevNode = head;
    		for (int i = 0; i < r; i++)
    		{
    			node = node.next;
    			prevNode = node;
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
