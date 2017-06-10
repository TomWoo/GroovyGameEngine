package sample;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Tom on 6/9/2017.
 */
public interface IEntitySystem {
    String getUID();
    Set<IEntity> getEntities();
    Set<IEntity> getEntities(String... uniqueIDs);
    Set<IEntity> getEntities(Collection<String> uniqueIDs);
    Set<IEntity> getEntitiesByNames(String... names);
    Set<IEntity> getEntitiesByNames(Collection<String> names);
    boolean containsEntity(String uniqueID);
    IReturnMessage addEntities(IEntity... entities);
    IReturnMessage addEntities(Collection<IEntity> entities);
    IReturnMessage removeEntities(String... uniqueIDs);
    IReturnMessage removeEntities(Collection<String> uniqueIDs);
}
