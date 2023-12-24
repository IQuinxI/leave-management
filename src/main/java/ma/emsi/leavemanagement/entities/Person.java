package ma.emsi.leavemanagement.entities;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.emsi.leavemanagement.entities.auth.User;

/**
 * Person
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@Entity
@Builder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public  class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String firstName;
    private String lastName;
    private String phone;
    private int annualBalance;
    private int unpaidBalance;
    private int maternityBalance;
    private int sickBalance;
    private BigDecimal salary;

    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private List<Leave>leaves;

    // TODO: uncomment JsonIgnore
    // @JsonIgnore
    @OneToOne
    private User userAccount;
}