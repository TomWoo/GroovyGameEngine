package com.components

import com.core.BindableObservableProperty

/**
 * Created by Tom on 6/11/2017.
 */
class Mass extends AbstractComponent {
    @BindableObservableProperty double mass = 1.0

    Mass() {}

    Mass(double mass) {
        this.mass = mass
    }

//    @Override
//    Mass clone() {
//        return new Mass(mass)
//    }
}
