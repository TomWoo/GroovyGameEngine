package com.collections;

import java.util.stream.Stream;

@Deprecated
public interface ReadOnlySet<E> {
    boolean contains(E element);
    int size();
    Stream<E> stream();
}
