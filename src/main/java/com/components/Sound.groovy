package com.components

import com.core.AssetManager
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
    @BindableObservableProperty double time = 0 // in ms

    Sound() {}

    Sound(String audioFilename) {
        this(audioFilename, "default")
    }

    Sound(String audioFilename, String state) {
        this.audioFilenames[state] = audioFilename
    }

    AudioClip getAudioClip() {
        return AssetManager.getAudioClip(audioFilenames[state])
    }
}
