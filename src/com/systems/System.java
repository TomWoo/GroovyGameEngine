package com.systems;

import com.core.IEntity;
import com.core.IEntitySystem;
import com.core.IReturnMessage;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Tom on 6/11/2017.
 */
public abstract class System implements ISystem {
    private final Set<Class> componentClasses;

    public System() {
        this.componentClasses = new LinkedHashSet<>();
        init(componentClasses);
    }

    public abstract void init(Set<Class> componentClasses);

    public abstract IReturnMessage execute(Set<IEntity> entities);

    @Override
    public final IReturnMessage execute(IEntitySystem universe) {
        Set<IEntity> relevantEntities = universe.getEntities().stream().filter(e -> e.hasComponents(componentClasses)).collect(Collectors.toSet());
        return execute(relevantEntities);
    }
}
