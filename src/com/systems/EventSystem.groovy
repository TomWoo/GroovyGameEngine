package com.systems

import com.components.IComponent
import com.core.IEntity
import com.core.IReturnMessage

/**
 * Created by Tom on 6/14/2017.
 */
class EventSystem extends System {
    private List<String> events = new ArrayList<>();

    def void init(Set<Class> componentClasses, Map<String, Serializable> data) {}

    def IReturnMessage execute(Set<IEntity> entities) {
        for(IEntity entity : entities) {
            for(IComponent component : entity.getComponents()) {
                if(component.hasProperty("log")) {

                }
            }
        }
    }
}
