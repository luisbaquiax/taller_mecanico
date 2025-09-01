package org.ayd.apimecahnicalworkshop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PartsPerVehicleDTO {

    private Long jobId;
    private String licencePlate;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleYear;
    private String partName;
    private String partBrand;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private String jobDescription;


    public PartsPerVehicleDTO(Long jobId, String licencePlate, String vehicleBrand,
                                  String vehicleModel, String vehicleYear, String partName,
                                  String partBrand, Integer quantity, BigDecimal price,
                                  LocalDateTime createdAt, String jobDescription) {
        this.jobId = jobId;
        this.licencePlate = licencePlate;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.partName = partName;
        this.partBrand = partBrand;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.jobDescription = jobDescription;
    }

}
