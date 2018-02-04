package com.core;

import com.Utilities;
import com.collections.ObservableCollection
import com.collections.SerializableObservableList
import com.collections.SerializableObservableMap
import com.components.IComponent
import groovy.transform.TypeChecked
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/8/2017.
 * System containing entities, which contain components.
 */
@TypeChecked
public class EntitySystem implements IEntitySystem {
    private String name = "universe" //Utilities.generateUID();
    @ObservableCollection
    private final SerializableObservableMap<String, IEntity> entitiesMap = new SerializableObservableMap<>();

    public EntitySystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<IEntity> getEntities() {
        return new LinkedHashSet<>(entitiesMap.values());
    }

    @Override
    final SerializableObservableList<IEntity> getEntitiesAsList() {
        return new SerializableObservableList<IEntity>(entitiesMap.values());
    }

    @Override
    public Set<IEntity> getEntities(@NotNull String... uniqueIDs) {
        return getEntities(new LinkedHashSet<>(Arrays.asList(uniqueIDs)));
    }

    @Override
    public Set<IEntity> getEntities(Set<String> uniqueIDs) {
        return new LinkedHashSet<IEntity>(uniqueIDs.stream().map({e -> entitiesMap.get(e)}).collect(Collectors.toList()));
    }

    @Override
    public Set<IEntity> getEntitiesByName(String name) {
        return new LinkedHashSet<IEntity>(getEntities().stream()
                .filter({IEntity e -> e.getName().equals(name)})
                .collect(Collectors.toList()));
    }

    @Override @SafeVarargs
    final Set<IEntity> getEntitiesWithComponents(Class<? extends IComponent>... classes) {
        return getEntitiesWithComponents(new LinkedHashSet<>(classes==null? [] : Arrays.asList(classes)));
    }

    @Override
    public Set<IEntity> getEntitiesWithComponents(Set<Class<? extends IComponent>> classes) {
        return new LinkedHashSet<IEntity>(getEntities().stream()
                .filter({IEntity e -> e!=null && e.hasComponents(classes)})
                .collect(Collectors.toList()));
    }

    @Override
    public boolean containsEntity(String uniqueID) {
        return entitiesMap.containsKey(uniqueID);
    }

    @Override
    public IReturnMessage addEntities(IEntity... entities) {
        return addEntities(new LinkedHashSet<>(entities==null? [] : Arrays.asList(entities)));
    }

    @Override
    public IReturnMessage addEntities(Set<IEntity> entities) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IEntity entity : entities) {
            if (entitiesMap.containsKey(entity.getUID())) {
                returnMessage.appendError(entity.getName() + " is already inside " + getName() + ". ");
            } else {
                entitiesMap.put(entity.getUID(), entity);
                returnMessage.appendInfo(entity.getName() + " is now inside " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeEntities(String... uniqueIDs) {
        return removeEntities(new LinkedHashSet<>(uniqueIDs==null? [] : Arrays.asList(uniqueIDs)));
    }

    @Override
    public IReturnMessage removeEntities(Set<String> uniqueIDs) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String uniqueID : uniqueIDs) {
            if (!entitiesMap.containsKey(uniqueID)) {
                returnMessage.appendError(uniqueID + " is not inside " + getName() + ". ");
            } else {
                entitiesMap.remove(uniqueID);
                returnMessage.appendInfo(uniqueID + " is no longer inside " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public void clear() {
        entitiesMap.clear();
    }

    @Override
    public void sendEntityToTop(String uniqueID) {
        entitiesMap.toBack(uniqueID);
    }

    @Override
    public void sendEntityToBottom(String uniqueID) {
        entitiesMap.toFront(uniqueID);
    }

    @Override
    public void addChangeListener(PropertyChangeListener listener) {
        entitiesMap.addPropertyChangeListener(listener);
    }

    @Override
    public void removeAllListeners() {
        entitiesMap.removeAllListeners();
    }
}
