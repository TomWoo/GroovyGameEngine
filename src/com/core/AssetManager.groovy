package com.core

import com.Utilities
import groovy.transform.CompileStatic
import javafx.scene.image.ImageView
import javafx.scene.media.AudioClip

/**
 * Created by Tom on 11/6/2017.
 */
@CompileStatic
class AssetManager {
    private static Map<String, ImageView> imageCache = ["default.png" : new ImageView(Utilities.getResourcePath("default.png"))]
    private static Map<String, AudioClip> audioCache = ["default.wav" : new AudioClip(Utilities.getResourcePath("default.wav"))] // TODO: check

    static AssetManager instance = new AssetManager()

    private AssetManager() {}

    ImageView getImageView(String name) {
        if(imageCache.containsKey(name)) {
            return imageCache.get(name)
        } else {
            try {
                ImageView imageView = new ImageView(Utilities.getResourcePath(name))
                imageCache.put(name, imageView)
                return imageView
            } catch (Exception e) {
                return imageCache.get("default.png")
            }
        }
    }

    AudioClip getAudioClip(String name) {
        if(audioCache.containsKey(name)) {
            return audioCache.get(name)
        } else {
            try {
                AudioClip audioClip = new AudioClip(Utilities.getResourcePath(name)) // TODO: check
                audioCache.put(name, audioClip)
                return audioClip
            } catch (Exception e) {
                return audioCache.get("default.png")
            }
        }
    }
}
