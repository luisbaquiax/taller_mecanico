package org.ayd.apimecahnicalworkshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuotationDetailDTO {

    private Long quotationId;
    private Long jobId;
    private String licencePlate;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleYear;
    private Double total;
    private String quotationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime validUntil;
    private String jobDescription;

    public QuotationDetailDTO(Long quotationId, Long jobId, String licencePlate,
                              String vehicleBrand, String vehicleModel, String vehicleYear,
                              Double total, String quotationStatus, LocalDateTime createdAt,
                              LocalDateTime validUntil, String jobDescription) {
        this.quotationId = quotationId;
        this.jobId = jobId;
        this.licencePlate = licencePlate;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.total = total;
        this.quotationStatus = quotationStatus;
        this.createdAt = createdAt;
        this.validUntil = validUntil;
        this.jobDescription = jobDescription;
    }

}
