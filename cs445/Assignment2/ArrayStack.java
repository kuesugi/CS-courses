package cs445.a2;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * A class of stacks whose entries are stored in an array.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison
 * @version 4.5
 */
public final class ArrayStack<E> implements StackInterface<E> {
    // Array of stack entries
    private E[] stack;
    // Index of top entry
    private int topIndex;
    private static final int DEFAULT_CAPACITY = 5;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(int initialCapacity) {
        // The cast is safe because of type erasure
        @SuppressWarnings("unchecked")
        E[] tempStack = (E[])new Object[initialCapacity];
        stack = tempStack;
        topIndex = -1;
    }

    public void push(E newEntry) {
        ensureCapacity();
        stack[++topIndex] = newEntry;
    }

    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[topIndex];
        }
    }

    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            E top = stack[topIndex];
            stack[topIndex--] = null;
            return top;
        }
    }

    public boolean isEmpty() {
        return topIndex < 0;
    }

    public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }

    // Doubles the size of the array stack if it is full
    private void ensureCapacity() {
        if (topIndex == stack.length - 1) { // If array is full, double its size
            stack = Arrays.copyOf(stack, 2 * stack.length);
        }
    }
}

