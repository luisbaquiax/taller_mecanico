package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.InvoicesPerClientDTO;
import org.ayd.apimecahnicalworkshop.dto.PartsPerVehicleDTO;
import org.ayd.apimecahnicalworkshop.dto.QuotationDetailDTO;
import org.ayd.apimecahnicalworkshop.services.InvoiceService;
import org.ayd.apimecahnicalworkshop.services.PartService;
import org.ayd.apimecahnicalworkshop.services.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/")
@CrossOrigin(origins = "*")
public class ClientRepController {

    @Autowired
    private PartService partService;
    @Autowired
    private QuotationService quotationService;
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Obtiene todos los repuestos comprados por un usuario según los vehículos reparados
     */
    @GetMapping("/{userId}/part")
    public ResponseEntity<List<PartsPerVehicleDTO>> obtenerRepuestosPorUsuario(@PathVariable Long userId) {
        try {
            List<PartsPerVehicleDTO> repuestos = partService.obtenerRepuestosPorUsuario(userId);
            return ResponseEntity.ok(repuestos);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene repuestos comprados por usuario filtrado por vehículo específico
     */
    @GetMapping("/{userId}/part/vehicle/{vehicleId}")
    public ResponseEntity<List<PartsPerVehicleDTO>> obtenerRepuestosPorUsuarioYVehiculo(
            @PathVariable Long userId,
            @PathVariable Long vehicleId) {
        try {
            List<PartsPerVehicleDTO> repuestos = partService.obtenerRepuestosPorUsuarioYVehiculo(userId, vehicleId);
            return ResponseEntity.ok(repuestos);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene todas las facturas del usuario asociadas con sus vehículos
     */
    @GetMapping("/{userId}/invoices")
    public ResponseEntity<List<InvoicesPerClientDTO>> obtenerFacturasPorUsuario(@PathVariable Long userId) {
        try {
            List<InvoicesPerClientDTO> facturas = invoiceService.obtenerFacturasPorUsuario(userId);
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene facturas por usuario filtrado por estado
     */
    @GetMapping("/{userId}/invoices/state/{status}")
    public ResponseEntity<List<InvoicesPerClientDTO>> obtenerFacturasPorUsuarioYEstado(
            @PathVariable Long userId,
            @PathVariable String status) {
        try {
            List<InvoicesPerClientDTO> facturas = invoiceService.obtenerFacturasPorUsuarioYEstado(userId, status);
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene todas las cotizaciones a nombre del usuario
     */
    @GetMapping("/{userId}/quotations")
    public ResponseEntity<List<QuotationDetailDTO>> obtenerCotizacionesPorUsuario(@PathVariable Long userId) {
        try {
            List<QuotationDetailDTO> cotizaciones = quotationService.obtenerCotizacionesPorUsuario(userId);
            return ResponseEntity.ok(cotizaciones);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene cotizaciones por usuario filtrado por estado
     */
    @GetMapping("/{userId}/quotations/state/{status}")
    public ResponseEntity<List<QuotationDetailDTO>> obtenerCotizacionesPorUsuarioYEstado(
            @PathVariable Long userId,
            @PathVariable String status) {
        try {
            List<QuotationDetailDTO> cotizaciones = quotationService.obtenerCotizacionesPorUsuarioYEstado(userId, status);
            return ResponseEntity.ok(cotizaciones);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene cotizaciones vigentes (que no han expirado) por usuario
     */
    @GetMapping("/{userId}/quotations/vigentes")
    public ResponseEntity<List<QuotationDetailDTO>> obtenerCotizacionesVigentesPorUsuario(@PathVariable Long userId) {
        try {
            List<QuotationDetailDTO> cotizaciones =  quotationService.obtenerCotizacionesVigentesPorUsuario(userId);
            return ResponseEntity.ok(cotizaciones);
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause()+"\n"+e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
    }

}