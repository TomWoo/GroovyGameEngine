package com.events

/**
 * Created by Tom on 6/14/2017.
 */
class EventLogDecorator {
    private delegate

    private EventLogDecorator(delegate) {
        this.delegate = delegate
    }

    public static wrap(delegate) {
        if(delegate instanceof EventLogDecorator) {
            return delegate
        } else {
            return new EventLogDecorator(delegate)
        }
    }

    def invokeMethod(String name, args) {
        if(name.contains("set") || name.contains("add") || name.contains("remove")) { // TODO: rigorize
            println(delegate + "." + name + "(" + args + ")")
        }
        delegate.invokeMethod(name, args)
    }
}
