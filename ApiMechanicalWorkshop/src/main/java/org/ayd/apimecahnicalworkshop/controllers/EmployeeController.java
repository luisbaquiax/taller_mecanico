package org.ayd.apimecahnicalworkshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.ayd.apimecahnicalworkshop.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@CrossOrigin (origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> getEmployeeDashboard() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to employe dashboard");
        response.put("status", "success");
        response.put("rol", "EMPLEADO");
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener trabajos asignados a un empleado específico
     */
    @GetMapping("/jobs/{empleadoId}")
    public ResponseEntity<Map<String, Object>> getTrabajosAsignados(@PathVariable Long empleadoId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var trabajos = employeeService.obtenerTrabajosAsignados(empleadoId);

            response.put("mensaje", "Trabajos asignados obtenidos correctamente");
            response.put("empleadoId", empleadoId);
            response.put("trabajos", trabajos);
            response.put("total", trabajos.size());
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al obtener trabajos asignados");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Iniciar trabajo
     */
    @PutMapping("/job/{trabajoId}/iniciar")
    public ResponseEntity<Map<String, Object>> iniciarTrabajo(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            employeeService.iniciarTrabajo(trabajoId, empleadoId);

            response.put("mensaje", "Trabajo iniciado correctamente");
            response.put("trabajoId", trabajoId);
            response.put("empleadoId", empleadoId);
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al iniciar trabajo");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Finalizar trabajo
     */
    @PutMapping("/job/{trabajoId}/finalizar")
    public ResponseEntity<Map<String, Object>> finalizarTrabajo(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            String observaciones = (String) requestBody.get("observaciones");

            employeeService.finalizarTrabajo(trabajoId, empleadoId, observaciones);

            response.put("mensaje", "Trabajo finalizado correctamente");
            response.put("trabajoId", trabajoId);
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al finalizar trabajo");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getEmployeeProfile() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to employe profile");
        response.put("name", "Test employee");
        response.put("role", "EMPLEADO");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification")
    public ResponseEntity<Map<String, Object>> getEmployeeVerification() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Session employe verification");
        response.put("role", "EMPLEADO");
        response.put("status", "Authenticated");
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener detalles específicos de un trabajo
     */
    @GetMapping("/job/{trabajoId}/details/{empleadoId}")
    public ResponseEntity<Map<String, Object>> getDetallesTrabajo(
            @PathVariable Long trabajoId,
            @PathVariable Long empleadoId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var trabajo = employeeService.obtenerDetallesTrabajo(trabajoId, empleadoId);

            response.put("mensaje", "Detalles del trabajo obtenidos correctamente");
            response.put("trabajo", trabajo);
            response.put("historialProgreso", new java.util.ArrayList<>()); // Vacío por ahora
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al obtener detalles del trabajo");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Registrar avance en un trabajo
     */
    @PostMapping("/job/{trabajoId}/avance")
    public ResponseEntity<Map<String, Object>> registrarAvance(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            String descripcion = (String) requestBody.get("descripcion");
            Double horasTrabajadas = requestBody.get("horasTrabajadas") != null ?
                    Double.valueOf(requestBody.get("horasTrabajadas").toString()) : null;
            String tipo = (String) requestBody.getOrDefault("tipo", "AVANCE");

            employeeService.registrarAvance(trabajoId, empleadoId, descripcion, horasTrabajadas, tipo);

            response.put("mensaje", "Avance registrado correctamente");
            response.put("trabajoId", trabajoId);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al registrar avance");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Historial del trabajo especifico
     */
    @GetMapping("/job/{trabajoId}/historial")
    public ResponseEntity<?> obtenerHistorial(
            @PathVariable Long trabajoId,
            @RequestParam Long empleadoId,
            @RequestParam(name = "limit", required = false, defaultValue = "100") int limit) {
        var historial = employeeService.obtenerHistorialProgreso(trabajoId, empleadoId);
        if (limit > 0 && historial.size() > limit) {
            historial = historial.subList(0, limit);
        }
        return ResponseEntity.ok(historial);
    }


    /**
     * Solicitar apoyo de especialista
     */
    @PostMapping("/job/{trabajoId}/solicitar-apoyo")
    public ResponseEntity<Map<String, Object>> solicitarApoyo(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            String motivo = (String) requestBody.get("motivo");
            String tipoEspecialista = (String) requestBody.get("tipoEspecialista");
            Long especialistaId = requestBody.get("especialistaId") != null
                    ? Long.valueOf(requestBody.get("especialistaId").toString())
                    : null;

            var asignacion = employeeService.solicitarApoyoYAsignar(
                    trabajoId, empleadoId, motivo, tipoEspecialista, especialistaId);

            response.put("mensaje", "Solicitud registrada y especialista asignado");
            response.put("status", "success");
            response.putAll(asignacion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al solicitar apoyo");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Reportar daños adicionales
     */
    @PostMapping("/job/{trabajoId}/reportar-danos")
    public ResponseEntity<Map<String, Object>> reportarDanos(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            String descripcionDanos = (String) requestBody.get("descripcionDanos");
            String severidad = (String) requestBody.getOrDefault("severidad", "MEDIO");

            employeeService.reportarDanos(trabajoId, empleadoId, descripcionDanos, severidad);

            response.put("mensaje", "Daños reportados correctamente");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al reportar daños");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Marcar uso de repuesto
     */
    @PostMapping("/job/{trabajoId}/usar-repuesto")
    public ResponseEntity<Map<String, Object>> marcarUsoRepuesto(
            @PathVariable Long trabajoId,
            @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long empleadoId = Long.valueOf(requestBody.get("empleadoId").toString());
            Long repuestoId = Long.valueOf(requestBody.get("repuestoId").toString());
            Integer cantidad = Integer.valueOf(requestBody.get("cantidad").toString());
            String observaciones = (String) requestBody.get("observaciones");

            employeeService.marcarUsoRepuesto(trabajoId, empleadoId, repuestoId, cantidad, observaciones);

            response.put("mensaje", "Uso de repuesto registrado correctamente");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al registrar uso de repuesto");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

}

