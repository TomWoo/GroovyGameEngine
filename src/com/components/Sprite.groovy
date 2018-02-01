package com.components

import com.assets.AssetManager

import com.collections.SerializableObservableMap
import com.core.BindableObservableProperty
import groovy.beans.Vetoable
import javafx.scene.image.ImageView

import java.beans.PropertyVetoException

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends AbstractComponent {
    @BindableObservableProperty boolean isAnimated = false
    // image(s) and/or texture atlas(es)
    @BindableObservableProperty SerializableObservableMap<String, String> imageFilenames = ["default" : "default.png"] // state : file path
    @BindableObservableProperty @Vetoable String state = "default"
    @BindableObservableProperty @Vetoable int frameIndex = 0
    @BindableObservableProperty @Vetoable int rows = 1
    @BindableObservableProperty @Vetoable int cols = 1

    Sprite() {
        //this.bindPropertyChangeListeners()
        this.bindPropertyChangeVetoers()
    }

    Sprite(String imageFilename) {
        this(imageFilename, "default")
    }

    Sprite(String imageFilename, String state) {
        this(imageFilename, state, 1, 1)
    }

    Sprite(String spriteSheetFilename, String state, int rows, int cols) {
        this()
        assert(rows>0 && cols>0)
        this.imageFilenames[state] = spriteSheetFilename
        //this.imageFilename = spriteSheetFilename
        this.rows = rows
        this.cols = cols
    }

    ImageView getImageView() {
        return AssetManager.getImageView(imageFilenames[state]) // TODO: cache animation properties
    }

    void bindPropertyChangeVetoers() {
        this.addVetoableChangeListener("state", {
            if(!imageFilenames.containsKey(it.getNewValue())) {
                state = it.getOldValue()
                throw new PropertyVetoException("state does not exist in imageFilenames", it)
            }
        })
        this.addVetoableChangeListener("frameIndex", {
            if(it.getNewValue()<0) {
                frameIndex = (int) it.getOldValue()
                throw new PropertyVetoException("frameIndex >= 0 required", it)
            }
        })
        this.addVetoableChangeListener("rows", {
            if(it.getNewValue()<=0) {
                rows = (int) it.getOldValue()
                throw new PropertyVetoException("rows > 0 required", it)
            }
        })
        this.addVetoableChangeListener("cols", {
            if(it.getNewValue()<=0) {
                rows = (int) it.getOldValue()
                throw new PropertyVetoException("cols > 0 required", it)
            }
        })
    }
}
