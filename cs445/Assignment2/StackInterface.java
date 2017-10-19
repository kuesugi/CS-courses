package cs445.a2;

import java.util.EmptyStackException;

/**
 * An interface for the ADT stack.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison
 * @version 4.5
 */
public interface StackInterface<E> {
    /** Adds a new entry to the top of this stack.
     *  @param newEntry  An object to be added to the stack */
    public void push(E newEntry);

    /** Removes and returns this stack's top entry.
     *  @return  The object at the top of the stack.
     *  @throws EmptyStackException  if the stack is empty. */
    public E pop() throws EmptyStackException;

    /** Retrieves this stack's top entry.
     *  @return  The object at the top of the stack.
     *  @throws EmptyStackException  if the stack is empty. */
    public E peek() throws EmptyStackException;

    /** Detects whether this stack is empty.
     *  @return  True if the stack is empty. */
    public boolean isEmpty();

    /** Removes all entries from this stack. */
    public void clear();
}

