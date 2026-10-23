import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    BSTNode root;
    int size;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value);
            size += 1;
        } else {
            put(key, value, root);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        BSTNode target = searchNode(key, root);
        if (target == null) {
            return null;
        }
        return searchNode(key, root).value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return searchNode(key, root) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        addToKeySet(keySet, root);
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        V oldValue = get(key);
        if (oldValue == null) {
            return null;
        }
        if (root.key.equals(key)) {
            root = remove(key, root);
        } else {
            remove(key, root);
        }
        size -= 1;
        return oldValue;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        toList(root, keys);
        return keys.iterator();
    }

    private BSTNode searchNode(K key, BSTNode root) {
        if (root == null) {
            return null;
        } else if (key.compareTo(root.key) == 0) {
            return root;
        } else if (key.compareTo(root.key) < 0) {
            return searchNode(key, root.left);
        } else {
            return searchNode(key, root.right);
        }
    }

    private BSTNode put(K key, V value, BSTNode root) {
        if (root == null) {
            size += 1;
            return new BSTNode(key, value);
        }

        if (key.compareTo(root.key) == 0) {
            root.value = value;
        } else if (key.compareTo(root.key) < 0) {
            root.left = put(key, value, root.left);
        } else if (key.compareTo(root.key) > 0) {
            root.right = put(key, value, root.right);
        }
        return root;
    }

    private BSTNode remove(K key, BSTNode root) {
        if (key.compareTo(root.key) < 0) {
            root.left = remove(key, root.left);
            return root;
        } else if (key.compareTo(root.key) > 0) {
            root.right = remove(key, root.right);
            return root;
        } else {  // key.compareTo(root.key) == 0
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                BSTNode successor = findSuccessor(root);
                remove(successor.key, root);
                root.key = successor.key;
                root.value = successor.value;
                return root;
            }
        }
    }

    private BSTNode findSuccessor(BSTNode root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private void addToKeySet(Set<K> keySet, BSTNode root) {
        if (root == null) {
            return;
        }
        keySet.add(root.key);
        addToKeySet(keySet, root.left);
        addToKeySet(keySet, root.right);
    }

    private void toList(BSTNode root, List<K> keys) {
        if (root == null) {
            return;
        }
        toList(root.left, keys);
        keys.add(root.key);
        toList(root.right, keys);
    }
}
