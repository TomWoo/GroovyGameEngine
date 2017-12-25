package com.components

import com.assets.AssetManager
import com.collections.SerializableObservableMap
import com.core.BindableObservableProperty
import javafx.scene.media.AudioClip

/**
 * Created by Tom on 6/29/2017.
 */
class Sound extends AbstractComponent {
    @BindableObservableProperty boolean isAnimated = false
    @BindableObservableProperty SerializableObservableMap<String, String> audioFilenames = [default : "default.wav"] // state : file path
    @BindableObservableProperty String state = "default"
    //@BindableObservableProperty int stateIndex = 0
    @BindableObservableProperty int time = 0 // in seconds

    Sound() {}

    Sound(String audioFilename) {
        this(audioFilename, 1, 1)
    }

    Sound(String spriteSheetFilename, int rows, int cols) {
        assert(rows>0 && cols>0)
        this.imageFilenames = [spriteSheet : spriteSheetFilename]
        //this.imageFilename = spriteSheetFilename
        this.rows = rows
        this.cols = cols
    }

    AudioClip getAudioClip() {
        return AssetManager.getInstance().getAudioClip(audioFilenames[state])
    }
}
