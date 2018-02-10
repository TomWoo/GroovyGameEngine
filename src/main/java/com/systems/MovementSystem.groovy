package com.systems

import com.components.Position
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/12/2017.
 */
class MovementSystem extends AbstractSystem {
    @Override
    void init() {
        componentClasses.addAll(Position.class, Velocity.class)
    }

    IReturnMessage update(Set<IEntity> entities, long dt) {
        for(IEntity entity : entities) {
            Position pos = entity.getComponent(Position.class)
            Velocity v = entity.getComponent(Velocity.class)
            pos.setX(pos.getX() + v.getVx()*dt)
            pos.setY(pos.getY() + v.getVy()*dt)
        }
    }
}
