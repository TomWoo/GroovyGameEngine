package com.collections

import com.core.IObservable
import groovy.transform.TypeChecked

import java.util.*;

/**
 * Created by Tom on 7/4/2017.
 */
@TypeChecked
public class SerializableObservableList<T> extends ArrayList<T> implements Serializable, IObservable {
    @Delegate
    private transient ObservableList delegate = new ObservableList();

    public SerializableObservableList() {
        super();
    }

    public SerializableObservableList(Collection<? extends T> c) {
        super(c);
        delegate = new ObservableList(c.toList());
    }

    @Override
    public boolean add(T e) {
        delegate.add(e);
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        delegate.remove(o);
        return super.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        delegate.addAll(c);
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        delegate.addAll(index, c);
        return super.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        delegate.removeAll(c);
        return super.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        delegate.retainAll(c);
        return super.retainAll(c);
    }

    @Override
    public void clear() {
        delegate.clear();
        super.clear();
    }

    @Override
    public T set(int index, T element) {
        delegate.set(index, element);
        return (T) super.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        delegate.add(index, element);
        super.add(element);
    }

    @Override
    public T remove(int index) {
        delegate.removeAt(index); // TODO: verify
        return (T) super.removeAt(index);
    }

    public List<T> toList() {
        return this;
    }

    static SerializableObservableList construct(String s) {
        //return new SerializableObservableList(s.toList())
        def set = s.substring(1,s.length()-1).split(', ').collect()
        return new SerializableObservableList(set)
    }
/*
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ArrayList<T>(observableDelegate));
        oos.close();
    }
*/
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        ois.close();

        delegate = new ObservableList(this); // TODO: check
    }
}
