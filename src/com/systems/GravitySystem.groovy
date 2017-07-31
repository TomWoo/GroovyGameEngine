package com.systems

import com.components.Mass
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/11/2017.
 */
class GravitySystem extends System {
    public static final G_X_KEY = "g_x"
    public static final G_Y_KEY = "g_y"

    void init(Set<Class> componentClasses, Map<String, Serializable> data) {
        componentClasses.addAll(Mass.class, Velocity.class)
        data.put(G_X_KEY, 0.0)
        data.put(G_Y_KEY, -4.0)
    }

    IReturnMessage update(Set<IEntity> entities, long dt) {
        for(IEntity entity : entities) {
            Velocity v = entity.getComponent(Velocity.class)
            v.setVx(v.getVx() + getGX()*dt)
            v.setVy(v.getVy() + getGY()*dt)
        }
    }

    double getGX() {
        return (Double) getValue(G_X_KEY)
    }

    void setGX(double g_x) {
        setValue(G_X_KEY, g_x)
    }

    double getGY() {
        return (Double) getValue(G_Y_KEY)
    }

    void setGY(double g_y) {
        setValue(G_Y_KEY, g_y)
    }
}
