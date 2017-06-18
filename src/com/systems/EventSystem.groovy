package com.systems

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
        }
    }

    def IReturnMessage execute(Set<IEntity> entities) {}

    /*
    def register(Object obj, String methodName, String conditions, String response) {
        MetaClass c = obj.metaClass
        c.invokeMethod = { String name, Object... args ->
            if(name.equals(methodName)) { // TODO
                println("$obj.$name($args) called under $conditions")
            }
            return c.getMetaMethod(name, args).invoke(delegate, args)
        }
        //println("$obj.$methodName() registered.")
    }
    */
    def register(Object obj, String methodName, String conditions, String response) {
        MetaClass mc = obj.getMetaClass()
        //Method m = obj.getClass().getMethod(methodName)
        def mm = mc.getMetaMethod(methodName)
        //mc.metaMethods.findAll{it.name.equals(methodName)}.each { args ->
        mc."$methodName" = { Object... args ->
            println("$obj.$methodName($args) called -> " + response + ".")
            //def result = mc.getMetaMethod(methodName, args).invoke(delegate, args)
            def result = mc.invokeMethod(delegate, methodName, args)
            //def result = mm.invoke(delegate, args)
            return result
        }
        println("$obj.$methodName() registered with $conditions.")
    }
    /*
    def register(Object obj, String methodName, String conditions, String response) {
        MetaClass mc = obj.metaClass
        def m = mc.&"$methodName"//mc.getMetaMethod(methodName)
        //mc.metaMethods.findAll{it.name==methodName}.each { args ->
        mc."$methodName" = { args ->
            println("$obj.$methodName($args) called.")
            //def result = mc.getMetaMethod(methodName, args).invoke(delegate, args)
            //def result = mc.invokeMethod(delegate, methodName, args)
            def result = m.invoke(delegate, args)
            return result
        }
        println("$obj.$methodName() registered with $conditions.")
    }
    */

    def register(IEntitySystem universe, String event) {
        String[] eventArr = event.split("->")
        assert eventArr.length==2

        // Trigger
        String trigger = eventArr[0]
        String[] triggerArr = trigger.split(";")
        assert triggerArr.length==2
        String[] methodCallArr = triggerArr[0].split(".")
        assert methodCallArr.length==2
        String objName = methodCallArr[0]

        Object obj = universe.getEntities(objName).getAt(0)
        if(obj==null) {
            obj = universe // TODO: use colon for fallback obj type
        }

        String methodName = methodCallArr[1]
        String conditions = triggerArr[1]

        // Response
        String response = eventArr[1]

        // Register event
        register(obj, methodName, conditions, response)
        setValue(event, true)
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
