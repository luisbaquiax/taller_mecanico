package org.ayd.apimecahnicalworkshop.dto.suplierdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private Long id;
    private Long userId;
    private String typeSupplier;
}
