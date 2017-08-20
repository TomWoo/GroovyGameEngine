package com.collections
/**
 * Created by Tom on 7/4/2017.
 */
public class SerializableObservableMap<K, V> extends LinkedHashMap<K, V> implements SerializableObservableCollection {
    @Delegate
    private transient ObservableMap delegate = new ObservableMap();

    @Override
    public V put(K key, V value) {
        delegate.put(key, value);
        return (V) super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        assert key instanceof K // TODO: return null?
        delegate.remove(key);
        return (V) super.remove(key);
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
