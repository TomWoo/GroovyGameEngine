package com.components

/**
 * Created by Tom on 6/11/2017.
 */
class Mass extends Component {
    public static final String MASS_KEY = "mass"

    def void init(Map<String, Serializable> data) {
        data.put(MASS_KEY, 1.0)
    }

    def double getMass() {
        return (Double) getValue(MASS_KEY)
    }

    def void setMass(double mass) {
        setValue(MASS_KEY, mass)
    }
}
