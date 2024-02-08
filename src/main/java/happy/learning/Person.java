package happy.learning;

/*import happy.learning.annotation.Column;
import happy.learning.annotation.Table;*/
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    //@Column(name = "first_name")
    private String firstName;

    //@Column(name = "last_name")
    private String lastName;

}
