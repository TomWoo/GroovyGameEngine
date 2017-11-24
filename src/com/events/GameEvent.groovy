package com.events

import com.Utilities;

/**
 * Created by Tom on 6/18/2017.
 */
class GameEvent implements Serializable { // TODO: interface?
    final String uniqueID = Utilities.generateUID() // final enforced at runtime

    // Trigger
    String objName
    String methodName
    String conditions

    // Response
    String response

    public GameEvent(String objName, String methodName, String conditions, String response) {
        this.objName = objName
        this.methodName = methodName
        this.conditions = conditions
        this.response = response
    }

    public GameEvent(String event) {
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

    String getUID() {
        return uniqueID
    }

    String getTrigger() {
        return getObjName() + "." + getMethodName() + ";" + getConditions()
    }

    @Override
    String toString() {
        return getTrigger() + "->" + getResponse()
    }
}
