package com.core;

import com.collections.ReadOnlySet;

import java.io.File;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEditorController extends IGameplayController {
    boolean isPlaying();
    IEntitySystem getPalette();
    IReturnMessage loadPalette(File file);
    IReturnMessage savePalette(IEntitySystem palette, File file);
    ReadOnlySet<IEntity> getSpritesInPalette(String... uniqueIDs);
    ReadOnlySet<IEntity> getSpritesInPalette(Set<String> uniqueIDs);
    IReturnMessage addSpritesToPalette(IEntity... sprites);
    IReturnMessage addSpritesToPalette(Set<IEntity> sprites);
    IReturnMessage removeSpritesFromPalette(String... uniqueIDs);
    IReturnMessage removeSpritesFromPalette(Set<String> uniqueIDs);

    IReturnMessage addSprite(IEntity sprite, double x, double y);
    IReturnMessage removeSprites(String... uniqueIDs);
    IReturnMessage removeSprites(Set<String> uniqueIDs);
    IReturnMessage clearUniverse();
}
