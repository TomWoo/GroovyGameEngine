package com.systems;

import com.core.IEntity;
import com.core.IEntitySystem
import com.core.IListenable;
import com.core.IReturnMessage;
import com.core.ReturnMessage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/11/2017.
 */
public abstract class System implements ISystem, IListenable {
    Set<Class> componentClasses = []

    abstract IReturnMessage execute(Set<IEntity> entities);

    @Override
    IReturnMessage execute(IEntitySystem universe) {
        Set<IEntity> relevantEntities;
        if(componentClasses.isEmpty()) {
            relevantEntities = universe.getEntities();
        } else {
            relevantEntities = universe.getEntities().stream().filter({e -> e.hasComponents(componentClasses)}).collect(Collectors.toSet());
        }
        return execute(relevantEntities);
    }

    @Override
    final List<String> getKeys() {
        return new ArrayList<>(properties.keySet());
    }

    @Override
    final Serializable getValue(String key) {
        return (Serializable) properties.get(key);
    }

    @Override
    final IReturnMessage setValue(String key, Serializable newValue) {
        IReturnMessage returnMessage = new ReturnMessage(0, "Setting value of " + getClass() + " system: " + key + ". ", "");
        Object value = properties.get(key);
        if(newValue.getClass().equals(value.getClass())) {
            properties.put(key, newValue);
            returnMessage.appendInfo("Successfully set to " + newValue + ". ");
        } else {
            returnMessage.appendErrors("Expecting " + value.getClass());
        }
        return returnMessage;
    }
}
