package com.components

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/11/2017.
 */
class Position extends Component {
    @Bindable @Vetoable double x = 0.0
    @Bindable @Vetoable double y = 0.0
    @Bindable @Vetoable double theta = 0.0

    Position() {}

    Position(double x, double y, double theta) {
        this.x = x
        this.y = y
        this.theta = theta
    }
}
