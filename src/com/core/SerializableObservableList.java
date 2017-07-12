package com.core;

import groovy.lang.Delegate;
import groovy.util.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 7/4/2017.
 */
public class SerializableObservableList extends ObservableList implements Serializable, IListenable {
    @Delegate
    private transient ObservableList delegate = new ObservableList();

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ArrayList(delegate)); // TODO: check
        oos.close();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        delegate = new ObservableList((List) ois.readObject());
        ois.close();
    }
}
