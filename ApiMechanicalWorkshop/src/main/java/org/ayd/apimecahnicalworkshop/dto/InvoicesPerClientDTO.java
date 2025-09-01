package org.ayd.apimecahnicalworkshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InvoicesPerClientDTO {



    private Long invoiceId;
    private Long jobId;
    private String licencePlate;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleYear;
    private Double total;
    private String invoiceStatus;
    private LocalDateTime issuedAt;
    private String jobDescription;


    public InvoicesPerClientDTO(Long invoiceId, Long jobId, String licencePlate,
                             String vehicleBrand, String vehicleModel, String vehicleYear,
                             Double total, String invoiceStatus, LocalDateTime issuedAt,
                             String jobDescription) {
        this.invoiceId = invoiceId;
        this.jobId = jobId;
        this.licencePlate = licencePlate;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.total = total;
        this.invoiceStatus = invoiceStatus;
        this.issuedAt = issuedAt;
        this.jobDescription = jobDescription;
    }

}
