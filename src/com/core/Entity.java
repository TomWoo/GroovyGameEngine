package com.core;

import com.components.IComponent;

import java.util.*;

/**
 * Created by Tom on 6/8/2017.
 * Object composed of components.
 */
public class Entity implements IEntity {
    private final transient String uniqueID;
    private String name;
    private final Set<String> groupIDs;
    private final Map<String, IComponent> componentsMap;

    public Entity(String name, String... groupIDs) {
        this(name, Arrays.asList(groupIDs));
    }

    public Entity(String name, List<String> groupIDs) {
        this.uniqueID = UtilityFunctions.generateUID();
        this.name = name;
        this.groupIDs = new LinkedHashSet<>(groupIDs); // TODO: warn?
        this.componentsMap = new LinkedHashMap<>();
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getGroupIDs() {
        return groupIDs;
    }

    @Override
    public IReturnMessage addGroupIDs(String... groupIDs) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String groupID : groupIDs) {
            if (this.groupIDs.add(groupID)) {
                returnMessage.appendInfo(getName() + " is now part of " + groupID + ". ");
            } else {
                returnMessage.appendErrors(getName() + " is already part of " + groupID + ". ");
                returnMessage.setExitStatus(1);
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage addGroupIDs(Collection<String> groupIDs) {
        return addGroupIDs(groupIDs.toArray(new String[groupIDs.size()]));
    }

    @Override
    public Set<IComponent> getComponents() {
        return new HashSet<>(componentsMap.values());
    }

    @Override
    public Set<IComponent> getComponents(String... names) {
        Set<IComponent> components = new HashSet<>();
        for(String name : names) {
            components.add(componentsMap.get(name)); // TODO: check null?
        }
        return components;
    }

    @Override
    public Set<IComponent> getComponents(Collection<String> names) {
        return getComponents(names.toArray(new String[names.size()]));
    }

    @Override
    public boolean hasComponent(String name) {
        return componentsMap.containsKey(name);
    }

    @Override
    public IReturnMessage addComponents(IComponent... components) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(IComponent component : components) {
            String name = component.getName();
            if(!UtilityFunctions.isComponent(name)) {
                returnMessage.appendErrors(name + " is not an existing component. ");
                returnMessage.setExitStatus(1);
            } else if(componentsMap.containsKey(name)) {
                returnMessage.appendErrors(getName() + " already has " + name + ". ");
                returnMessage.setExitStatus(2);
            } else {
                componentsMap.put(name, component);
                returnMessage.appendInfo(name + " has been added to " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage addComponents(Collection<IComponent> components) {
        return addComponents(components.toArray(new IComponent[components.size()]));
    }

    @Override
    public IReturnMessage removeComponents(String... names) {
        IReturnMessage returnMessage = new ReturnMessage();
        for(String name : names) {
            if(!UtilityFunctions.isComponent(name)) {
                returnMessage.appendErrors(name + " is not an existing component. ");
                returnMessage.setExitStatus(1);
            } else if(!componentsMap.containsKey(name)) {
                returnMessage.appendErrors(getName() + " does not have " + name + ". ");
                returnMessage.setExitStatus(2);
            } else {
                componentsMap.remove(name);
                returnMessage.appendInfo(name + " has been removed from " + getName() + ". ");
            }
        }
        return returnMessage;
    }

    @Override
    public IReturnMessage removeComponents(Collection<String> names) {
        return removeComponents(names.toArray(new String[names.size()]));
    }
}
