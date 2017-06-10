package sample;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IEntity {
    String getUID();
    String getName();
    void setName(String name);

    Set<String> getGroupIDs();
    IReturnMessage addGroupIDs(String... groupIDs);
    IReturnMessage addGroupIDs(Collection<String> groupIDs);

    Set<IComponent> getComponents();
    Set<IComponent> getComponents(String... names);
    Set<IComponent> getComponents(Collection<String> names);
    boolean hasComponent(String name);
    IReturnMessage addComponents(IComponent... components);
    IReturnMessage addComponents(Collection<IComponent> components);
    IReturnMessage removeComponents(String... names);
    IReturnMessage removeComponents(Collection<String> names);
}
