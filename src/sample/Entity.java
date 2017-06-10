package sample;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Tom on 6/8/2017.
 */
public class Entity implements IEntity {
    private final String uniqueID;
    private final String name;
    private final Set<String> groupIDs;
    private final Set<IComponent> components;
    private final Set<String> template;

    public Entity(String name, Set<String> groupIDs) {
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.groupIDs = groupIDs;
        this.components = new LinkedHashSet<IComponent>();
        this.template = new LinkedHashSet<String>();
    }

    @Override
    public String getUID() {
        return uniqueID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getGroupIDs() {
        return groupIDs;
    }

    @Override
    public Set<IComponent> getComponents() {
        return new LinkedHashSet<IComponent>(components);
    }

    @Override
    public IReturnMessage addComponents(IComponent... components) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IComponent component : components) {
            if(UtilityFunctions.isComponent(componentName)) {
                returnMessage.appendInfo(componentName + " has been removed from " + getName() + ". ");
            } else {
                returnMessage.appendErrors(componentName + " is not an existing component. ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage addComponents(Collection<IComponent> components) {
        return addComponents(components.toArray(new IComponent[components.size()]));
    }

    @Override
    public IReturnMessage removeComponents(String... componentNames) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String componentName : componentNames) {
            if(UtilityFunctions.isComponent(componentName)) {
                returnMessage.appendInfo(componentName + " has been removed from " + getName() + ". ");
            } else {
                returnMessage.appendErrors(componentName + " is not an existing component. ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeComponents(Collection<String> componentNames) {
        return removeComponents(componentNames.toArray(new String[componentNames.size()]));
    }

    @Override
    public Set<String> getTemplate() {
        return new LinkedHashSet<String>(template);
    }

    @Override
    public IReturnMessage addToTemplate(String... componentNames) {

    }

    @Override
    public IReturnMessage addToTemplate(Collection<String> componentNames) {
        return addToTemplate(componentNames.toArray(new String[template.size()]));
    }

    @Override
    public IReturnMessage removeFromTemplate(String... componentNames) {
        IReturnMessage returnMessage = new ReturnMessage();

    }

    @Override
    public IReturnMessage removeFromTemplate(Collection<String> componentNames) {
        return removeFromTemplate(componentNames.toArray(new String[template.size()]));
    }

    @Override
    public IReturnMessage setTemplate(String... componentNames) {
        Set<String> newTemplate = new LinkedHashSet<String>();
        IReturnMessage returnMessage = addToTemplate(componentNames);
        if(returnMessage.getExitStatus()==0) {
            template.clear();
            template.addAll(newTemplate);
            return returnMessage;
        } else {
            returnMessage.appendErrors("Unable to set template. ");
            return returnMessage;
        }
    }

    @Override
    public IReturnMessage setTemplate(Collection<String> componentNames) {
        return setTemplate(componentNames.toArray(new String[template.size()]));
    }
}
