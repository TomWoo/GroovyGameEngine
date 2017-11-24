package com.core;

import com.Utilities;
import com.collections.ObservableCollection;
import com.collections.SerializableObservableMap;
import com.collections.SerializableObservableSet;
import com.components.IComponent;
import com.sun.istack.internal.NotNull;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/8/2017.
 * Object composed of components.
 */
public class Entity implements IEntity {
    private final String uniqueID = Utilities.generateUID();
    private String name;
    @ObservableCollection
    private final SerializableObservableSet<String> groupIDs;
    @ObservableCollection
    private final SerializableObservableMap<Class, IComponent> componentsMap;

    public Entity(String name, String... groupIDs) {
        this(name, Arrays.asList(groupIDs));
    }

    public Entity(String name, List<String> groupIDs) {
        this.name = name;
        this.groupIDs = new SerializableObservableSet<>(groupIDs);
        this.componentsMap = new SerializableObservableMap<>();
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
        return new LinkedHashSet<>(groupIDs);
    }

    @Override
    public IReturnMessage addGroupIDs(String... groupIDs) {
        return addGroupIDs(new LinkedHashSet<>(Arrays.asList(groupIDs)));
    }

    @Override
    public IReturnMessage addGroupIDs(Set<String> groupIDs) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String groupID : groupIDs) {
            if (this.groupIDs.add(groupID)) {
                returnMessage.appendInfo(getName() + " is now part of " + groupID + ". ");
            } else {
                returnMessage.appendErrors(getName() + " is already part of " + groupID + ". ");
                returnMessage.setExitStatus(1);
            }
        }
        return returnMessage;
    }

    @Override
    public Set<IComponent> getComponents() {
        return new LinkedHashSet<>(componentsMap.values());
    }

    @Override @NotNull  // TODO: handle?
    public <T extends IComponent>T getComponent(Class<T> c) {
        return (T) componentsMap.get(c);
    }

    @Override
    public boolean hasComponents(Class... names) {
        return hasComponents(new LinkedHashSet<>(Arrays.asList(names)));
    }

    @Override
    public boolean hasComponents(Set<Class> classes) {
        Set<Class> relevantNames = classes.stream().filter({e -> componentsMap.containsKey(e)}).collect(Collectors.toSet());
        return relevantNames.equals(classes);
    }

    @Override
    public IReturnMessage addComponents(IComponent... components) {
        return addComponents(new LinkedHashSet<>(Arrays.asList(components)));
    }

    @Override
    public IReturnMessage addComponents(Set<IComponent> components) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IComponent component : components) {
            Class c = component.getClass();
            if(componentsMap.containsKey(c)) {
                returnMessage.appendErrors(getName() + " already has " + c + ". ");
                returnMessage.setExitStatus(2);
            } else {
                componentsMap.put(c, component);
                returnMessage.appendInfo(c.getName() + " has been added to " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeComponents(Class... classes) {
        return removeComponents(new LinkedHashSet<>(Arrays.asList(classes)));
    }

    @Override
    public IReturnMessage removeComponents(Set<Class> classes) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(Class c : classes) {
            if(!componentsMap.containsKey(c)) {
                returnMessage.appendErrors(getName() + " does not have " + c + ". ");
                returnMessage.setExitStatus(2);
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
}
