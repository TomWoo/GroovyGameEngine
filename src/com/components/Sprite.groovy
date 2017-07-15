package com.components

import com.collections.SerializableObservableMap
import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends Component {
    @Bindable @Vetoable boolean isAnimated = false
    SerializableObservableMap<String, String> spriteSheets = [default : "default.png"] // state : file path

    Sprite() {}

    Sprite(String spriteSheet) {
        this.spriteSheet = [default : spriteSheet]
    }
}
