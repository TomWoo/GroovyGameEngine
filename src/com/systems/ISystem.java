package com.systems;

import com.components.IComponent;
import com.core.IEntitySystem;
import com.core.IObservable;
import com.core.IReturnMessage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tom on 6/11/2017.
 */
public interface ISystem {
    void init();
    IReturnMessage update(IEntitySystem universe, long dt);
    List<String> getKeys();
    Serializable getValue(String key);
    IReturnMessage setValue(String key, Serializable value);
}
