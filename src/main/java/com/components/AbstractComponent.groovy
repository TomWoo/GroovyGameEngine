package com.components

import com.core.IReturnMessage
import com.core.ReturnMessage

/**
 * Created by Tom on 6/10/2017.
 * Data contained within an entity.
 */
abstract class AbstractComponent implements IComponent {
    @Override
    final List<String> getKeys() {
        return this.getPropertiesMap().keySet().toList();
        //return this.getClass().getDeclaredFields().toList().stream().map({e -> e.getName()}).collect(Collectors.toList());
        //return this.getMetaClass().getProperties().stream().map({e -> e.getName()}).collect(Collectors.toList());
    }

    @Override
    final Serializable getValue(String key) {
        return (Serializable) this.getProperty(key);
    }

    @Override
    final IReturnMessage setValue(String key, Serializable newValue) {
        IReturnMessage returnMessage = new ReturnMessage("Setting value of " + getClass() + " component: " + key + ". ", "");
        Serializable value = getValue(key); //this.getProperties().get(key);
        if(newValue.getClass().equals(value.getClass())) {
            this.setProperty(key, newValue);
            returnMessage.appendInfo("Successfully set " + key + " to " + newValue + ". ");
        } else {
            returnMessage.appendError("Expecting value of type " + value.getClass());
        }
        return returnMessage;
    }

    @Override // Reference: https://stackoverflow.com/questions/28696988/how-do-i-get-declared-properties-in-grails-domain-objects
    final Map<String, Serializable> getPropertiesMap() {
        //return new LinkedHashMap<String, Serializable>(this.getProperties())
        return this.getClass().getDeclaredFields().findAll {!it.synthetic}.collectEntries {
            [(it.name) : this."$it.name"]
        }
    }

//    @Override
//    final IComponent clone() {
//        IComponent newComponent = (IComponent) this.getClass().newInstance() //def newComponent = this.getClass().newInstance(this.getProperties()) as AbstractComponent
//        InvokerHelper.setProperties(newComponent, this.getProperties())
//        return newComponent
//    }
}
