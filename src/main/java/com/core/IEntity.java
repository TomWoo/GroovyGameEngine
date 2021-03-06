package com.core;

import com.components.IComponent;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IEntity extends Serializable {
    String getUID();
    String getName();
    void setName(String name);

    Set<String> getGroupIDs();
    IReturnMessage addGroupIDs(String... groupIDs);
    IReturnMessage addGroupIDs(Set<String> groupIDs);

    Set<IComponent> getComponents();
    <T extends IComponent>T getComponent(Class<T> c);
    boolean hasComponents(Class... classes);
    boolean hasComponents(Set<Class> classes);
    IReturnMessage addComponents(IComponent... components);
    IReturnMessage addComponents(Set<IComponent> components);
    IReturnMessage removeComponents(Class... classes);
    IReturnMessage removeComponents(Set<Class> classes);
    boolean setComponent(IComponent component); // overrides any existing component

    void addComponentsChangeListener(PropertyChangeListener listener);
    void addGroupsChangeListener(PropertyChangeListener listener);
    void removeAllListeners();
}
