package com.systems

import com.components.IComponent
import com.components.Mass
import com.components.Position
import com.components.Velocity
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/11/2017.
 */
class Gravity extends System {
    private double g_y = 4.0;

    def void init(Set<Class> componentClasses) {
        componentClasses.addAll(Mass.class, Velocity.class)
    }

    def IReturnMessage execute(Set<IEntity> entities) {
        for(IEntity entity : entities) {
            Velocity v = entity.getComponent(Velocity.class)
            v.setVY(v.getVY() - g_y)
        }
    }
}
