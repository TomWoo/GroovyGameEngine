package com.assets

import com.Utilities
import groovy.transform.CompileStatic
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.media.AudioClip

/**
 * Created by Tom on 11/6/2017.
 */
@CompileStatic
final class AssetManager {
    public static final String IMAGE_ASSETS_PATH = "assets/images/";
    public static final String AUDIO_ASSETS_PATH = "assets/audio/";

    private static final Map<String, ImageView> imageCache = ["default.png" : new ImageView(getImageFilename("default.png"))]
    private static final Map<String, AudioClip> audioCache = ["default.wav" : new AudioClip(Utilities.getResourceFilename(AUDIO_ASSETS_PATH + "default.wav"))] // TODO: refactor

    private AssetManager() {}

    private static String getImageFilename(String name) {
        return Utilities.getResourceFilename(IMAGE_ASSETS_PATH + name);
    }

    static ImageView getImageView(String name) {
        if(imageCache.containsKey(name)) {
            //return imageCache.get(name)
            // TODO: check performance
            // ImageView cloning
            ImageView imageView = imageCache.get(name)
            Image image = imageView.getImage()
            return new ImageView(image)
        } else {
            try {
                ImageView imageView = new ImageView(getImageFilename(name))
                imageCache.put(name, imageView)
                return imageView
            } catch (Exception ex) {
                return null //imageCache.get("default.png")
            }
        }
    }

    static AudioClip getAudioClip(String name) {
        if(audioCache.containsKey(name)) {
            return audioCache.get(name)
        } else {
            try {
                AudioClip audioClip = new AudioClip(Utilities.getResourcePath(name)) // TODO: check
                audioCache.put(name, audioClip)
                return audioClip
            } catch (Exception ex) {
                return null //audioCache.get("default.png")
            }
        }
    }
}
