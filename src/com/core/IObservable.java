package com.core;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.beans.PropertyChangeListener;
//import java.beans.VetoableChangeListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/18/2017.
 */
public interface IObservable { // Must override these methods!
    default PropertyChangeListener[] getPropertyChangeListeners() {
        throw new NotImplementedException(getClass().getName() + " does not implement getPropertyChangeListeners()");
    }

    default void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        throw new NotImplementedException(getClass().getName() + " does not implement addPropertyChangeListener()");
    }

    default void removePropertyChangeListener(PropertyChangeListener listener) {
        throw new NotImplementedException(getClass().getName() + " does not implement removePropertyChangeListener()");
    }

    default void firePropertyChange(String name, Object oldValue, Object newValue) {
        throw new NotImplementedException(getClass().getName() + " does not implement firePropertyChange()");
    }

    default void removeAllListeners() {
        for(PropertyChangeListener listener : getPropertyChangeListeners()) {
            removePropertyChangeListener(listener); // TODO: test
        }
    }
/*
    PropertyChangeListener[] getPropertyChangeListeners();
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    void firePropertyChange(String name, Object oldValue, Object newValue);
    void removeAllListeners();
*/
    /*
    default List<String> getObservablePropertyNames() {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(getClass(), ObservableProperty.class);
        return fields.stream().map(e -> e.getName()).collect(Collectors.toList());
    }

    default VetoableChangeListener[] getVetoableChangeListeners() {
        return new VetoableChangeListener[0];
    }
    default void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {}
    default void removeVetoableChangeListener(VetoableChangeListener listener) {}
    default void fireVetoableChange(String name, Object oldValue, Object newValue) {}
    */
}
