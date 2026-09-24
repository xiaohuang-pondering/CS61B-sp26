public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L != null) {
            L.first += x;
            incrRecursiveDestructive(L.rest, x);
        }
        return L;
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    /**
     * Returns the sum of all elements in the IntList.
     */
    public int sum() {
        if (rest == null) {
            return first;
        }
        return first + rest.sum();
    }

    /**
     * Destructively adds x to the end of the list.
     */
    public void addLast(int x) {
        IntList p = this;
        while (p.rest != null) {
            p = p.rest;
        }
        p.rest = new IntList(x, null);
    }

    /**
     * Destructively adds x to the front of this IntList.
     * This is a bit tricky to implement. The standard way to do this would be
     * to return a new IntList, but for practice, this implementation should
     * be destructive.
     */
    public void addFirst(int x) {
        rest = new IntList(first, rest);
        first = x;
    }
    // A not so satisfactory implementation:
    // IntList p = this;
    // int pre = x;
    // int cur = first;
    //     while (p.rest != null) {
    //     p.first = pre;
    //     p = p.rest;
    //     pre = cur;
    //     cur = p.first;
    // }
    // p.first = pre;
    // p.rest = new IntList(cur, null);
}
