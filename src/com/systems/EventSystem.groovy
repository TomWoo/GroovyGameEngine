package com.systems

import com.core.Event
import com.core.IEntity
import com.core.IEntitySystem
import com.core.IReturnMessage

import java.lang.reflect.Method

/**
 * Created by Tom on 6/14/2017.
 * Data map
 */
class EventSystem extends System {
    def void init(Set<Class> componentClasses, Map<String, Serializable> data) {}

    def IReturnMessage execute(IEntitySystem universe) {
        for(String key : getKeys()) {
            // TODO
        }
    }

    def IReturnMessage execute(Set<IEntity> entities) {}

    def register(Object obj, String methodName, String conditions, String response) {
        MetaClass c = obj.getMetaClass()
        c.invokeMethod = { String name, Object... args ->
            if(name.equals(methodName)) { // TODO
                println("$obj.$name($args) called when $conditions...")
            }
            def result = c.invokeMethod(name, args)
            if(name.equals(methodName)) { // TODO
                println("$response... DONE.")
            }
            return result
        }
        println("$obj.$methodName() registered.")
    }

    def register(IEntitySystem universe, Event event) {
        Object obj = universe.getEntities(event.getObjName()).getAt(0)
        if(obj==null) {
            obj = universe // TODO: use colon for fallback obj type
        }

        // Register event
        register(obj, event.getMethodName(), event.getConditions(), event.getResponse())
        setValue(event.toString(), true)
        println("$event registered.")
    }

    def unregister(Object obj, String methodName, String conditions) {
        MetaClass c = obj.metaClass
        obj = (GroovyObject) obj
        obj.getProperty("")
    }

    def unregister(IEntitySystem universe, String event) {
        // TODO
        println("$event unregistered.")
    }
}
