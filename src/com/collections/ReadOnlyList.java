package com.collections;

import java.util.stream.Stream;

public interface ReadOnlyList<T> {
    T get(int i);
    boolean contains(T element);
    int size();
    Stream<T> stream();
}
