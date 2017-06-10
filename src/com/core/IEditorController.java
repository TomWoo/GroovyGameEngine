package com.core;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEditorController extends IGamePlayerController {
    IReturnMessage loadPalette(File file);
    IReturnMessage savePalette(IEntitySystem palette, File file);
    Set<IEntity> getSpritesInPalette(String... uniqueIDs);
    Set<IEntity> getSpritesInPalette(Collection<String> uniqueIDs);
    IReturnMessage addSpritesToPalette(IEntity... sprites);
    IReturnMessage addSpritesToPalette(Collection<IEntity> sprites);
    IReturnMessage removeSpritesFromPalette(String... uniqueIDs);
    IReturnMessage removeSpritesFromPalette(Collection<String> uniqueIDs);

    IReturnMessage addSprite(IEntity sprite, double x, double y);
    IReturnMessage removeSprites(String... uniqueIDs);
    IReturnMessage removeSprites(Collection<String> uniqueIDs);
    IReturnMessage clearUniverse();
}
