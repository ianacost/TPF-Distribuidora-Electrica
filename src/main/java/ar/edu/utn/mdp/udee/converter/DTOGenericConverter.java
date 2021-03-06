package ar.edu.utn.mdp.udee.converter;

import ar.edu.utn.mdp.udee.model.*;
import ar.edu.utn.mdp.udee.model.dto.address.AddressDTO;
import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.meter.ElectricMeterDTO;
import ar.edu.utn.mdp.udee.model.dto.tariff.TariffDTO;
import ar.edu.utn.mdp.udee.model.dto.tariff.TariffTypeDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserConsumptionDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserDTO;
import ar.edu.utn.mdp.udee.model.dto.user.UserTypeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DTOGenericConverter implements GenericConverter {

    private ModelMapper modelMapper;

    public DTOGenericConverter (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair[] convertiblePairs = new ConvertiblePair[] {
                // User
                new ConvertiblePair(User.class, UserDTO.class),
                new ConvertiblePair(UserDTO.class, User.class),
                new ConvertiblePair(UserType.class, UserTypeDTO.class),
                new ConvertiblePair(UserTypeDTO.class, UserType.class),
                new ConvertiblePair(User.class, UserConsumptionDTO.class),
                // Tariff
                new ConvertiblePair(Tariff.class, TariffDTO.class),
                new ConvertiblePair(TariffDTO.class, Tariff.class),
                new ConvertiblePair(TariffType.class, TariffTypeDTO.class),
                new ConvertiblePair(TariffTypeDTO.class, TariffType.class),
                // measurement
                new ConvertiblePair(NewMeasurementDTO.class, Measurement.class),
                new ConvertiblePair(Measurement.class, MeasurementDTO.class),
                // ElectricMeter
                new ConvertiblePair(ElectricMeter.class, ElectricMeterDTO.class),
                new ConvertiblePair(ElectricMeterDTO.class, ElectricMeter.class),
                // Address
                new ConvertiblePair(Address.class, AddressDTO.class),
                new ConvertiblePair(AddressDTO.class, Address.class),
                // Bill
                new ConvertiblePair(Bill.class, BillDTO.class),
                new ConvertiblePair(BillDTO.class, Bill.class)
        };
        return Set.of(convertiblePairs);
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {

        Object result = modelMapper.map(o, targetType.getType());

        if (sourceType.equals(TypeDescriptor.valueOf(NewMeasurementDTO.class)) && targetType.equals(TypeDescriptor.valueOf(Measurement.class))) {
            NewMeasurementDTO input = (NewMeasurementDTO)o;

            Measurement output = (Measurement)result;
            output.setMeasure(input.getValue());
            output.setMeasureDateTime(LocalDateTime.parse(input.getDate()));

            result = output;
        }

        if (sourceType.equals(TypeDescriptor.valueOf(Measurement.class)) && targetType.equals(TypeDescriptor.valueOf(MeasurementDTO.class))) {
            Measurement input = (Measurement)o;

            MeasurementDTO output = (MeasurementDTO)result;
            output.setBillId(input.getBill() == null ? null : input.getBill().getId());
            output.setElectricMeterId(input.getElectricMeter() == null ? null : input.getElectricMeter().getId());

            result = output;
        }

        return result;
    }
}
