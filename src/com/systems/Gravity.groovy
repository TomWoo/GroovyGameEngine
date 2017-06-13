package com.systems

import com.components.Mass
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/11/2017.
 */
class Gravity extends System {
    public static final G_X_KEY = "g_x"
    public static final G_Y_KEY = "g_y"

    def void init(Set<Class> componentClasses, Map<String, Serializable> data) {
        componentClasses.addAll(Mass.class, Velocity.class)
        data.put(G_X_KEY, 0.0)
        data.put(G_Y_KEY, -4.0)
    }

    def IReturnMessage execute(Set<IEntity> entities) {
        for(IEntity entity : entities) {
            Velocity v = entity.getComponent(Velocity.class)
            v.setVX(v.getVX() + getGX())
            v.setVY(v.getVY() + getGY())
        }
    }

    def double getGX() {
        return (Double) getValue(G_X_KEY)
    }

    def void setGX(double g_x) {
        setValue(G_X_KEY, g_x)
    }

    def double getGY() {
        return (Double) getValue(G_Y_KEY)
    }

    def void setGY(double g_y) {
        setValue(G_Y_KEY, g_y)
    }
}
