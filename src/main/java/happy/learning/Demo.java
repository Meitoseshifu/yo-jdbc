package happy.learning;

/*import lombok.SneakyThrows;
import org.hibernate.Session;
import org.postgresql.ds.PGSimpleDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Consumer;
import java.util.function.Function;*/

import happy.learning.orm.CacheableSession;
import happy.learning.orm.Session;
import happy.learning.orm.SessionFactory;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Demo {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, 3L);
            System.out.println(person);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            System.out.println(e);
        } finally {
            em.close();
        }
    }

    /*public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        SessionFactory factory = new SessionFactory(dataSource);
        Session session = factory.createSession();
        //CacheableSession session = (CacheableSession) factory.createCacheableSession();

        Person person = session.find(Person.class, 1L);
        Person theSamePerson = session.find(Person.class, 1L);
        System.out.println(person == theSamePerson);
    }*/

    /*@SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        Person person = new Person();
        person.setId(78L);
        person.setFirstName("Focus");
        person.setLastName("Pokus");

        Orm orm = new Orm(dataSource);
        Person person1 = orm.find(person.getClass(), 1L, person);
        System.out.println(person1);
    }*/

/*    private static EntityManagerFactory entityManagerFactory;

    @SneakyThrows
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");*/

        /*var person = doInSessionReturning(entityManager -> entityManager.find(Person.class, 1L));
        var theSamePerson = doInSessionReturning(entityManager -> entityManager.find(Person.class, 1L));
        System.out.println(person == theSamePerson);*/

/*        doInSession(entityManager -> {
            var person1 = entityManager.find(Person.class, 3L);
            System.out.println(person1);
            person1.setLastName("atata");

            var theSamePerson1 = entityManager.createQuery(
                    "select p from Person p where p.firstName = :firstName",
                            Person.class
                    )
                    .setParameter("firstName", "Chuck")
                    .getSingleResult();
            System.out.println(theSamePerson1);
            System.out.println(person1 == theSamePerson1);
        });

    }

    public static void doInSession(Consumer<EntityManager> entityManagerConsumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManagerConsumer.accept(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public static <T> T doInSessionReturning(Function<EntityManager, T> entityManagerFunction) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            var result = entityManagerFunction.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return null;
    }*/



    /*@SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://93.175.204.87:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "insert into persons (first_name, last_name) values ('Andrii', 'Paliichuk')";
                *//*String sql = "select first_name, last_name, sum(length (n.body)) total from persons p\n" +
                        "    inner join notes n on p.id = n.person_id\n" +
                        "group by p.id order by total desc ";*//*
                statement.executeQuery(sql);
               *//* ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    System.out.println(last_name + " " + first_name);
                }*//*
            }
        }
    }*/

    /*@SneakyThrows
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person = em.find(Person.class, 29L);
        System.out.println(person);

        Person samePerson = em.find(Person.class, 29L);
        System.out.println(person==samePerson);

        em.getTransaction().commit();
        em.close();
    }

    public void doInSession(Consumer<EntityManager> entityManagerConsumer) {
        emf.create
    }*/
}
