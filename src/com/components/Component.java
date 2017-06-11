package com.components;

import com.core.IReturnMessage;
import com.core.ReturnMessage;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Tom on 6/10/2017.
 * Data contained within an entity.
 */
public abstract class Component implements IComponent {
    private final Map<String, Serializable> dataMap;

    public Component() {
        this.dataMap = new LinkedHashMap<>();
        init(dataMap);
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public List<String> getKeys() {
        return new ArrayList<>(dataMap.keySet());
    }

    @Override
    public Serializable getValue(String key) {
        return dataMap.get(key);
    }

    @Override
    public IReturnMessage setValue(String key, Serializable newValue) {
        IReturnMessage returnMessage = new ReturnMessage(0, "Setting value of " + getName() + " : " + key + ". ", "");
        Serializable value = dataMap.get(key);
        if(newValue.getClass().equals(value.getClass())) {
            dataMap.put(key, newValue);
        } else {
            returnMessage.appendErrors("Expecting " + value.getClass());
        }
        return returnMessage;
    }
}
