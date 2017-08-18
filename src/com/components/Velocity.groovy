package com.components

import com.core.ObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Velocity extends Component {
    @ObservableProperty double vx = 0.0
    @ObservableProperty double vy = 0.0

    Velocity() {}

    Velocity(double vx, double vy) {
        this.vx = vx
        this.vy = vy
    }
}
