package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.partdto.PartDTO;
import org.ayd.apimecahnicalworkshop.entities.Part;
import org.springframework.stereotype.Service;

@Service
public class PartConvert {

    public PartDTO mapToDTO(Part part) {
        return new PartDTO(
                part.getPartId(),
                part.getSupplierId(),
                part.getNamePart(),
                part.getBrandPart(),
                part.getDescriptionPart(),
                part.getCostPrice(),
                part.getSalePrice(),
                part.getStockPart(),
                part.getActive());
    }

    public Part mapToEntity(PartDTO partDTO) {
        return new Part(
                partDTO.getPartId(),
                partDTO.getSupplierId(),
                partDTO.getNamePart(),
                partDTO.getBrandPart(),
                partDTO.getDescriptionPart(),
                partDTO.getCostPrice(),
                partDTO.getSalePrice(),
                partDTO.getStockPart(),
                partDTO.getActive());
    }
}
