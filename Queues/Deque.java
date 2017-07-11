import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;	
	private int size;

	private class Node{
		Item item;
		Node next;
		Node prev;
	}

	public Deque()                           // construct an empty deque
	{
		size=0;
		first = new Node();
		last = new Node();
		
		first.item=null;
		first.next=last;
		first.prev=null;
		
		last.item=null;
		last.next=null;
		last.prev=first;
		
		first=last;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return (first.item==null);
	}
	public int size()                        // return the number of items on the deque
	{
		return size;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if(item == null){
			throw new java.lang.NullPointerException();
		}
		else{
			Node newNode = new Node();
			newNode.next = first;
			newNode.prev = null;
			newNode.item = item;
			first.prev = newNode;
			first = newNode;
			if(size==0)
				last = newNode;
			size++;
		}
	}
	public void addLast(Item item)           // add the item to the end
	{
		if(item == null){
			throw new java.lang.NullPointerException();
		}
		else{
			Node newNode = new Node();
			newNode.next = null;
			newNode.prev = last;
			newNode.item = item;
			last.next = newNode;
			last = newNode;
			if(size==0)
				first=newNode;
			size++;
		}
	}
	public Item removeFirst()                // remove and return the item from the front
	{
		if(size==0)
		{
			throw new java.util.NoSuchElementException();
		}
		else{
			Item temp_item;
			temp_item = first.item;
			first = first.next;
			first.prev = null;
			size--;
			return temp_item;
		}
	}
	public Item removeLast()                 // remove and return the item from the end
	{
		if(size==0)
		{
			throw new java.util.NoSuchElementException();
		}
		else{
			Item temp_item;
			temp_item = last.item;
			last = last.prev;
			last.next = null;
			size--;
			return temp_item;
		}
	}
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new ItemIterator();
	}

	private class ItemIterator implements Iterator<Item>
	{
		private Node current = first;
		public boolean hasNext(){	
			return current!=null;
		}
		public Item next()
		{
			if(current == null)
			{
				throw new java.util.NoSuchElementException();
			}
			else
			{
				Item item = current.item;
				current = current.next;
				return item;
			}
		}
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		}

	}

	public static void main(String[] args)   // unit testing (optional
	{
		Deque<Integer> dequeObj = new Deque<Integer>();
		dequeObj.addLast(52);
		dequeObj.addFirst(2);
		dequeObj.addFirst(3);
		dequeObj.removeFirst();
		dequeObj.removeFirst();
		dequeObj.addFirst(4);
		dequeObj.removeLast();

		dequeObj.addLast(52);
		dequeObj.addFirst(5);
		dequeObj.addLast(51);
		dequeObj.addLast(52);
		dequeObj.addLast(55);
		dequeObj.removeFirst();
		dequeObj.removeLast();
		dequeObj.addLast(60);
		 	
		Iterator<Integer> it = dequeObj.iterator();
		while(it.hasNext()){
			System.out.println(it.next()+"Size="+dequeObj.size());
		}
	}
}


