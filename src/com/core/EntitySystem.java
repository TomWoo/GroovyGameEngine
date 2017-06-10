package com.core;

import java.util.*;

/**
 * Created by Tom on 6/8/2017.
 */
public class EntitySystem implements IEntitySystem {
    private final String uniqueID;
    private final Map<String, IEntity> entitiesMap;

    public EntitySystem() {
        uniqueID = UUID.randomUUID().toString();
        entitiesMap = new HashMap<String, IEntity>();
    }

    public String getUID() {
        return uniqueID;
    }

    @Override
    public Set<IEntity> getEntities() {
        return new HashSet<IEntity>(entitiesMap.values());
    }

    @Override
    public Set<IEntity> getEntities(String... uniqueIDs) {
        Set<IEntity> entities = new HashSet<IEntity>();
        for(String uniqueID : uniqueIDs) {
            entities.add(entitiesMap.get(uniqueID)); // TODO: check null?
        }
        return entities;
    }

    @Override
    public Set<IEntity> getEntities(Collection<String> uniqueIDs) {
        return getEntities(uniqueIDs.toArray(new String[uniqueIDs.size()]));
    }

    @Override
    public Set<IEntity> getEntitiesByNames(String... names) {
        Set<IEntity> entities = new HashSet<IEntity>();
        for(String name : names) {
            entities.add(entitiesMap.get(name));
        }
        return entities;
    }

    @Override
    public Set<IEntity> getEntitiesByNames(Collection<String> names) {
        return getEntitiesByNames(names.toArray(new String[names.size()]));
    }

    @Override
    public boolean containsEntity(String uniqueID) {
        return entitiesMap.containsKey(uniqueID);
    }

    @Override
    public IReturnMessage addEntities(IEntity... entities) {
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
    public IReturnMessage addEntities(Collection<IEntity> entities) {
        return addEntities(entities.toArray(new IEntity[entities.size()]));
    }

    @Override
    public IReturnMessage removeEntities(String... uniqueIDs) {
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
    public IReturnMessage removeEntities(Collection<String> uniqueIDs) {
        return removeEntities(uniqueIDs.toArray(new String[uniqueIDs.size()]));
    }

    @Override
    public void clear() {
        entitiesMap.clear();
    }
}
