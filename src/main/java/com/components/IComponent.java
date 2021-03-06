package com.components;

import com.core.IObservable;
import com.core.IReturnMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IComponent extends Serializable, IObservable {
    List<String> getKeys();
    Serializable getValue(String key);
    IReturnMessage setValue(String key, Serializable newValue);
    Map<String, Serializable> getPropertiesMap();
}
