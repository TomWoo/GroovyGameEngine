package com.core;

import com.Utilities;
import com.collections.ObservableCollection
import com.collections.ReadOnlySet;
import com.collections.SerializableObservableMap
import com.collections.SerializableObservableSet;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/8/2017.
 * System containing entities, which contain components.
 */
public class EntitySystem implements IEntitySystem {
    private final String uniqueID = Utilities.generateUID();
    @ObservableCollection
    private final SerializableObservableMap<String, IEntity> entitiesMap = new SerializableObservableMap<>();

    public String getUID() {
        return uniqueID;
    }

    @Override
    public ReadOnlySet<IEntity> getEntities() {
        return new SerializableObservableSet<IEntity>(entitiesMap.values());
    }

    @Override
    public ReadOnlySet<IEntity> getEntities(String... uniqueIDs) {
        return getEntities(new LinkedHashSet<String>(Arrays.asList(uniqueIDs)));
    }

    @Override
    public ReadOnlySet<IEntity> getEntities(Set<String> uniqueIDs) {
        return new SerializableObservableSet<IEntity>(uniqueIDs.stream().map({e -> entitiesMap.get(e)}).collect(Collectors.toList()));
    }

    @Override
    public ReadOnlySet<IEntity> getEntitiesByName(String name) {
        return entitiesMap.values().stream()
                .filter({e -> e.getName().equals(name)})
                .collect(Collectors.toSet());
    }

    @Override
    public boolean containsEntity(String uniqueID) {
        return entitiesMap.containsKey(uniqueID);
    }

    @Override
    public IReturnMessage addEntities(IEntity... entities) {
        return addEntities(new LinkedHashSet<IEntity>(entities==null? [] : Arrays.asList(entities)));
    }

    @Override
    public IReturnMessage addEntities(Set<IEntity> entities) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IEntity entity : entities) {
            if (entitiesMap.containsKey(entity.getUID())) {
                returnMessage.appendError(entity.getName() + " is already inside " + getUID() + ". ");
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
                returnMessage.appendError(uniqueID + " is not inside " + getUID() + ". ");
            } else {
                entitiesMap.remove(uniqueID);
                returnMessage.appendInfo(uniqueID + " is no longer inside " + getUID() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public void clear() {
        entitiesMap.clear();
    }

    @Override
    public void toTop(String uniqueID) {
        entitiesMap.toBack(uniqueID);
    }

    @Override
    public void toBottom(String uniqueID) {
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
