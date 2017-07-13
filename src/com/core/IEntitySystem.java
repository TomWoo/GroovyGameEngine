package com.core;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEntitySystem extends Serializable, IObservable {
    String getUID();
    Set<IEntity> getEntities();
    Set<IEntity> getEntities(String... uniqueIDs);
    Set<IEntity> getEntities(Set<String> uniqueIDs);
    Set<IEntity> getEntitiesByName(String name);
    boolean containsEntity(String uniqueID);
    IReturnMessage addEntities(IEntity... entities);
    IReturnMessage addEntities(Set<IEntity> entities);
    IReturnMessage removeEntities(String... uniqueIDs);
    IReturnMessage removeEntities(Set<String> uniqueIDs);
    void clear();
}
