package org.ayd.apimecahnicalworkshop.dto.employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeJobDTO {

    private Long trabajoId;
    private Long vehiculoId;
    private String placaVehiculo;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String tipoMantenimiento;
    private String estadoTrabajo;
    private String descripcion;
    private Double horasEstimadas;
    private String fechaAsignacion;
    private String fechaInicio;
    private String fechaFinalizacion;
    private String clienteNombre;

}
