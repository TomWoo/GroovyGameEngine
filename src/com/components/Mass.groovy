package com.components

import com.core.BindableObservableProperty
import groovy.beans.Vetoable

import java.beans.PropertyVetoException

/**
 * Created by Tom on 6/11/2017.
 */
class Mass extends AbstractComponent {
    @BindableObservableProperty @Vetoable double mass = 1.0

    Mass() {
        this.bindPropertyChangeVetoers()
    }

    Mass(double mass) {
        this()
        this.mass = mass
    }

    void bindPropertyChangeVetoers() {
        this.addVetoableChangeListener("mass", {
            if(it.getNewValue()<=0) {
                mass = (double) it.getOldValue()
                throw new PropertyVetoException("mass > 0 required", it)
            }
        })
    }
}
