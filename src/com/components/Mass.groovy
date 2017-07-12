package com.components

import groovy.beans.Bindable
import groovy.beans.Vetoable

/**
 * Created by Tom on 6/11/2017.
 */
class Mass extends Component {
    @Bindable @Vetoable double mass = 1.0

    Mass() {}

    Mass(double mass) {
        this.mass = mass
    }
}
