package ar.edu.utn.mdp.udee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tariffs")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_type_id", referencedColumnName = "id")
    private TariffType tariffType;

    @NotNull(message = "Field value is required.")
    @Min(value = 0, message = "Field value must be a positive number.")
    private float tariffValue;
}
