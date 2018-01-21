package com.components

import com.core.IReturnMessage;
import com.core.ReturnMessage
import org.codehaus.groovy.runtime.InvokerHelper

import java.util.*;

/**
 * Created by Tom on 6/10/2017.
 * Data contained within an entity.
 */
abstract class AbstractComponent implements IComponent {
    @Override
    final List<String> getKeys() {
        return new ArrayList<>(this.getProperties().keySet());
    }

    @Override
    final Serializable getValue(String key) {
        return (Serializable) this.getProperty(key);
    }

    @Override
    final IReturnMessage setValue(String key, Serializable newValue) {
        IReturnMessage returnMessage = new ReturnMessage("Setting value of " + getClass() + " component: " + key + ". ", "");
        Object value = this.getProperties().get(key);
        if(newValue.getClass().equals(value.getClass())) {
            setProperty(key, newValue);
            returnMessage.appendInfo("Successfully set to " + newValue + ". ");
        } else {
            returnMessage.appendError("Expecting " + value.getClass());
        }
        return returnMessage;
    }

    @Override
    final Map<String, Serializable> getPropertiesMap() {
        return new LinkedHashMap<String, Serializable>(this.getProperties()) // TODO: test
    }

//    @Override
//    final IComponent clone() {
//        IComponent newComponent = (IComponent) this.getClass().newInstance() //def newComponent = this.getClass().newInstance(this.getProperties()) as AbstractComponent
//        InvokerHelper.setProperties(newComponent, this.getProperties())
//        return newComponent
//    }
}
