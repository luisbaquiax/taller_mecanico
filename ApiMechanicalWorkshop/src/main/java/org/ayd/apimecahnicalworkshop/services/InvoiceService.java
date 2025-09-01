package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.InvoicesPerClientDTO;
import org.ayd.apimecahnicalworkshop.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;


    /**
     * Obtiene todas las facturas del usuario asociadas con sus vehículos
     * @param userId ID del usuario
     * @return Lista de facturas con información del vehículo
     */
    public List<InvoicesPerClientDTO> obtenerFacturasPorUsuario(Long userId) {
        List<Object[]> results = invoiceRepository.findInvoiceByClientId(userId);
        return results.stream().map(this::mapToFacturaPorUsuarioDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene facturas por usuario filtrado por estado
     * @param userId ID del usuario
     * @param status Estado de la factura (pendiente, pagada, anulada)
     * @return Lista de facturas filtradas por estado
     */
    public List<InvoicesPerClientDTO> obtenerFacturasPorUsuarioYEstado(Long userId, String status) {
        List<Object[]> results = invoiceRepository.findInvoiceByStatus(userId, status);
        return results.stream().map(this::mapToFacturaPorUsuarioDTO).collect(Collectors.toList());
    }

    private InvoicesPerClientDTO mapToFacturaPorUsuarioDTO(Object[] result) {
        InvoicesPerClientDTO dto = new InvoicesPerClientDTO();
        dto.setInvoiceId(Long.valueOf((Integer)(result[0])));
        dto.setJobId(Long.valueOf((Integer)(result[1])));
        dto.setLicencePlate((String)(result[2]));
        dto.setVehicleBrand((String)(result[3]));
        dto.setVehicleModel((String)(result[4]));
        dto.setVehicleYear((String)(result[5]));
        dto.setTotal((Double) (result[6]));
        dto.setInvoiceStatus((String)(result[7]));
        dto.setIssuedAt(((Timestamp) (result[8])).toLocalDateTime());
        dto.setJobDescription((String)(result[9]));
        return dto;
    }

}
