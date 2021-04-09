package ar.edu.utn.mdp.udee.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "Field username is required.")
    private String username;
    @NotNull(message = "Field password is required.")
    private String password;
    private String name;
    private String lastname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(username, employee.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
