package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 6/8/2017.
 */
public class EntitySystem implements IEntitySystem {
    private final List<Entity> entities; // List maintains render z-order (last element on top)

    public EntitySystem() {
        entities = new ArrayList<Entity>();
    }

    @Override
    public List<IEntity> getEntities() {
        return null;
    }
}
