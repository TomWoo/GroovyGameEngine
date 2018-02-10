package com.core;

import com.Utilities;
import com.collections.SerializableObservableMap;
import com.collections.SerializableObservableSet;
import com.components.IComponent;
//import com.sun.istack.internal.NotNull
import groovy.transform.TypeChecked;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/8/2017.
 * Node composed of components.
 */
@TypeChecked
public class Entity implements IEntity { // TODO: add Node-based operations
    private transient String uniqueID = Utilities.generateUID();
    private String name;
    //@ObservableCollection
    private SerializableObservableSet<String> groupIDs = new SerializableObservableSet<>();
    //@ObservableCollection
    private SerializableObservableMap<Class, IComponent> componentsMap = new SerializableObservableMap<>();

    public Entity(String name) {
        this.name = name;
    }

    public Entity(String name, List<String> groupIDs) {
        this(name);
        this.groupIDs = new SerializableObservableSet<>(groupIDs);
    }

    public Entity(String name, String... groupIDs) {
        this(name, Arrays.asList(groupIDs));
    }

    @Override
    public String getUID() {
        return uniqueID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getGroupIDs() {
        return new LinkedHashSet<String>(groupIDs);
    }

    @Override
    public IReturnMessage addGroupIDs(String... groupIDs) {
        return addGroupIDs(new LinkedHashSet<>(groupIDs==null? [] : Arrays.asList(groupIDs)));
    }

    @Override
    public IReturnMessage addGroupIDs(Set<String> groupIDs) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String groupID : groupIDs) {
            if (this.groupIDs.add(groupID)) {
                returnMessage.appendInfo(getName() + " is now part of " + groupID + ". ");
            } else {
                returnMessage.appendError(getName() + " is already part of " + groupID + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public Set<IComponent> getComponents() {
        return new LinkedHashSet<>(componentsMap.values());
    }

    @Override
    public <T extends IComponent>T getComponent(Class<T> c) {
        return (T) componentsMap.get(c);
    }

    @Override
    public boolean hasComponents(Class... names) {
        return hasComponents(new LinkedHashSet<>(names==null? [] : Arrays.asList(names)));
    }

    @Override
    public boolean hasComponents(Set<Class> classes) { // TODO: use keySet().contains()
        Set<Class> componentClasses = classes.stream().filter({e -> componentsMap.containsKey(e)}).collect(Collectors.toSet());
        return componentClasses.equals(classes);
    }

    @Override
    public IReturnMessage addComponents(IComponent... components) {
        return addComponents(new LinkedHashSet<>(components==null? [] : Arrays.asList(components)));
    }

    @Override
    public IReturnMessage addComponents(Set<IComponent> components) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IComponent component : components) {
            Class c = component.getClass();
            if(componentsMap.containsKey(c)) {
                returnMessage.appendError(getName() + " already has " + c.getName() + ". ");
            } else {
                componentsMap.put(c, component);
                returnMessage.appendInfo(c.getName() + " has been added to " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeComponents(Class... classes) {
        return removeComponents(new LinkedHashSet<>(classes==null? [] : Arrays.asList(classes)));
    }

    @Override
    public IReturnMessage removeComponents(Set<Class> classes) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(Class c : classes) {
            if(!componentsMap.containsKey(c)) {
                returnMessage.appendError(getName() + " does not have " + c + ". ");
            } else {
                componentsMap.remove(c);
                returnMessage.appendInfo(c.getName() + " has been removed from " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public void addComponentsChangeListener(PropertyChangeListener listener) {
        componentsMap.addPropertyChangeListener(listener);
    }

    @Override
    public void addGroupsChangeListener(PropertyChangeListener listener) {
        groupIDs.addPropertyChangeListener(listener);
    }

    @Override
    public void removeAllListeners() {
        componentsMap.removeAllListeners();
        groupIDs.removeAllListeners();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.uniqueID = Utilities.generateUID();
    }
}
