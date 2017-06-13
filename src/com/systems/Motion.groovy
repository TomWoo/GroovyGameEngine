package com.systems

import com.components.Mass
import com.components.Position
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/12/2017.
 */
class Motion extends System {
    def void init(Set<Class> componentClasses, Map<String, Serializable> data) {
        componentClasses.addAll(Position.class, Velocity.class)
    }

    def IReturnMessage execute(Set<IEntity> entities) {
        for(IEntity entity : entities) {
            Position pos = entity.getComponent(Position.class)
            Velocity v = entity.getComponent(Velocity.class)
            pos.setX(pos.getX() + v.getVX())
            pos.setY(pos.getY() + v.getVY())
        }
    }
}
