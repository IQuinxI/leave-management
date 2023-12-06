package ma.emsi.leavemanagement.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Person
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Person {
    private String firstName;
    private String lastName;
    private String phone;
    private int soldePaye;
    private int soldeNonPaye;
    private int soldeMaternité;
    private int soldeMaladie;
    private BigDecimal salire;

}