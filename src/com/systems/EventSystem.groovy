package com.systems

import com.core.IEntity
import com.core.IEntitySystem
import com.core.IObservable
import com.core.IReturnMessage
import com.core.GameEvent
import com.core.ReturnMessage
import org.codehaus.groovy.control.CompilationFailedException

import java.beans.PropertyChangeListener

/**
 * Created by Tom on 6/14/2017.
 * Data map
 */
class EventSystem extends System {
    final String EVENT_UID_KEY = "uniqueID"
    final Map<String, GameEvent> events = new LinkedHashMap<>()

    @Override
    IReturnMessage update(IEntitySystem universe, long dt) {
        for(String key : getKeys()) {
            // TODO: garbage collect 'lapsed listeners'
        }
        return new ReturnMessage()
    }

    @Override
    IReturnMessage update(Set<IEntity> entities, long dt) {
        return new ReturnMessage()
    }

    IReturnMessage register(IObservable obj, String prop, String conditions,
                            String response, String uniqueID) throws CompilationFailedException {
        PropertyChangeListener listener = { e ->
            // TODO: pass parameters
            if (Eval.me(conditions)) {
                Eval.me(response)
            }
        }
        listener.metaClass."$EVENT_UID_KEY" = uniqueID
        obj.addPropertyChangeListener(prop, listener)
        return new ReturnMessage(0, "Listening for changes to $obj.$prop.", "")
    }

    IReturnMessage register(IEntitySystem universe, GameEvent event) {
        IObservable obj = universe.getEntities(event.getObjName()).getAt(0)
        if(obj==null) {
            obj = universe // TODO: use colon for fallback obj type?
        }
        IReturnMessage returnMessage = register(obj, event.getMethodName(), event.getConditions(), event.getResponse(), event.getUID())
        events.put(event.getUID(), event)
        return returnMessage
    }

    void unregister(IEntitySystem universe, String eventID) {
        GameEvent event = events.get(eventID)
        String uniqueID = event.getObjName()
        Set<IObservable> entities = universe.getEntities(uniqueID) // TODO: don't assume entity
        for(IObservable obj : entities) {
            obj.removePropertyChangeListener(obj.getPropertyChangeListeners().find({ e -> e.metaClass."$EVENT_UID_KEY" })) // TODO: check
            events.remove(eventID)
        }
    }
}
