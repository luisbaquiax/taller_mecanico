package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.PartsPerVehicleDTO;
import org.ayd.apimecahnicalworkshop.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PartService {

    @Autowired
    private PartRepository partRepository;

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
