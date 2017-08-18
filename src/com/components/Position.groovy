package com.components

import com.core.ObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Position extends Component {
    @ObservableProperty double x = 0.0
    @ObservableProperty double y = 0.0
    @ObservableProperty double theta = 0.0

    Position() {}

    Position(double x, double y, double theta) {
        this.x = x
        this.y = y
        this.theta = theta
    }
}
