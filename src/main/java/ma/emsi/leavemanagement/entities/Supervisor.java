package ma.emsi.leavemanagement.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Supervisor
 */
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Supervisor extends Person {

    public abstract List<Employee> getEmployees();

}