package ar.edu.utn.mdp.udee.model.dto.tariff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffTypeDTO {
    private Integer id;
    private String typeName;
}
