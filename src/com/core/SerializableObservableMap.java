package com.core;

import groovy.lang.Delegate;
import groovy.util.ObservableMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tom on 7/4/2017.
 */
public class SerializableObservableMap extends ObservableMap implements Serializable, IListenable {
    @Delegate
    private transient ObservableMap delegate = new ObservableMap();

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new LinkedHashMap(delegate)); // TODO: check
        oos.close();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        delegate = new ObservableMap((Map) ois.readObject());
        ois.close();
    }
}
