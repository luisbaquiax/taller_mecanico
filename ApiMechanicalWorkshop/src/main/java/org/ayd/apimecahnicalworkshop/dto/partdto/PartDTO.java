package org.ayd.apimecahnicalworkshop.dto.partdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PartDTO {
    private Long partId;
    private Long supplierId;
    private String namePart;
    private String brandPart;
    private String descriptionPart;
    private Double costPrice;
    private Double salePrice;
    private int stockPart;
    private Boolean active;
}
