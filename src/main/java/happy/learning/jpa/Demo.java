package happy.learning.jpa;

import happy.learning.Person;
import org.postgresql.ds.PGSimpleDataSource;

@SuppressWarnings(value = "Duplicates")
public class Demo {
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        SessionFactory factory = new SessionFactory(dataSource);
        Session session = factory.createSession();

        Person person = session.find(Person.class, 1L);
        Person theSamePerson = session.find(Person.class, 1L);
        //theSamePerson.setLastName("Xy9k");
        System.out.println(person == theSamePerson);

        session.close();
    }
}
