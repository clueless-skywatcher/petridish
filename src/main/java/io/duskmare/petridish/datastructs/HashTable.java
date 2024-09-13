package io.duskmare.petridish.datastructs;

import java.util.List;

import io.duskmare.petridish.PetriDishServer;
import lombok.Getter;

import java.util.ArrayList;

public class HashTable<K, V> implements GenericTable<K, V> {

    private static final float DEFAULT_LOAD_FACTOR = 0.70f;
    private static final float CAPACITY_SCALE_MULTIPLIER = 2;

    private int capacity;
    private @Getter int elementCount = 0;
    private @Getter float loadFactor = DEFAULT_LOAD_FACTOR;

    private List<HashTableNode<K, V>> nodes;

    public HashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        nodes = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            nodes.add(null);
        }
    }

    public HashTable() {
        this(10, DEFAULT_LOAD_FACTOR);
    }
    
    @SuppressWarnings("hiding")
    private class HashTableNode<K, V> {
        private K key;
        private V value;
        private HashTableNode<K, V> next;

        public HashTableNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public void add(K key, V value) {
            HashTableNode<K, V> head = this;
            while (head != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            head = new HashTableNode<>(key, value);
            elementCount++;
        }

        public String toString() {
            return String.format("(%s, %s) -> %s", key, value, next.toString());
        }
    }

    @Override
    public void set(K key, V value) {
        int position = getPosition(key);
        if (nodes.get(position) == null) {
            nodes.set(position, new HashTableNode<>(key, value));
            elementCount++;
        }
        else {
            // Hash collision. Resolve by separate chain
            nodes.get(position).add(key, value);
        }
        if ((float) elementCount / capacity > loadFactor) {
            rehash();
        }
    }

    public int getPosition(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    @Override
    public V get(K key) {
        int position = getPosition(key);
        HashTableNode<K, V> head = nodes.get(position);
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        int position = getPosition(key);
        HashTableNode<K, V> head = nodes.get(position);
        HashTableNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    nodes.set(position, head.next);
                }
                else {
                    prev.next = head.next;
                }
                head.next = null;
                elementCount--;
                break;
            }
            prev = head;
            head = head.next;
        }
    }

    public int getCapacity() {
        return this.nodes.size();
    }

    private void rehash() {
        List<HashTableNode<K, V>> oldNodes = this.nodes;
        List<HashTableNode<K, V>> newNodes = new ArrayList<>();

        int oldCapacity = this.capacity;
        int newCapacity = (int) (this.capacity * CAPACITY_SCALE_MULTIPLIER);

        PetriDishServer.LOGGER.info(String.format("Rehashing global hash table: %d => %d", oldCapacity, newCapacity));

        for (int i = 0; i < newCapacity; i++) {
            newNodes.add(null);
        }

        this.capacity = newCapacity;

        for (int i = 0; i < oldCapacity; i++) {
            HashTableNode<K, V> node = oldNodes.get(i);
            if (node != null) {
                int position = getPosition(node.key);
                newNodes.set(position, node);
            }
        }

        this.nodes = newNodes;
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < this.capacity; i++) {
            HashTableNode<K, V> head = this.nodes.get(i);
            while (head != null) {
                if (head.key.equals(key)) {
                    return true;
                }
                head = head.next;
            }
        }

        return false;
    }
    
}
