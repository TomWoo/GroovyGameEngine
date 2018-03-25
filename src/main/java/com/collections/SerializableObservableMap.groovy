package com.collections

import com.core.IObservable
import groovy.transform.TypeChecked

/**
 * Created by Tom on 7/4/2017.
 */
@TypeChecked
public class SerializableObservableMap<K, V> extends LinkedHashMap<K, V> implements Serializable, IObservable {
    @Delegate
    private transient ObservableMap observableDelegate = new ObservableMap();

    public SerializableObservableMap() {
        super();
    }

    public SerializableObservableMap(Map<? extends K, ? extends V> map) {
        super(map);
        observableDelegate = new ObservableMap(map);
    }

    @Override
    final V put(K key, V value) {
        observableDelegate.put(key, value)
        return (V) super.put(key, value)
    }

    @Override
    public V remove(Object key) {
        observableDelegate.remove(key);
        return (V) super.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        observableDelegate.putAll(m);
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
        observableDelegate.clear();
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

    // Reference: http://mrhaki.blogspot.com/2016/06/groovy-goodness-turn-map-or-list-as.html
    static SerializableObservableMap construct(String s) { // TODO: parse JSON?
        def map = s.substring(1,s.length()-1).split(', ').collectEntries {
            def pair = it.split('=')
            [(pair.first()) : pair.last()]
        }
        return new SerializableObservableMap(map)
    }

/*
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new LinkedHashMap<K, V>(observableDelegate));
        oos.close();
    }
*/
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        //observableDelegate = new ObservableMap() // TODO: fix, re-populate delegate map w/o triggering recursive calls to put()
        ois.defaultReadObject()
        ois.close()
        observableDelegate = new ObservableMap()
    }
}
