import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner;
import java.io.*; 
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item>
{
	private QNode head;
	private RndQueueIterator iterator;
	private int N = 0;
	
	// construct an empty randomized queue
	public RandomizedQueue()
	{
		iterator = new RndQueueIterator();
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
		return this.iterator.next();
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
		return this.iterator;		
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
            if (size() == 0)            
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
    		prevNode.next = node.next;
    		N--;
    		return (Item)node.item;
        }
    }
	
	// unit testing (optional)
	public static void main(String[] args)
	{
		RandomizedQueue queue = new RandomizedQueue();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);
		queue.enqueue(6);
		
    	System.out.println("Size: " + queue.size()); // 6
    	System.out.println(queue.sample());
    	System.out.println("Size: " + queue.size()); // 6
    	System.out.println(queue.dequeue());
    	System.out.println(queue.iterator().next());
    	System.out.println("Size: " + queue.size()); // 4
    	
	}
}
