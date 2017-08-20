package com.components

import com.core.BindableObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Velocity extends AbstractComponent {
    @BindableObservableProperty double vx = 0.0
    @BindableObservableProperty double vy = 0.0

    Velocity() {}

    Velocity(double vx, double vy) {
        this.vx = vx
        this.vy = vy
    }
}
