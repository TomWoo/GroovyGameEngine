package com.components

import com.core.IReturnMessage;
import com.core.ReturnMessage

import java.util.*;

/**
 * Created by Tom on 6/10/2017.
 * Data contained within an entity.
 */
abstract class AbstractComponent implements IComponent {
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
        IReturnMessage returnMessage = new ReturnMessage(0, "Setting value of " + getClass() + " component: " + key + ". ", "");
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
