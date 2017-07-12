package com.components

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends Component {
    @Bindable @Vetoable String imageFilename = "default.png"
    //transient ImageView image

    Sprite() {}

    Sprite(String imageFilename) {
        this.imageFilename = imageFilename
        //this.image = new ImageView(UtilityFunctions.getPath(imageFilename)) // TODO: remove
    }
}
