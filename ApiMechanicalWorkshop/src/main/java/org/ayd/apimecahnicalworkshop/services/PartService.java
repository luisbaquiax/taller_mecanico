package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.partdto.PartDTO;
import org.ayd.apimecahnicalworkshop.entities.InventaryMovment;
import org.ayd.apimecahnicalworkshop.entities.Part;
import org.ayd.apimecahnicalworkshop.entities.Supplier;
import org.ayd.apimecahnicalworkshop.repositories.InventoryMovmentRepository;
import org.ayd.apimecahnicalworkshop.repositories.PartRepository;
import org.ayd.apimecahnicalworkshop.repositories.SupplierRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.PartConvert;
import org.ayd.apimecahnicalworkshop.utils.TypeMovment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {
    @Autowired
    private PartRepository repository;
    @Autowired
    private InventoryMovmentRepository inventoryMovmentRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PartConvert convert;

    /**
     * List of parts
     *
     * @return List<Part>
     */
    public List<Part> findAll() {
        return (List<Part>) repository.findAll();
    }

    /**
     * Save part
     *
     * @param partDTO - PartDTO
     * @return PartDTO
     */
    public PartDTO save(PartDTO partDTO) {
        System.out.println(" id del proveedor: ");
        System.out.println(partDTO.getSupplierId());
        Part aux = repository.findPartByNamePartIgnoreCaseAndSupplierId(partDTO.getNamePart(), partDTO.getSupplierId());
        if (aux != null) {
            throw new ErrorApi(402,
                    "El repuesto ya existe a nombre del proveedor: "
                            + partDTO.getSupplierId()
                            + ", le sugerimos registrar el repuesto en el inventario."
            );
        }

        if (partDTO.getStockPart() < 1) {
            throw new ErrorApi(402,
                    "La cantidad de repuestos debe ser mayor a 1"
            );
        }
        // register the new part
        Part partSave = new Part();
        partSave.setSupplierId(partDTO.getSupplierId());
        partSave.setNamePart(partDTO.getNamePart());
        partSave.setBrandPart(partDTO.getBrandPart());
        partSave.setDescriptionPart(partDTO.getDescriptionPart());
        partSave.setCostPrice(partDTO.getCostPrice());
        partSave.setSalePrice(partDTO.getSalePrice());
        partSave.setStockPart(partDTO.getStockPart());
        partSave.setActive(partDTO.getActive());
        repository.save(partSave);

        //register the inventory movment
        InventaryMovment movment = new InventaryMovment();
        movment.setPartId(partSave.getPartId());
        movment.setCreatedBy(null);
        movment.setTypeMovement(TypeMovment.ENTRY.getTypeMovment());
        movment.setReference(
                "Se ha registrado el nuevo repuesto " + partDTO.getNamePart()
                        + " con una cantidad de: " + partDTO.getStockPart()
        );
        movment.setQuantity(partDTO.getStockPart());
        inventoryMovmentRepository.save(movment);

        return convert.mapToDTO(partSave);
    }

    /**
     * Update part,
     * if addStock is true, add stock to the part
     * if addStock is false, reduce stock from the part
     *
     * @param partDTO  - PartDTO
     * @param addStock - boolean
     * @return PartDTO
     */
    public PartDTO update(PartDTO partDTO, boolean addStock) {
        Part part = repository.findById(partDTO.getPartId()).orElseThrow(() -> new ErrorApi(404, "El repuesto no existe"));
        if (addStock) {
            part.setStockPart(part.getStockPart() + partDTO.getStockPart());
            InventaryMovment movment = new InventaryMovment();
            movment.setPartId(part.getPartId());
            movment.setCreatedBy(null);
            movment.setTypeMovement(TypeMovment.ENTRY.getTypeMovment());
            movment.setReference(
                    "Se ha agregado al repuesto " + partDTO.getNamePart()
                            + " la cantidad de: " + partDTO.getStockPart()
                            + " en el inventario."
            );
            movment.setQuantity(partDTO.getStockPart());
            inventoryMovmentRepository.save(movment);
        } else {
            if (!(part.getStockPart() >= partDTO.getStockPart())) {
                throw new ErrorApi(402, "No existe suficiente cantidad de repuestos");
            }
            part.setStockPart(part.getStockPart() - partDTO.getStockPart());
            InventaryMovment movment = new InventaryMovment();
            movment.setPartId(part.getPartId());
            movment.setCreatedBy(null);
            movment.setTypeMovement(TypeMovment.ENTRY.getTypeMovment());
            movment.setReference(
                    "Se reducido la cantidad al repuesto " + partDTO.getNamePart()
                            + " la cantidad de: " + partDTO.getStockPart()
                            + " en el inventario."
            );
            movment.setQuantity(partDTO.getStockPart());
            inventoryMovmentRepository.save(movment);
        }
        part.setSupplierId(partDTO.getSupplierId());
        part.setNamePart(partDTO.getNamePart());
        part.setBrandPart(partDTO.getBrandPart());
        part.setDescriptionPart(partDTO.getDescriptionPart());
        part.setCostPrice(partDTO.getCostPrice());
        part.setSalePrice(partDTO.getSalePrice());
        part.setActive(partDTO.getActive());
        repository.save(part);
        return convert.mapToDTO(part);
    }

    public List<PartDTO> getPartByActive(Boolean active) {
        return repository.getPartByActive(active).stream().map(convert::mapToDTO).toList();
    }
}
