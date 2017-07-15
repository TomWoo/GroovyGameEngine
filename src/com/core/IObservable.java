package com.core;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;

/**
 * Created by Tom on 6/18/2017.
 */
public interface IObservable { // Must override these methods!
    default PropertyChangeListener[] getPropertyChangeListeners() {
        return new PropertyChangeListener[0];
    }
    default void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {}
    default void removePropertyChangeListener(PropertyChangeListener listener) {}
    default void firePropertyChange(String name, Object oldValue, Object newValue) {}
    default void removeAllListeners() {
        for(PropertyChangeListener listener : getPropertyChangeListeners()) {
            removePropertyChangeListener(listener); // TODO: test
        }
    }

    /*
    default VetoableChangeListener[] getVetoableChangeListeners() {
        return new VetoableChangeListener[0];
    }
    default void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {}
    default void removeVetoableChangeListener(VetoableChangeListener listener) {}
    default void fireVetoableChange(String name, Object oldValue, Object newValue) {}
    */
}
