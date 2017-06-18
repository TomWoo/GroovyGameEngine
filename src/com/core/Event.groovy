package com.core;

/**
 * Created by Tom on 6/18/2017.
 */
class Event { // TODO: interface?
    // Trigger
    String objName
    String methodName
    String conditions

    // Response
    String response

    public Event(String objName, String methodName, String conditions, String response) {
        this.objName = objName
        this.methodName = methodName
        this.conditions = conditions
        this.response = response
    }

    public Event(String event) {
        String[] eventArr = event.split("->")
        assert eventArr.length==2

        // Trigger
        String trigger = eventArr[0]
        String[] triggerArr = trigger.split(";")
        assert triggerArr.length==2
        String[] methodCallArr = triggerArr[0].split(".")
        assert methodCallArr.length==2
        this.objName = methodCallArr[0]

        this.methodName = methodCallArr[1]
        this.conditions = triggerArr[1]

        // Response
        this.response = eventArr[1]
    }

    def String getTrigger() {
        return getObjName() + "." + getMethodName() + ";" + getConditions()
    }

    @Override
    def String toString() {
        return getTrigger() + "->" + getResponse()
    }
}
