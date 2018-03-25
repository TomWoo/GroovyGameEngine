package com.systems

import com.collections.ObservableCollection
import com.collections.SerializableObservableSet
import com.core.IEntity
import com.core.IEntitySystem
import com.core.IObservable
import com.core.IReturnMessage
import com.events.GameEvent
import com.core.ReturnMessage
import com.events.SerializableTimer
import org.codehaus.groovy.control.CompilationFailedException

import java.beans.PropertyChangeListener

/**
 * Created by Tom on 6/14/2017.
 * Event bus
 */
class EventSystem extends AbstractSystem {
    final String EVENT_UID_KEY = "name"
    final Map<String, GameEvent> events = new LinkedHashMap<>()
    //@ObservableCollection
    //final SerializableObservableSet<SerializableTimer> timersMap = new SerializableObservableSet<>();

    @Override
    void init() {}

    @Override
    IReturnMessage update(IEntitySystem universe, long dt) {
        for(String key : getKeys()) {
            // TODO: garbage collect 'lapsed listeners'
        }
        return new ReturnMessage() // TODO
    }

    @Override
    IReturnMessage update(Set<IEntity> entities, long dt) {
        return new ReturnMessage() // TODO: implement
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

    IReturnMessage unregister(IEntitySystem universe, String eventID) {
        GameEvent event = events.get(eventID)
        String uniqueID = event.getObjName()
        Set<IEntity> entities = universe.getEntities(uniqueID) // TODO: don't assume entity
        for(IEntity obj : entities) { // TODO
            //obj.removePropertyChangeListener(obj.getPropertyChangeListeners().find({ e -> e.metaClass."$EVENT_UID_KEY" }))
            events.remove(eventID)
        }
        return new ReturnMessage() // TODO
    }
}
