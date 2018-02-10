package com.core;

import com.collections.ReadOnlySet;
import com.collections.SerializableObservableList;
import com.components.IComponent;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEntitySystem extends Serializable {
    String getName();
    Set<IEntity> getEntities();
    SerializableObservableList<IEntity> getEntitiesAsList();
    Set<IEntity> getEntities(String... uniqueIDs);
    Set<IEntity> getEntities(Set<String> uniqueIDs);
    Set<IEntity> getEntitiesByName(String name);
    Set<IEntity> getEntitiesWithComponents(Class<? extends IComponent>... classes);
    Set<IEntity> getEntitiesWithComponents(Set<Class<? extends IComponent>> classes);
    boolean containsEntity(String uniqueID);
    IReturnMessage addEntities(IEntity... entities);
    IReturnMessage addEntities(Set<IEntity> entities);
    IReturnMessage removeEntities(String... uniqueIDs);
    IReturnMessage removeEntities(Set<String> uniqueIDs);
    void clear();
    void sendEntityToTop(String uniqueID);
    void sendEntityToBottom(String uniqueID);

    void addChangeListener(PropertyChangeListener listener);
    void removeAllListeners();
}
