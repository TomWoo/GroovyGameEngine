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

    public abstract void init(Map<String, Serializable> data);

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
        IReturnMessage returnMessage = new ReturnMessage(0, "Setting value of " + getClass() + " component: " + key + ". ", "");
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
