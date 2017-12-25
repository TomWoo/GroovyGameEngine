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
    @BindableObservableProperty SerializableObservableMap<String, String> imageFilenames = [:] // state : file path
    @BindableObservableProperty String state = "default"
    @BindableObservableProperty int frameIndex = 0
    @BindableObservableProperty int rows = 1
    @BindableObservableProperty int cols = 1

    Sprite() {}

    Sprite(String imageFilename) {
        this(imageFilename, "default")
    }

    Sprite(String imageFilename, String state) {
        this(imageFilename, state, 1, 1)
    }

    Sprite(String spriteSheetFilename, String state, int rows, int cols) {
        assert(rows>0 && cols>0)
        this.imageFilenames[state] = spriteSheetFilename
        //this.imageFilename = spriteSheetFilename
        this.rows = rows
        this.cols = cols
    }

    ImageView getImageView() {
        return AssetManager.getImageView(imageFilenames[state]) // TODO: cache animation properties
    }
}
