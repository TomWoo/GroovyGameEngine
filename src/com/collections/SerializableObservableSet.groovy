package com.collections
/**
 * Created by Tom on 7/12/2017.
 */
public class SerializableObservableSet<E> extends LinkedHashSet<E> implements SerializableObservableCollection {
    @Delegate
    private transient ObservableSet<E> delegate = new ObservableSet<>();

    public SerializableObservableSet() {
        super();
    }

    public SerializableObservableSet(Collection<? extends E> c) {
        super(c);
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