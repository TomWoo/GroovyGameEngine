package com.systems

import com.components.Mass
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage
import com.core.ReturnMessage

/**
 * Created by Tom on 6/11/2017.
 */
class GravitySystem extends System {
    double gx = 0.0
    double gy = -4.0

    @Override
    void init() {
        componentClasses.addAll(Mass.class, Velocity.class)
    }

    IReturnMessage update(Set<IEntity> entities, long dt) {
        for(IEntity entity : entities) {
            Velocity v = entity.getComponent(Velocity.class)
            v.setVx(v.getVx() + getGx()*dt)
            v.setVy(v.getVy() + getGy()*dt)
        }
        return new ReturnMessage() // TODO
    }
}
