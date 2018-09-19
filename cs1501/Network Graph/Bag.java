import java.util.*;
/*  This is a revised version of Bag class to fit operations 
 *  in NetworkAnalysis.java 
 *  The original authors are Robert Sedgewick and Kevin Wayne,
 *  the textbook authors
 */
public class Bag<Item> implements Iterable<Item>{
    private Node<Item> first;
    private int n;
    
    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    public Bag(){
        first = null;
        n = 0;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return n;
    }

    public void add(Item item){
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Iterator<Item> iterator(){
        return new ListIterator<Item>(first);  
    }

    private class ListIterator<Item> implements Iterator<Item>{
        private Node<Item> current;

        public ListIterator(Node<Item> first){
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}