package happy.learning.jpa;

import happy.learning.annotation.Column;
import happy.learning.annotation.Table;
import happy.learning.orm.EntityKey;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Session {

    private static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id=?";
    private static final String UPDATE_BY_ID = "UPDATE %s SET %s WHERE id=%s";

    private final DataSource dataSource;

    private Map<EntityKey<?>, Object> entitiesMap = new HashMap<>();
    private Map<EntityKey<?>, Object[]> entitiesSnapshotMap = new HashMap<>();

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    public <T> T find(Class<T> type, Object id) {
        var key = new EntityKey<>(type, id);
        var entity = entitiesMap.computeIfAbsent(key, this::loadFromDB);
        return type.cast(entity);
    }


    @SneakyThrows
    private <T> T loadFromDB(EntityKey<T> entityKey) {
        try(var connection = dataSource.getConnection()) {
            var selectQuery = prepareSelectQuery(entityKey.type());
            try(var selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setObject(1, entityKey.id());
                var resultSet = selectStatement.executeQuery();
                return createEntityFromResultSet(entityKey, resultSet);
            }
        }
    }

    private String prepareSelectQuery(Class<?> type) {
        var tableAnnotation = type.getDeclaredAnnotation(Table.class);
        var tableName = tableAnnotation.name();
        return String.format(SELECT_BY_ID, tableName);
    }

    @SneakyThrows
    private <T> T createEntityFromResultSet(EntityKey<T> entityKey, ResultSet resultSet) {
        Class<T> type = entityKey.type();
        Field[] declaredFields = sortFieldsByName(type.getDeclaredFields());
        Object[] snapshotCopies = new Object[declaredFields.length];

        if (resultSet.next()) {
            T entity = type.getConstructor().newInstance();
            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                String columnName = columnAnnotation.name();
                field.setAccessible(true);

                Object fieldValue = resultSet.getObject(columnName);
                field.set(entity, fieldValue);
                snapshotCopies[i] = fieldValue;
            }
            entitiesSnapshotMap.put(entityKey, snapshotCopies);
            return entity;
        }
        throw new RuntimeException("ResultSet is empty");
    }

    public void close() {
        entitiesMap.entrySet()
                .stream()
                .filter(this::hasChanged)
                .forEach(this::performUpdate);
    }

    private boolean hasChanged(Map.Entry<EntityKey<?>, Object> entityEntry) {
        EntityKey<?> key = entityEntry.getKey();
        if (entitiesSnapshotMap.containsKey(key)) {
            Object[] snapshotValues = entitiesSnapshotMap.get(key);
            Object[] entityValues = extractValuesFromEntity(entityEntry.getValue(), new Object[snapshotValues.length]);

            for (int i = 0; i < entityValues.length; i++) {
                if (notEqual(entityValues[i], snapshotValues[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean notEqual(Object entityValue, Object snapshotValue) {
        return entityValue != snapshotValue && entityValue.hashCode() != snapshotValue.hashCode();
    }

    @SneakyThrows
    private Object[] extractValuesFromEntity(Object entity, Object[] entityValues) {
        Field[] declaredFields = sortFieldsByName(entity.getClass().getDeclaredFields());

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            entityValues[i] = field.get(entity);
        }

        return entityValues;
    }

    private Field[] sortFieldsByName(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .sorted(Comparator.comparing(Field::getName))
                .toArray(Field[]::new);
    }

    @SneakyThrows
    private void performUpdate(Map.Entry<EntityKey<?>, Object> entityKeyObjectEntry) {
        String updateQuery = buildUpdateQuery(entityKeyObjectEntry.getKey(), entityKeyObjectEntry.getValue());

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    private String buildUpdateQuery(EntityKey<?> key, Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        StringBuilder nameValuePairBuilder = new StringBuilder();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            String name = extractColumnName(field);
            String value = field.get(entity).toString();

            nameValuePairBuilder.append(name);
            nameValuePairBuilder.append("='");
            nameValuePairBuilder.append(value);
            nameValuePairBuilder.append("'");

            if (notLastNameValuePair(i, declaredFields.length - 1)) {
                nameValuePairBuilder.append(", ");
            }
        }

        return String.format(UPDATE_BY_ID, extractTableName(key.type()), nameValuePairBuilder, key.id());
    }

    private boolean notLastNameValuePair(int counter, int arrayLength) {
        return counter != arrayLength;
    }

    private String extractTableName(Class<?> type) {
        return type.getAnnotation(Table.class).name() != null ?
                                                              type.getAnnotation(Table.class).name() :
                                                              type.getSimpleName().toLowerCase();
    }

    private String extractColumnName(Field field) {
        return field.getAnnotation(Column.class).name() != null ?
                                                                field.getAnnotation(Column.class).name() :
                                                                field.getName();
    }
}
