package com.systems;

import com.core.IEntity;
import com.core.IEntitySystem;
import com.core.IReturnMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Tom on 6/11/2017.
 */
public interface ISystem {
    IReturnMessage execute(IEntitySystem universe);
    List<String> getKeys();
    Serializable getValue(String key);
    IReturnMessage setValue(String key, Serializable value);
}
