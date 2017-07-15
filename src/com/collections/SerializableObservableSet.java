package com.collections;

import com.core.IObservable;
import groovy.lang.Delegate;
import groovy.util.ObservableSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Tom on 7/12/2017.
 */
public class SerializableObservableSet<E> extends LinkedHashSet<E> implements IObservable {
    @Delegate
    private transient ObservableSet<E> delegate = new ObservableSet<>();

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

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        ois.close();

        delegate = new ObservableSet<>(this); // TODO: check
    }
}
