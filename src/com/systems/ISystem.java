package com.systems;

import com.core.IEntity;
import com.core.IEntitySystem;
import com.core.IReturnMessage;

import java.util.Set;

/**
 * Created by Tom on 6/11/2017.
 */
public interface ISystem {
    IReturnMessage execute(IEntitySystem universe);
}
