package ar.edu.utn.mdp.udee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initial_measure_id", referencedColumnName = "id")
    private Measurement initialMeasurement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "final_measure_id", referencedColumnName = "id")
    private Measurement finalMeasurement;

    @NotNull(message = "Field date is required.")
    private Date billDate;

    private Float amountPayed;

    private Float consumption;

    @NotNull(message = "Field payed is required.")
    @Min(value = 0, message = "Field payed must be a positive number.")
    private Float total;

}
