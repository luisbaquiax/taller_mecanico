package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.QuotationDetailDTO;
import org.ayd.apimecahnicalworkshop.repositories.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    /**
     * Obtiene todas las cotizaciones a nombre del usuario
     * @param userId ID del usuario
     * @return Lista de cotizaciones con información del vehículo
     */
    public List<QuotationDetailDTO> obtenerCotizacionesPorUsuario(Long userId) {
        List<Object[]> results = quotationRepository.findQuotationByClientId(userId);
        return results.stream().map(this::mapToQuotationDetail).collect(Collectors.toList());
    }

    /**
     * Obtiene cotizaciones por usuario filtrado por estado
     * @param userId ID del usuario
     * @param status Estado de la cotización (pendiente, aceptada, rechazada)
     * @return Lista de cotizaciones filtradas por estado
     */
    public List<QuotationDetailDTO> obtenerCotizacionesPorUsuarioYEstado(Long userId, String status) {
        List<Object[]> results = quotationRepository.findBystate(userId, status);
        return results.stream().map(this::mapToQuotationDetail).collect(Collectors.toList());
    }

    /**
     * Obtiene cotizaciones vigentes (que no han expirado) por usuario
     * @param userId ID del usuario
     * @return Lista de cotizaciones vigentes
     */
    public List<QuotationDetailDTO> obtenerCotizacionesVigentesPorUsuario(Long userId) {
        List<Object[]> results = quotationRepository.findQuotationsByValid(userId);
        return results.stream().map(this::mapToQuotationDetail).collect(Collectors.toList());
    }


    private QuotationDetailDTO mapToQuotationDetail(Object[] result) {
        QuotationDetailDTO dto = new QuotationDetailDTO();
        dto.setQuotationId(Long.valueOf((Integer) (result[0])));
        dto.setJobId(Long.valueOf((Integer) (result[1])));
        dto.setLicencePlate((String) (result[2]));
        dto.setVehicleBrand((String) (result[3]));
        dto.setVehicleModel((String) (result[4]));
        dto.setVehicleYear((String) (result[5]));
        dto.setTotal((Double)(result[6]));
        dto.setQuotationStatus((String) (result[7]));
        dto.setCreatedAt(((Timestamp)(result[8])).toLocalDateTime());
        dto.setValidUntil(((Timestamp)(result[9])).toLocalDateTime());
        dto.setJobDescription((String) (result[10]));
        return dto;
    }


}
