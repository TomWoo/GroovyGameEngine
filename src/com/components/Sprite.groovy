package com.components

import com.collections.SerializableObservableMap
import com.core.ObservableProperty

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends Component {
    @ObservableProperty boolean isAnimated = false
    @ObservableProperty SerializableObservableMap<String, String> spriteSheets = [default : "default.png"] // state : file path

    Sprite() {}

    Sprite(String spriteSheet) {
        this.spriteSheets = [default : spriteSheet]
    }
}
