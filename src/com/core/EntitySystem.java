package com.core;

import com.UtilityFunctions;
import com.collections.SerializableObservableMap;
import groovy.lang.Delegate;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/8/2017.
 * System containing entities, which contain components.
 */
public class EntitySystem implements IEntitySystem { // TODO: override IListener default methods
    private final String uniqueID = UtilityFunctions.generateUID();
    @Delegate(includes = {"addPropertyChangeListener"}) @ObservableProperty
    private final SerializableObservableMap<String, IEntity> entitiesMap = new SerializableObservableMap<>();

    public String getUID() {
        return uniqueID;
    }

    @Override
    public Set<IEntity> getEntities() {
        return new LinkedHashSet<>(entitiesMap.values());
    }

    @Override
    public Set<IEntity> getEntities(String... uniqueIDs) {
        return getEntities(new LinkedHashSet<>(Arrays.asList(uniqueIDs)));
    }

    @Override
    public Set<IEntity> getEntities(Set<String> uniqueIDs) {
        Set<IEntity> entities = new LinkedHashSet<>();
        for(String uniqueID : uniqueIDs) { // TODO: stream
            entities.add(entitiesMap.get(uniqueID));
        }
        return entities;
    }

    @Override
    public Set<IEntity> getEntitiesByName(String name) {
        return entitiesMap.values().stream().filter(e -> e.getName().equals(name)).collect(Collectors.toSet());
    }

    @Override
    public boolean containsEntity(String uniqueID) {
        return entitiesMap.containsKey(uniqueID);
    }

    @Override
    public IReturnMessage addEntities(IEntity... entities) {
        return addEntities(new LinkedHashSet<>(Arrays.asList(entities)));
    }

    @Override
    public IReturnMessage addEntities(Set<IEntity> entities) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IEntity entity : entities) {
            if (entitiesMap.containsKey(entity.getUID())) {
                returnMessage.appendErrors(entity.getName() + " is already inside " + getUID() + ". ");
                returnMessage.setExitStatus(1);
            } else {
                entitiesMap.put(entity.getUID(), entity);
                returnMessage.appendInfo(entity.getName() + " is now inside " + getUID() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeEntities(String... uniqueIDs) {
        return removeEntities(new LinkedHashSet<>(Arrays.asList(uniqueIDs)));
    }

    @Override
    public IReturnMessage removeEntities(Set<String> uniqueIDs) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String uniqueID : uniqueIDs) {
            if (!entitiesMap.containsKey(uniqueID)) {
                returnMessage.appendErrors(uniqueID + " is not inside " + getUID() + ". ");
                returnMessage.setExitStatus(1);
            } else {
                entitiesMap.remove(uniqueID);
                returnMessage.appendInfo(uniqueID + " is no longer inside " + getUID() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public void clear() { // TODO: use removeEntities instead?
        entitiesMap.clear();
    }
}
