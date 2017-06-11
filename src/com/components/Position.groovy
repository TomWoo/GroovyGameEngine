package com.components

/**
 * Created by Tom on 6/11/2017.
 */
class Position extends Component {
    public static final String X_KEY = "x"
    public static final String Y_KEY = "y"
    public static final String THETA_KEY = "theta"

    def init(Map<String, Serializable> data) {
        data.put(X_KEY, 0.0)
        data.put(Y_KEY, 0.0)
        data.put(THETA_KEY, 0.0)
    }

    // TODO: remove?
    def getX() {
        return getValue(X_KEY)
    }

    def getY() {
        return getValue(Y_KEY)
    }

    def getTheta() {
        return getValue(THETA_KEY)
    }
}
