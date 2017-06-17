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

    def trace(Object obj) {
        MetaClass c = obj.metaClass
        c.invokeMethod = { String name, Object... args ->
            println("$obj.$name($args) called (tracing object).")
            def result = c.getMetaMethod(name, args).invoke(delegate, args)
            return result
            //delegate.invokeMethod(name, args) // TODO: delegate."$name"(args)
        }
    }

    def traceMethod(Object obj, String methodName, Object[] methodArgs) { // TODO: condition(s)
        MetaClass c = obj.metaClass
        /*
        c.invokeMethod = { String name, Object... args ->
            if(name.equals(methodName)) {
                println("$obj.$name($args) called (tracing method).")
            }
            //def result = c.getMetaMethod(name, args).invoke(delegate, args)
            //return result
        }
        */
        c."$methodName" = {
            println("$obj.$methodName($methodArgs) called (tracing method).")
        }
        //c.invokeMethod(methodName, methodArgs)
    }

    def test() {
        println("string")
    }
}
