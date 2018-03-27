package com.collections

import com.core.IObservable
import groovy.transform.TypeChecked

/**
 * Created by Tom on 7/12/2017.
 */
@TypeChecked
public class SerializableObservableSet<E> extends LinkedHashSet<E> implements Serializable, IObservable {
    @Delegate
    private transient ObservableSet<E> observableDelegate = new ObservableSet<>();

    public SerializableObservableSet() {
        super();
    }

    public SerializableObservableSet(Collection<? extends E> c) {
        super(c);
        observableDelegate = new ObservableSet<E>(c.toSet());
    }

    @Override
    public boolean add(E e) {
        observableDelegate.add(e);
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        observableDelegate.remove(o);
        return super.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(observableDelegate==null) { // TODO: avoid workaround
            observableDelegate = new ObservableSet<>();
        }
        observableDelegate.addAll(c);
        return super.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        observableDelegate.retainAll(c);
        return super.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        observableDelegate.removeAll(c);
        return super.removeAll(c);
    }

    @Override
    public void clear() {
        observableDelegate.clear();
        super.clear();
    }

    static SerializableObservableSet construct(String s) {
        //return new SerializableObservableSet(s.toSet())
        def set = s.substring(1,s.length()-1).split(', ').collect()
        return new SerializableObservableSet(set)
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject()
        ois.close()
        observableDelegate = new ObservableSet<E>()
        observableDelegate.addAll(this)
    }
}
