package com.collections;

import com.core.IObservable;
import groovy.lang.Delegate;
import groovy.util.ObservableMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tom on 7/4/2017.
 */
public class SerializableObservableMap<K, V> extends LinkedHashMap<K, V> implements IObservable {
    @Delegate
    private transient ObservableMap delegate = new ObservableMap();

    @Override
    public V put(K key, V value) {
        delegate.put(key, value);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        delegate.remove(key);
        return super.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
        super.putAll(m);
    }

    @Override
    public void clear() {
        delegate.clear();
        super.clear();
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
