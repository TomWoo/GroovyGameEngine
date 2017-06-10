package sample;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Tom on 6/8/2017.
 */
public interface IEntity {
    String getUID();
    String getName();
    Set<String> getGroupIDs();

    Set<IComponent> getComponents();
    IReturnMessage addComponents(IComponent... components);
    IReturnMessage addComponents(Collection<IComponent> components);
    IReturnMessage removeComponents(String... componentName);
    IReturnMessage removeComponents(Collection<String> componentNames);

    Set<String> getTemplate();
    IReturnMessage addToTemplate(String... componentNames);
    IReturnMessage addToTemplate(Collection<String> componentNames);
    IReturnMessage removeFromTemplate(String... componentNames);
    IReturnMessage removeFromTemplate(Collection<String> componentNames);
    IReturnMessage setTemplate(String... componentNames);
    IReturnMessage setTemplate(Collection<String> componentNames);
}
