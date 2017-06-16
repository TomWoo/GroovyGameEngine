package com.core;

import java.io.*;
import java.util.Set;

public class Controller implements IEditorController {
    private IEntitySystem universe;
    private IEntitySystem palette;

    public Controller() {
        this.universe = new EntitySystem();
        this.palette = new EntitySystem();
    }

    @Override
    public IReturnMessage loadPalette(File file) {
        IReturnMessage returnMessage = new ReturnMessage();
        try {
            palette = UtilityFunctions.deserialize(file, EntitySystem.class); // TODO: catch ClassCastException?
            returnMessage.appendInfo("Loaded " + file + " to palette. ");
        } catch (IOException | ClassNotFoundException e) {
            returnMessage.appendErrors(e.getMessage());
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage savePalette(IEntitySystem palette, File file) {
        IReturnMessage returnMessage = new ReturnMessage();
        try {
            UtilityFunctions.serialize(getPalette(), file);
            returnMessage.appendInfo("Saved palette to " + file);
        } catch (IOException | ClassNotFoundException e) {
            returnMessage.appendErrors(e.getMessage());
        }
        return returnMessage;
    }

    @Override
    public Set<IEntity> getSpritesInPalette(String... uniqueIDs) {
        return getPalette().getEntities(uniqueIDs);
    }

    @Override
    public Set<IEntity> getSpritesInPalette(Set<String> uniqueIDs) {
        return getPalette().getEntities(uniqueIDs);
    }

    @Override
    public IReturnMessage addSpritesToPalette(IEntity... sprites) {
        return getPalette().addEntities(sprites);
    }

    @Override
    public IReturnMessage addSpritesToPalette(Set<IEntity> sprites) {
        return getPalette().addEntities(sprites);
    }

    @Override
    public IReturnMessage removeSpritesFromPalette(String... uniqueIDs) {
        return getPalette().removeEntities(uniqueIDs);
    }

    @Override
    public IReturnMessage removeSpritesFromPalette(Set<String> uniqueIDs) {
        return getPalette().removeEntities(uniqueIDs);
    }

    @Override
    public IReturnMessage addSprite(IEntity sprite, double x, double y) {
        return getUniverse().addEntities();
    }

    @Override
    public IReturnMessage removeSprites(String... uniqueIDs) {
        return null;
    }

    @Override
    public IReturnMessage removeSprites(Set<String> uniqueIDs) {
        return null;
    }

    @Override
    public IEntitySystem getPalette() {
        return palette;
    }

    @Override
    public Set<IEntity> getSprites(String... uniqueIDs) {
        return getUniverse().getEntities(uniqueIDs);
    }

    @Override
    public Set<IEntity> getSprites(Set<String> uniqueIDs) {
        return getUniverse().getEntities(uniqueIDs);
    }

    @Override
    public IReturnMessage clearUniverse() {
        getUniverse().clear();
        return new ReturnMessage(0, getUniverse().getUID() + " cleared. ", "");
    }

    @Override
    public IReturnMessage loadUniverse(File file) {
        IReturnMessage returnMessage = new ReturnMessage();
        try {
            universe = UtilityFunctions.deserialize(file, EntitySystem.class);
            returnMessage.appendInfo("Loaded " + file + " to universe. ");
        } catch (IOException | ClassNotFoundException e) {
            returnMessage.appendErrors(e.getMessage());
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage saveUniverse(IEntitySystem universe, File file) {
        IReturnMessage returnMessage = new ReturnMessage();
        try {
            UtilityFunctions.serialize(getUniverse(), file);
            returnMessage.appendInfo("Saved universe to " + file);
        } catch (IOException | ClassNotFoundException e) {
            returnMessage.appendErrors(e.getMessage());
        }
        return returnMessage;
    }

    @Override
    public IEntitySystem getUniverse() {
        return universe;
    }
}
