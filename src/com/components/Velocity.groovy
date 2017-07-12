package com.components

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/11/2017.
 */
class Velocity extends Component {
    @Bindable @Vetoable double vx = 0.0
    @Bindable @Vetoable double vy = 0.0

    Velocity() {}

    Velocity(double vx, double vy) {
        this.vx = vx
        this.vy = vy
    }
}
