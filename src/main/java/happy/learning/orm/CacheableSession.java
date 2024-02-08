package happy.learning.orm;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class CacheableSession extends Session {
    /*private Map<EntityKey<?>, Object> entitiesMap = new HashMap<>();

    public CacheableSession(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T find(Class<T> type, Object id) {
        var key = new EntityKey<>(type, id);
        var entity = entitiesMap.computeIfAbsent(key, k -> super.find(type, id));
        return type.cast(entity);
    }*/
}
