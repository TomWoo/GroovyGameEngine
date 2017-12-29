package com.core;

import com.collections.ReadOnlySet;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEntitySystem extends Serializable {
    String getUID();
    ReadOnlySet<IEntity> getEntities();
    ReadOnlySet<IEntity> getEntities(String... uniqueIDs);
    ReadOnlySet<IEntity> getEntities(Set<String> uniqueIDs);
    ReadOnlySet<IEntity> getEntitiesByName(String name);
    boolean containsEntity(String uniqueID);
    IReturnMessage addEntities(IEntity... entities);
    IReturnMessage addEntities(Set<IEntity> entities);
    IReturnMessage removeEntities(String... uniqueIDs);
    IReturnMessage removeEntities(Set<String> uniqueIDs);
    void clear();
    void toTop(String uniqueID);
    void toBottom(String uniqueID);

    void addChangeListener(PropertyChangeListener listener);
    void removeAllListeners();
}
