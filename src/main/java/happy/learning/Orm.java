package happy.learning;

import happy.learning.annotation.Column;
import happy.learning.annotation.Table;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RequiredArgsConstructor
public class Orm {
    private final DataSource dataSource;

    /*public Orm() {
        this.dataSource = new PGSimpleDataSource();
    }*/

    /**
     * не обовʼязково перевіряти типи, ти можеш в резалт сета взяти обджект, потім обджект засетити в value і якщо тип
     * не співпаде то воно все одно впаде. Хібер зобовʼязує мати конструктор без аргументів, бо він спочатку створює
     * інстанс а потім сетить покроково поля. Щоб створити інстанс і засетити йому поля треба:
     * getConstructor new instance без нічого, а потім береш field, setAccessible(true) і field set
     *
     */

    /*@SneakyThrows
    public <T> T find(Class<T> type, Object id, Person person) {

        String selectQuery = String.format("select*from %s where id = ?", type.getAnnotation(Table.class).name());
        System.out.println(selectQuery);

        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setObject(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Field[] declaredFields = type.getDeclaredFields();
                    Object[] values = new Object[declaredFields.length];
                    int index = 0;

                    for(Field field : declaredFields) {
                        String variableName = field.getAnnotation(Column.class) != null ? field.getAnnotation(Column.class).name() : field.getType().getName();
                        field.setAccessible(true);
                        Object o = field.get(person);
                        values[index] = resultSet.getObject(variableName);
                        index++;
                    }

                    Constructor<?> declaredConstructor = type.getDeclaredConstructors()[0];
                    declaredConstructor.setAccessible(true);

                    return type.cast(declaredConstructor.newInstance(values));
                }
            }
        }
        return null;
    }*/
}
