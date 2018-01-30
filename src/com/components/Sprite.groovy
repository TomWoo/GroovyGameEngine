package com.components

import com.assets.AssetManager

import com.collections.SerializableObservableMap
import com.core.BindableObservableProperty
import javafx.scene.image.ImageView

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends AbstractComponent {
    @BindableObservableProperty boolean isAnimated = false
    // image(s) and/or texture atlas(es)
    @BindableObservableProperty SerializableObservableMap<String, String> imageFilenames = ["default" : "default.png"] // state : file path
    @BindableObservableProperty String state = "default"
    @BindableObservableProperty int frameIndex = 0
    @BindableObservableProperty int rows = 1
    @BindableObservableProperty int cols = 1

    Sprite() {
        this.bindPropertyChangeListeners()
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

    void bindPropertyChangeListeners() {
        this.addPropertyChangeListener("state", {
            if(!imageFilenames.containsKey(it.getNewValue())) {
                state = it.getOldValue()
            }
        })
        this.addPropertyChangeListener("frameIndex", {
            if(it.getNewValue()<0) {
                frameIndex = (int) it.getOldValue()
            }
        })
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        bindPropertyChangeListeners();
    }

//    @Override
//    Sprite clone() {
//        return new Sprite(imageFilenames[state], state, rows, cols)
//    }
}
