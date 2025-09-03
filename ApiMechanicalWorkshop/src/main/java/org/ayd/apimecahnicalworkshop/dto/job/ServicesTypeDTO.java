package org.ayd.apimecahnicalworkshop.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesTypeDTO {
    private Long serviceTypeId;
    private String nameServiceType;
    private String descriptionServiceType;
    private Double basePrice;
}
