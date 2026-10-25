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

    private BSTNode root;
    private int size;

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
        root = put(key, value, root);
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
        return target.value;
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
        toSet(keySet, root);
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
        BSTNode oldNode = searchNode(key, root);
        if (oldNode == null) {
            return null;
        }
        V oldValue = oldNode.value;
        root = remove(key, root);
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

    private BSTNode searchNode(K key, BSTNode node) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return searchNode(key, node.left);
        } else {
            return searchNode(key, node.right);
        }
    }

    private BSTNode put(K key, V value, BSTNode node) {
        if (node == null) {
            size += 1;
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(key, value, node.left);
        } else {
            node.right = put(key, value, node.right);
        }
        return node;
    }

    private BSTNode remove (K key, BSTNode node) {
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                BSTNode successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                remove(successor.key, node);
                node.key = successor.key;
                node.value = successor.value;
                return node;
            }
        } else if (cmp < 0) {
            node.left = remove(key, node.left);
        } else {
            node.right = remove(key, node.right);
        }
        return node;
    }

    private void toSet(Set<K> keySet, BSTNode node) {
        if (node == null) {
            return;
        }
        keySet.add(node.key);
        toSet(keySet, node.left);
        toSet(keySet, node.right);
    }

    private void toList(BSTNode node, List<K> keys) {
        if (node == null) {
            return;
        }
        toList(node.left, keys);
        keys.add(node.key);
        toList(node.right, keys);
    }
}
