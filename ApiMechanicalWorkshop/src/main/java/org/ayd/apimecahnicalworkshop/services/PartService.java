package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.PartsPerVehicleDTO;
import org.ayd.apimecahnicalworkshop.dto.partdto.PartDTO;
import org.ayd.apimecahnicalworkshop.entities.InventaryMovment;
import org.ayd.apimecahnicalworkshop.entities.Part;
import org.ayd.apimecahnicalworkshop.repositories.InventoryMovmentRepository;
import org.ayd.apimecahnicalworkshop.repositories.PartRepository;
import org.ayd.apimecahnicalworkshop.repositories.SupplierRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.PartConvert;
import org.ayd.apimecahnicalworkshop.utils.TypeMovment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PartService {
    @Autowired
    private PartRepository partRepository;
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
        return (List<Part>) partRepository.findAll();
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
        Part aux = partRepository.findPartByNamePartIgnoreCaseAndSupplierId(partDTO.getNamePart(), partDTO.getSupplierId());
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
        partRepository.save(partSave);

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
        Part part = partRepository.findById(partDTO.getPartId()).orElseThrow(() -> new ErrorApi(404, "El repuesto no existe"));
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
        partRepository.save(part);
        return convert.mapToDTO(part);
    }

    public List<PartDTO> getPartByActive(Boolean active) {
        return partRepository.getPartByActive(active).stream().map(convert::mapToDTO).toList();
    }

    public PartDTO findPartByNamePartIgnoreCaseAndSupplierId(String namePart, Long supplierId) {
        return convert.mapToDTO(partRepository.findPartByNamePartIgnoreCaseAndSupplierId(namePart, supplierId));
    }

    /**
     * Obtiene todos los repuestos comprados por un usuario según los vehículos reparados
     * @param userId ID del usuario
     * @return Lista de repuestos con información del vehículo
     */
    public List<PartsPerVehicleDTO> obtenerRepuestosPorUsuario(Long userId) {
        List<Object[]> results = partRepository.findPartByUserID(userId);
        return results.stream().map(this::mapToPartPerVehicle).collect(Collectors.toList());
    }

    /**
     * Obtiene repuestos comprados por usuario filtrado por vehículo específico
     * @param userId ID del usuario
     * @param vehicleId ID del vehículo
     * @return Lista de repuestos para el vehículo específico
     */
    public List<PartsPerVehicleDTO> obtenerRepuestosPorUsuarioYVehiculo(Long userId, Long vehicleId) {
        List<Object[]> results = partRepository.findPartByVehicleID(userId, vehicleId);
        return results.stream().map(this::mapToPartPerVehicle).collect(Collectors.toList());
    }

    private PartsPerVehicleDTO mapToPartPerVehicle(Object[] result) {
        PartsPerVehicleDTO dto = new PartsPerVehicleDTO();
        dto.setJobId(Long.valueOf((Integer) (result[0])));
        dto.setLicencePlate((String)(result[1]));
        dto.setVehicleBrand((String)(result[2]));
        dto.setVehicleModel((String)(result[3]));
        dto.setVehicleYear((String)(result[4]));
        dto.setPartName((String)(result[5]));
        dto.setPartBrand((String)(result[6]));
        dto.setQuantity((Integer) (result[7]));
        dto.setPrice((BigDecimal) (result[8]));
        dto.setCreatedAt(((Timestamp)(result[9])).toLocalDateTime());
        dto.setJobDescription((String)(result[10]));
        return dto;
    }

}
