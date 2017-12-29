package com.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface ReadOnlyMap<K, V> {
    V get(K key);
    Set<K> keySet();
    Collection<V> values();
    Set<Map.Entry<K,V>>	entrySet();
    boolean containsKey(K key);
    boolean containsValue(V value);
    int size();
}
