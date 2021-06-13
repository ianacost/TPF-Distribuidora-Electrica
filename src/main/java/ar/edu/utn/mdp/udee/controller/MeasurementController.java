package ar.edu.utn.mdp.udee.controller;

import ar.edu.utn.mdp.udee.model.dto.measurement.MeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.measurement.NewMeasurementDTO;
import ar.edu.utn.mdp.udee.model.dto.range.DateRangeDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.service.MeasurementService;
import ar.edu.utn.mdp.udee.util.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping(MeasurementController.PATH)
public class MeasurementController {

    public final static String PATH = "/measurements";

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping
    public ResponseEntity<MeasurementDTO> add(@RequestBody NewMeasurementDTO newMeasurementDTO) {
        MeasurementDTO measurementDTO = measurementService.addMeasurement(newMeasurementDTO);
        return ResponseEntity.created(
                URI.create(
                        EntityURLBuilder.buildURL(
                                UserController.PATH,
                                measurementDTO.getId()
                        )
                )
        ).body(measurementDTO);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<MeasurementDTO>> getAll(
            Authentication auth,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "50") Integer size,
            @RequestBody DateRangeDTO dateRangeDTO
            ) throws MissingServletRequestParameterException {
        PaginationResponse<MeasurementDTO> paginationResponse;
        String role = auth.getAuthorities().stream().findFirst().orElseThrow(() -> new MissingServletRequestParameterException("role", "string")).getAuthority();

        if (role.equals("ROLE_EMPLOYEE"))
            paginationResponse = measurementService.getAll(
                    PageRequest.of(page, size),
                    LocalDateTime.parse(dateRangeDTO.getSince()),
                    LocalDateTime.parse(dateRangeDTO.getUntil())
            );
        else // If the role is not employee, only the user specific measures are returned.
            paginationResponse = measurementService.getAll(
                    PageRequest.of(page, size),
                    LocalDateTime.parse(dateRangeDTO.getSince()),
                    LocalDateTime.parse(dateRangeDTO.getUntil()),
                    (int)auth.getPrincipal()
            );

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping(UserController.PATH + "/{id}")
    public ResponseEntity<PaginationResponse<MeasurementDTO>> getAllByUserId(
            @PathVariable Integer id,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        return ResponseEntity.ok(measurementService.getAllByUserId(id, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(measurementService.getById(id));
    }

}
