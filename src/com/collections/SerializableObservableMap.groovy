package com.collections

import groovy.transform.TypeChecked

/**
 * Created by Tom on 7/4/2017.
 */
@TypeChecked
public class SerializableObservableMap<K, V> extends LinkedHashMap<K, V> implements SerializableObservableCollection, ReadOnlyMap<K, V> {
    @Delegate
    private transient ObservableMap delegate = new ObservableMap();

    public SerializableObservableMap() {
        super();
    }

    public SerializableObservableMap(Map<? extends K, ? extends V> map) {
        super(map);
        delegate = new ObservableMap(map);
    }

    @Override
    public V put(K key, V value) {
        delegate.put(key, value);
        return (V) super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        delegate.remove(key);
        return (V) super.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
        super.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    public void clear() {
        delegate.clear();
        super.clear();
    }

    boolean toBack(K key) {
        if(super.containsKey(key)) {
            super.put(key, super.remove(key))
            return true
        }
        return false
    }

    boolean toFront(K key) {
        if(super.containsKey(key)) {
            Collection<K> otherkeys = keySet().stream().findAll { e -> !e.equals(key) }
            for (K otherKey : otherkeys) {
                super.put(otherKey, super.remove(otherKey))
            }
            return true
        }
        return false
    }
/*
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new LinkedHashMap<K, V>(delegate));
        oos.close();
    }
*/
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        ois.close();
        delegate = new ObservableMap(this); // TODO: check
    }
}
