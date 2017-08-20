package com.components

import com.collections.SerializableObservableMap
import com.core.BindableObservableProperty

/**
 * Created by Tom on 6/29/2017.
 */
class Sprite extends AbstractComponent {
    @BindableObservableProperty boolean isAnimated = false
    @BindableObservableProperty SerializableObservableMap<String, String> spriteSheets = [default : "default.png"] // state : file path

    Sprite() {}

    Sprite(String spriteSheet) {
        this.spriteSheets = [default : spriteSheet]
    }
}
