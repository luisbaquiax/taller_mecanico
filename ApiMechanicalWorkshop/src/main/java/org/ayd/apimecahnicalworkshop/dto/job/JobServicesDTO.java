package org.ayd.apimecahnicalworkshop.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class JobServicesDTO {
    private Long jobServiceId;
    private Long jobId;
    private Long serviceTypeId;
    private int quantity;
    private Double price;
}
