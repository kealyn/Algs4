import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Scanner;
import java.io.*; 
import java.util.Iterator;

public class Permutation 
{
 
 
 
 
 /*
 public static void main(String[] args) throws FileNotFoundException, IOException
 {
  try (BufferedReader br = new BufferedReader(new FileReader("2-Deque/distinct.txt"))) {
   // Read a integer k first
   int k = StdIn.readInt();
      String line = br.readLine();
      String[] segments = line.split("\\s");
      int n = segments.length;
      
      Deque<String> deque = new Deque<String>();      
      Deque<String> deque = new Deque<String>();      
      PopulateDeque(deque, segments, k, n);
      PrintDeque(deque);
  }
 }
 */
 
 public static void main(String[] args)
 {
  
  
   // Read a integer k first
      int k = Integer.parseInt(args[0]);
       //System.out.println(k);
       RandomizedQueue<String> rq = new RandomizedQueue<String>(); 
       String[] words = StdIn.readAllStrings();
       for (int i = 0; i < words.length; i++)
       {
           rq.enqueue(words[i]);
       }
       
       
       Iterator<String> it = rq.iterator(); 
       int counter = 0;
       while (counter < k && it.hasNext())
       {
        System.out.println(it.next());
        counter++;
       }
 }
 
 private static void PopulateDeque(Deque deque, String[] segments, int k, int n)
 {
  for (int i = 0; i < k; i++)
     {
      int idx = StdRandom.uniform(n);
      while (segments[idx] == null)
       idx = StdRandom.uniform(n);
      
      int r = StdRandom.uniform(2);
      if (r == 0)
      {
       // From front
       deque.addFirst(segments[idx]);
      }
      else
      {
       // From back
       deque.addLast(segments[idx]);
      }
      
      segments[idx] = null;
     }
 }
 
 private static void PrintDeque(Deque deque)
 {
  while (deque.iterator().hasNext())
     {
      String item = (String) deque.iterator().next();
      StdOut.println(item);
     }
 }
}
