package ar.edu.utn.mdp.udee.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterModelDTO {
    private Integer id;
    private MeterBrandDTO meterBrand;
    private String modelName;
}
