package com.core;

import com.collections.ReadOnlySet;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IGameplayController {
    IReturnMessage loadUniverse(File file);
    IReturnMessage saveUniverse(IEntitySystem universe, File file);
    IEntitySystem getUniverse();
    Set<IEntity> getSprites(String... uniqueIDs);
    Set<IEntity> getSprites(Set<String> uniqueIDs);

    boolean togglePlayPause();
    IReturnMessage update(long dt);
}
