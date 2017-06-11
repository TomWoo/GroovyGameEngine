package com.components

/**
 * Created by Tom on 6/11/2017.
 */
class Velocity extends Component {
    public static final String V_X_KEY = "v_x"
    public static final String V_Y_KEY = "v_y"

    def init(Map<String, Serializable> data) {
        data.put(V_X_KEY, 0.0)
        data.put(V_Y_KEY, 0.0)
    }

    def double getVX() {
        return (Double) getValue(V_X_KEY)
    }

    def void setVX(double v_x) {
        setValue(V_X_KEY, v_x)
    }

    def double getVY() {
        return (Double) getValue(V_Y_KEY)
    }

    def void setVY(double v_y) {
        setValue(V_Y_KEY, v_y)
    }
}
