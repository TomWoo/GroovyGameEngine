package com.components

import com.core.BindableObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Position extends AbstractComponent {
    @BindableObservableProperty double x = 0.0
    @BindableObservableProperty double y = 0.0
    @BindableObservableProperty double theta = 0.0

    Position() {}

    Position(double x, double y, double theta) {
        this.x = x
        this.y = y
        this.theta = theta
    }
}
