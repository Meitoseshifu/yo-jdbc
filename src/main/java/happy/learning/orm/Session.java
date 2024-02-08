package happy.learning.orm;

import happy.learning.annotation.Column;
import happy.learning.annotation.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

public class Session {

    /*private static final String SELECT_FROM_TABLE_BY_ID = "select * from %s where id = ?";
    private final DataSource dataSource;

    public Session(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    public <T> T find(Class<T> type, Object id) {
        try(var connection = dataSource.getConnection()) {
            var selectQuery = prepareSelectQuery(type);
            try(var selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setObject(1, id);
                System.out.println("SQL: " + selectQuery);
                var resultSet = selectStatement.executeQuery();
                return createEntityFromResultSet(type, resultSet);
            }
        }
    }

    private String prepareSelectQuery(Class<?> type) {
        var tableAnnotation = type.getDeclaredAnnotation(Table.class);
        var tableName = tableAnnotation.name();
        return String.format(SELECT_FROM_TABLE_BY_ID, tableName);
    }

    @SneakyThrows
    private <T> T createEntityFromResultSet(Class<T> type, ResultSet resultSet) {
        resultSet.next();
        var entity = type.getConstructor().newInstance();
        for(var field : type.getDeclaredFields()) {
            var columnAnnotation = field.getDeclaredAnnotation(Column.class);
            var columnName = columnAnnotation.name();
            field.setAccessible(true);
            var fieldValue = resultSet.getObject(columnName);
            field.set(entity, fieldValue);
        }
        return entity;
    }*/
}
