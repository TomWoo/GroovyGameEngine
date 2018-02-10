package com.collections

import com.core.IObservable
import groovy.transform.TypeChecked

/**
 * Created by Tom on 7/12/2017.
 */
@TypeChecked
public class SerializableObservableSet<E> extends LinkedHashSet<E> implements Serializable, IObservable {
    @Delegate
    private transient ObservableSet<E> delegate = new ObservableSet<>();

    public SerializableObservableSet() {
        super();
    }

    public SerializableObservableSet(Collection<? extends E> c) {
        super(c);
        delegate = new ObservableSet<E>(c.toSet());
    }

    @Override
    public boolean add(E e) {
        delegate.add(e);
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        delegate.remove(o);
        return super.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(delegate==null) { // TODO: avoid workaround
            delegate = new ObservableSet<>();
        }
        delegate.addAll(c);
        return super.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        delegate.retainAll(c);
        return super.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        delegate.removeAll(c);
        return super.removeAll(c);
    }

    @Override
    public void clear() {
        delegate.clear();
        super.clear();
    }

    static SerializableObservableSet construct(String s) {
        //return new SerializableObservableSet(s.toSet())
        def set = s.substring(1,s.length()-1).split(', ').collect()
        return new SerializableObservableSet(set)
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        ois.close();

        delegate = new ObservableSet<>(this);
    }
}
