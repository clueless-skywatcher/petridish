package io.duskmare.petridish.datastructs;

public interface GenericTable<K, V> {
    public void set(K key, V value);
    public V get(K key);
    public void remove(K key);
    public boolean containsKey(K key);
}
