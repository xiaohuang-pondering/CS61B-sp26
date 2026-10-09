import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int lastPos;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        lastPos = items.length - 1;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp();
        }

        items[nextFirst] = x;
        size += 1;

        if (nextFirst == 0) {
            nextFirst = lastPos;
        } else {
            nextFirst -= 1;
        }
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp();
        }

        items[nextLast] = x;
        size += 1;

        if (nextLast == lastPos) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        return items[calcRealIndex(0)];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        return items[calcRealIndex(size - 1)];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }

        int firstIndex = calcRealIndex(0);
        T firstItem = items[firstIndex];
        items[firstIndex] = null;

        nextFirst = firstIndex;
        size -= 1;

        if (items.length > 15 && (double) size / items.length <= 0.25) {
            resizeDown();
        }

        return firstItem;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int lastIndex = calcRealIndex(size - 1);
        T lastItem = items[lastIndex];
        items[lastIndex] = null;

        nextLast = lastIndex;
        size -= 1;

        if (items.length > 15 && (double) size / items.length <= 0.25) {
            resizeDown();
        }

        return lastItem;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[calcRealIndex(index)];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * Returns index of the {@code i}th item. Assumes {@code i} is smaller than {@code size}.
     */
    private int calcRealIndex(int i) {
        int uncheckedRes = nextFirst + 1 + i;
        if (uncheckedRes > lastPos) {
            return uncheckedRes - items.length;
        } else {
            return uncheckedRes;
        }
    }

    private void resizeUp() {
        T[] newArray = (T[]) new Object[2 * items.length];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }

        items = newArray;
        lastPos = newArray.length - 1;
        nextFirst = lastPos;
        nextLast = size;
    }

    private void resizeDown() {
        T[] newArray = (T[]) new Object[items.length / 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }

        items = newArray;
        lastPos = newArray.length - 1;
        nextFirst = lastPos;
        nextLast = size;
    }
}
