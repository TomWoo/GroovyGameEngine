package com.components

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/29/2017.
 */
class Animated extends Component { // TODO: merge into Sprite component
    @Bindable @Vetoable boolean isPlaying = false
    @Bindable @Vetoable double frameDuration = 10.0 // FPS
    @Bindable @Vetoable String sequenceFilename = "default_animation.png"
    //transient SpriteAnimation animation

    Animated() {}

    Animated(String sequenceFilename, double frameDuration) {
        this.sequenceFilename = sequenceFilename
        this.frameDuration = frameDuration
        //ImageView image = new ImageView(UtilityFunctions.getPath(sequenceFilename))
        //this.animation = new SpriteAnimation(image, frameDuration, )
    }
}