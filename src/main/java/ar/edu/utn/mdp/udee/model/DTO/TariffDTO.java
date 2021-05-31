package ar.edu.utn.mdp.udee.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDTO {
    private Integer id;
    private TariffTypeDTO tariffType;
    private float tariffValue;
}
