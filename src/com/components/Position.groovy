package com.components

/**
 * Created by Tom on 6/11/2017.
 */
class Position extends Component {
    public static final String X_KEY = "x"
    public static final String Y_KEY = "y"
    public static final String THETA_KEY = "theta"

    def void init(Map<String, Serializable> data) {
        data.put(X_KEY, 0.0)
        data.put(Y_KEY, 0.0)
        data.put(THETA_KEY, 0.0)
    }

    def double getX() {
        return (Double) getValue(X_KEY)
    }

    def void setX(double x) {
        setValue(X_KEY, x)
    }

    def double getY() {
        return (Double) getValue(Y_KEY)
    }

    def void setY(double y) {
        setValue(Y_KEY, y)
    }

    def double getTheta() {
        return (Double) getValue(THETA_KEY)
    }

    def void setTheta(double theta) {
        setValue(THETA_KEY, theta)
    }
}
