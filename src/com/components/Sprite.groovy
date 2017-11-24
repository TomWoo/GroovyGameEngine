package com.components

import com.core.AssetManager

import com.collections.SerializableObservableMap
import com.core.BindableObservableProperty
import javafx.scene.image.ImageView

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends AbstractComponent {
    @BindableObservableProperty boolean isAnimated = false
    @BindableObservableProperty SerializableObservableMap<String, String> imageFilenames = [default : "default.png"] // state : file path
    @BindableObservableProperty String imageFilename = "default.png"
    @BindableObservableProperty int stateIndex = 0
    @BindableObservableProperty int rows = 1
    @BindableObservableProperty int cols = 1

    Sprite() {}

    Sprite(String imageFilename) {
        this(imageFilename, 1, 1)
    }

    Sprite(String spriteSheetFilename, int rows, int cols) {
        assert(rows>0 && cols>0)
        this.imageFilenames = [spriteSheet : spriteSheetFilename]
        //this.imageFilename = spriteSheetFilename
        this.rows = rows
        this.cols = cols
    }

    ImageView getImageView() {
        return AssetManager.getInstance().getImageView(imageFilename)
    }
}
