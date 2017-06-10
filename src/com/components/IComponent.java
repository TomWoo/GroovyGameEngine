package com.components;

import java.io.Serializable;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IComponent<T> extends Serializable {
    T getValue();
}
