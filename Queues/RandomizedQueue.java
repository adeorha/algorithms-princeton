import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] s;
	private int n;
	public RandomizedQueue()                 // construct an empty randomized queue
	{
		n=0;
		s = (Item[]) new Object[2];
	}
	public boolean isEmpty()                 // is the queue empty?
	{
		return n==0;
	}
	public int size()                        // return the number of items on the queue
	{
		return n;
	}
	// resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = s[i];
        }
        s = temp;
    }
	public void enqueue(Item item)           // add the item
	{
		if (n == s.length) 
		{
			resize(2*s.length);    // double size of array if necessary
		}
		s[n++] = item;                            // add item
	}
	public Item dequeue()                    // remove and return a random item
	{
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
		int randIndex = StdRandom.uniform(n);
        Item item = s[randIndex];
        s[randIndex] = s[n-1];
        s[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == s.length/4) resize(s.length/2);
        return item;
	}
	public Item sample()                     // return (but do not remove) a random item
	{
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randIndex = StdRandom.uniform(n);
        Item item = s[randIndex];
        
        return item;
		
	}
	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item>
	{
		private int i=0;
		
		public boolean hasNext()
		{
			return i!=n;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
		public Item next()
		{
			if(!hasNext())
				throw new NoSuchElementException();
			return s[i++];
		}
	}
	
	public static void main(String[] args)   // unit testing (optional)
	{
		RandomizedQueue<Integer> randqueueObj = new RandomizedQueue<Integer>();
		randqueueObj.enqueue(3);
		randqueueObj.enqueue(5);
		randqueueObj.enqueue(15);
		randqueueObj.enqueue(23);
		randqueueObj.enqueue(54);
		randqueueObj.enqueue(59);
		randqueueObj.dequeue();
		System.out.println("Sample" + randqueueObj.sample());
		Iterator<Integer> it = randqueueObj.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
	}
}