package com.components

import com.core.ObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Mass extends Component {
    @ObservableProperty double mass = 1.0

    Mass() {}

    Mass(double mass) {
        this.mass = mass
    }
}
