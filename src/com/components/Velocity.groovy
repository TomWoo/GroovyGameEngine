package com.components

/**
 * Created by Tom on 6/11/2017.
 */
class Velocity extends Component {
    def init(Map<String, Serializable> data) {
        data.put("vx", 0.0)
        data.put("vy", 0.0)
    }
}
