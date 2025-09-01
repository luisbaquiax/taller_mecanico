package org.ayd.apimecahnicalworkshop.dto.typesServices;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
}