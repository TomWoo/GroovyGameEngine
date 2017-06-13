package com.systems;

import com.core.IEntity;
import com.core.IEntitySystem;
import com.core.IReturnMessage;
import com.core.ReturnMessage;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/11/2017.
 */
public abstract class System implements ISystem {
    private final Set<Class> componentClasses;
    private final Map<String, Serializable> dataMap;

    public System() {
        this.componentClasses = new LinkedHashSet<>();
        this.dataMap = new LinkedHashMap<>();
        init(componentClasses, dataMap);
    }

    public abstract void init(Set<Class> componentClasses, Map<String, Serializable> data);

    public abstract IReturnMessage execute(Set<IEntity> entities);

    @Override
    public final IReturnMessage execute(IEntitySystem universe) {
        Set<IEntity> relevantEntities = universe.getEntities().stream().filter(e -> e.hasComponents(componentClasses)).collect(Collectors.toSet());
        return execute(relevantEntities);
    }

    @Override
    public final List<String> getKeys() {
        return new ArrayList<>(dataMap.keySet());
    }

    @Override
    public final Serializable getValue(String key) {
        return dataMap.get(key);
    }

    @Override
    public final IReturnMessage setValue(String key, Serializable newValue) {
        IReturnMessage returnMessage = new ReturnMessage(0, "Setting value of " + getClass() + " system: " + key + ". ", "");
        Serializable value = dataMap.get(key);
        if(newValue.getClass().equals(value.getClass())) {
            dataMap.put(key, newValue);
            returnMessage.appendInfo("Successfully set to " + newValue + ". ");
        } else {
            returnMessage.appendErrors("Expecting " + value.getClass());
        }
        return returnMessage;
    }
}
