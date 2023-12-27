package ma.emsi.leavemanagement.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Manager
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Manager extends Person{
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Employee> employees;
}