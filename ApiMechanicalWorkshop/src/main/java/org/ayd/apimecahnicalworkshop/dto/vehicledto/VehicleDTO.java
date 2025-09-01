package org.ayd.apimecahnicalworkshop.dto.vehicledto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long vehicleId;
    private Long clientId;
    private String licencePlate;
    private String brand;
    private String model;
    private String year;
    private String color;
    private String createdAt;
    private String updatedAt;
}
