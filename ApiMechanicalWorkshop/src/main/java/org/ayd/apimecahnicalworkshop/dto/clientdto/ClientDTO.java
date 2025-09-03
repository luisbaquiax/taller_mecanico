package org.ayd.apimecahnicalworkshop.dto.clientdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long clientId;
    private Long userId;
    private String nit;
    private String address;
}
