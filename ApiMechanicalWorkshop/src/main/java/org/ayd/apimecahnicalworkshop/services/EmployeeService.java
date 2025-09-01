package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.userdto.UserResponseDTO;
import org.ayd.apimecahnicalworkshop.dto.employee.EmployeeJobDTO;
import org.ayd.apimecahnicalworkshop.entities.*;
import org.ayd.apimecahnicalworkshop.repositories.*;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobAssigmentRepository jobAssigmentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private StatusJobRepository statusJobRepository;

    @Autowired
    private TypeJobRepository typeJobRepository;

    @Autowired
    private UpdateJobsRepository updateJobRepository;

    private void verificarAsignacionEmpleado(Long trabajoId, Long empleadoId) {
        List<JobAssigment> asignaciones = jobAssigmentRepository.findByJobId(trabajoId);
        boolean asignadoAlEmpleado = asignaciones.stream()
                .anyMatch(a -> a.getUserId().equals(empleadoId) && a.getUnassignedAt() == null);

        if (!asignadoAlEmpleado) {
            throw new ErrorApi(403, "El trabajo no está asignado a este empleado");
        }
    }

    /**
     * Obtener empleado por ID y validar que sea empleado activo
     */
    public UserResponseDTO obtenerEmpleadoPorId(Long empleadoId) {
        Optional<User> userOpt = userRepository.findById(empleadoId);

        if (userOpt.isEmpty()) {
            throw new ErrorApi(404, "Empleado no encontrado");
        }

        User user = userOpt.get();

        if (!user.getRolId().equals(Roles.EMPLOYEE.getId())) {
            throw new ErrorApi(403, "El usuario no tiene rol de empleado");
        }

        if (!user.isActive()) {
            throw new ErrorApi(403, "El empleado no está activo");
        }

        return mapUserToResponseDTO(user);
    }

    /**
     * Obtener trabajos asignados a un empleado específico
     */
    public List<EmployeeJobDTO> obtenerTrabajosAsignados(Long empleadoId) {
        obtenerEmpleadoPorId(empleadoId);
        List<JobAssigment> asignaciones = jobAssigmentRepository.findByUserIdAndUnassignedAtIsNull(empleadoId);
        return asignaciones.stream()
                .map(this::mapJobAssignmentToTrabajoDTO)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    /**
     * Mapear JobAssigment a TrabajoEmpleadoDTO con todos los datos necesarios
     */
    private EmployeeJobDTO mapJobAssignmentToTrabajoDTO(JobAssigment asignacion) {
        Optional<Job> jobOpt = jobRepository.findById(asignacion.getJobId());
        if (jobOpt.isEmpty()) {
            return null;
        }

        Job job = jobOpt.get();
        EmployeeJobDTO dto = new EmployeeJobDTO();

        dto.setTrabajoId(job.getJobId());
        dto.setVehiculoId(job.getVehicleId());
        dto.setDescripcion(job.getDescription());
        dto.setHorasEstimadas(job.getEstimatedHours());
        dto.setFechaAsignacion(asignacion.getAssignedAt());
        dto.setFechaInicio(job.getStartedAt());
        dto.setFechaFinalizacion(job.getFinishedAt());

        Optional<TypeJob> typeJobOpt = typeJobRepository.findById(job.getTypeJobId());
        if (typeJobOpt.isPresent()) {
            dto.setTipoMantenimiento(typeJobOpt.get().getNameTypeJob());
        } else {
            dto.setTipoMantenimiento("No especificado");
        }

        Optional<StatusJob> statusJobOpt = statusJobRepository.findById(job.getStatusJobId());
        if (statusJobOpt.isPresent()) {
            dto.setEstadoTrabajo(statusJobOpt.get().getNameStatusJob());
        } else {
            dto.setEstadoTrabajo("Desconocido");
        }

        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(job.getVehicleId());
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            dto.setPlacaVehiculo(vehicle.getLicencePlate());
            dto.setMarcaVehiculo(vehicle.getBrand());
            dto.setModeloVehiculo(vehicle.getModel());

            if (vehicle.getClient() != null) {
                dto.setClienteNombre("Cliente ID: " + vehicle.getClient().getClientId());
            } else {
                dto.setClienteNombre("Sin cliente asignado");
            }
        }

        return dto;
    }

    /**
     * Mapear User a UserResponseDTO
     */
    private UserResponseDTO mapUserToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getRolId(),
                user.getUsername(),
                user.isActive(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getLastName(),
                user.isTwoFactorAuth(),
                user.getTypeTwoFactorId()
        );
    }

    /**
     * Iniciar trabajo - Cambiar estado y registrar inicio
     */
    public void iniciarTrabajo(Long trabajoId, Long empleadoId) {
        obtenerEmpleadoPorId(empleadoId);
        Optional<Job> jobOpt = jobRepository.findById(trabajoId);
        if (jobOpt.isEmpty()) {
            throw new ErrorApi(404, "Trabajo no encontrado");
        }
        Job trabajo = jobOpt.get();
        verificarAsignacionEmpleado(trabajoId, empleadoId);
        if (!trabajo.getStatusJobId().equals(1L)) {
            throw new ErrorApi(400, "El trabajo no está en estado pendiente");
        }
        trabajo.setStartedAt(LocalDateTime.now().toString());
        trabajo.setStatusJobId(2L); // En progreso
        trabajo.setUpdatedAt(LocalDateTime.now().toString());
        jobRepository.save(trabajo);
        UpdateJob update = new UpdateJob();
        update.setJobId(trabajoId);
        update.setCreatedBy(empleadoId);
        update.setStatusUpdateJobId(1L);
        update.setNotes("Trabajo iniciado por el empleado");
        update.setHoursSpent(0.0);
        update.setCreatedAt(LocalDateTime.now().toString());
        update.setUpdatedAt(LocalDateTime.now().toString());
        updateJobRepository.save(update);
    }

    /**
     * Finalizar trabajo - Cambiar estado a completado
     */
    public void finalizarTrabajo(Long trabajoId, Long empleadoId, String observaciones) {

        obtenerEmpleadoPorId(empleadoId);

        Optional<Job> jobOpt = jobRepository.findById(trabajoId);
        if (jobOpt.isEmpty()) {
            throw new ErrorApi(404, "Trabajo no encontrado");
        }

        Job trabajo = jobOpt.get();

        List<JobAssigment> asignaciones = jobAssigmentRepository.findByJobId(trabajoId);
        boolean asignadoAlEmpleado = asignaciones.stream()
                .anyMatch(a -> a.getUserId().equals(empleadoId) && a.getUnassignedAt() == null);

        if (!asignadoAlEmpleado) {
            throw new ErrorApi(403, "El trabajo no está asignado a este empleado");
        }

        trabajo.setFinishedAt(java.time.LocalDateTime.now().toString());
        trabajo.setStatusJobId(3L);
        trabajo.setUpdatedAt(java.time.LocalDateTime.now().toString());
        jobRepository.save(trabajo);
    }

    /**
     * Obtener detalles específicos de un trabajo para el empleado
     */
    public EmployeeJobDTO obtenerDetallesTrabajo(Long trabajoId, Long empleadoId) {
        obtenerEmpleadoPorId(empleadoId);
        List<JobAssigment> asignaciones = jobAssigmentRepository.findByJobId(trabajoId);
        boolean asignadoAlEmpleado = asignaciones.stream()
                .anyMatch(a -> a.getUserId().equals(empleadoId) && a.getUnassignedAt() == null);

        if (!asignadoAlEmpleado) {
            throw new ErrorApi(403, "El trabajo no está asignado a este empleado");
        }

        JobAssigment tempAssignment = new JobAssigment();
        tempAssignment.setJobId(trabajoId);
        tempAssignment.setUserId(empleadoId);
        tempAssignment.setAssignedAt(LocalDateTime.now().toString());

        return mapJobAssignmentToTrabajoDTO(tempAssignment);
    }

    /**
     * Obtener historial de progreso usando updates_jobs
     */
    public List<UpdateJob> obtenerHistorialProgreso(Long trabajoId, Long empleadoId) {
        verificarAsignacionEmpleado(trabajoId, empleadoId);
        return updateJobRepository.findByJobIdOrderByCreatedAtDesc(trabajoId);
    }


    /**
     * Registrar avance en un trabajo usando updates_jobs
     */
    public void registrarAvance(Long trabajoId, Long empleadoId, String descripcion,
                                Double horasTrabajadas, String tipo) {
        obtenerEmpleadoPorId(empleadoId);
        verificarAsignacionEmpleado(trabajoId, empleadoId);

        UpdateJob update = new UpdateJob();
        update.setJobId(trabajoId);
        update.setCreatedBy(empleadoId);
        update.setStatusUpdateJobId(obtenerStatusUpdateJobId(tipo));
        update.setNotes(descripcion);
        update.setHoursSpent(horasTrabajadas);
        update.setCreatedAt(LocalDateTime.now().toString());
        update.setUpdatedAt(LocalDateTime.now().toString());

        updateJobRepository.save(update);
    }

    private Long obtenerStatusUpdateJobId(String tipo) {
        switch (tipo.toUpperCase()) {
            case "AVANCE":
            case "REPARACION":
                return 3L;  // AVANCE
            case "DIAGNOSTICO":
                return 8L; // DIAGNOSTICO
            case "PRUEBA":
                return 9L; // PRUEBA
            default:
                return 3L; //AVANCE
        }
    }

    /**
     * Solicitar apoyo de especialista
     */

    public void solicitarApoyo(Long trabajoId, Long empleadoId, String motivo, String tipoEspecialista) {
        obtenerEmpleadoPorId(empleadoId);
        verificarAsignacionEmpleado(trabajoId, empleadoId);
        String descripcion = String.format("Solicitud de apoyo - %s: %s", tipoEspecialista, motivo);
        UpdateJob update = new UpdateJob();
        update.setJobId(trabajoId);
        update.setCreatedBy(empleadoId);
        update.setStatusUpdateJobId(4L); // SOLICITUD_APOYO
        update.setNotes(descripcion);
        update.setHoursSpent(null);
        update.setCreatedAt(LocalDateTime.now().toString());
        update.setUpdatedAt(LocalDateTime.now().toString());

        updateJobRepository.save(update);
    }

    @Transactional
    public Map<String, Object> solicitarApoyoYAsignar(
            Long trabajoId,
            Long empleadoId,
            String motivo,
            String tipoEspecialista,
            Long especialistaId
    ) {
        obtenerEmpleadoPorId(empleadoId);
        verificarAsignacionEmpleado(trabajoId, empleadoId);
        solicitarApoyo(trabajoId, empleadoId, motivo, tipoEspecialista);
        User especialista;
        if (especialistaId != null) {
            especialista = userRepository.findById(especialistaId)
                    .orElseThrow(() -> new ErrorApi(404, "Especialista no encontrado"));
            if (!especialista.isActive()) {
                throw new ErrorApi(403, "Especialista inactivo");
            }
        } else {
            especialista = userRepository
                    .findFirstByRolIdAndIsActive(/* Roles.SPECIALIST.getId() */ 3L, true)
                    .orElseThrow(() -> new ErrorApi(409, "No hay especialistas disponibles"));
        }

        boolean yaAsignado = jobAssigmentRepository
                .existsByJobIdAndUserIdAndUnassignedAtIsNull(trabajoId, especialista.getUserId());
        if (yaAsignado) {
            return Map.of(
                    "trabajoId", trabajoId,
                    "especialistaId", especialista.getUserId(),
                    "especialistaNombre", (especialista.getName() + " " + especialista.getLastName()).trim(),
                    "mensaje", "Ya estaba asignado como especialista"
            );
        }

        JobAssigment asign = new JobAssigment();
        asign.setJobId(trabajoId);
        asign.setUserId(especialista.getUserId());
        asign.setRoleAssignment(JobAssigment.RoleAssignment.especialista);
        asign.setNotes("Asignado por solicitud de apoyo - " + tipoEspecialista + ": " + motivo);
        asign.setAssignedAt(LocalDateTime.now().toString());
        jobAssigmentRepository.save(asign);

        UpdateJob upd = new UpdateJob();
        upd.setJobId(trabajoId);
        upd.setCreatedBy(empleadoId);
        upd.setStatusUpdateJobId(3L);
        upd.setNotes("Especialista asignado: " + especialista.getName() + " " + especialista.getLastName());
        upd.setHoursSpent(null);
        upd.setCreatedAt(LocalDateTime.now().toString());
        upd.setUpdatedAt(LocalDateTime.now().toString());
        updateJobRepository.save(upd);

        return Map.of(
                "trabajoId", trabajoId,
                "especialistaId", especialista.getUserId(),
                "especialistaNombre", (especialista.getName() + " " + especialista.getLastName()).trim()
        );
    }


    /**
     * Reportar da;os adicionales
     */
    public void reportarDanos(Long trabajoId, Long empleadoId, String descripcionDanos, String severidad) {
        obtenerEmpleadoPorId(empleadoId);
        verificarAsignacionEmpleado(trabajoId, empleadoId);

        String descripcion = String.format("DA;OS REPORTADOS [%s]: %s", severidad, descripcionDanos);

        UpdateJob update = new UpdateJob();
        update.setJobId(trabajoId);
        update.setCreatedBy(empleadoId);
        update.setStatusUpdateJobId(5L); // DANOS_REPORTADOS
        update.setNotes(descripcion);
        update.setHoursSpent(null);
        update.setCreatedAt(LocalDateTime.now().toString());
        update.setUpdatedAt(LocalDateTime.now().toString());

        updateJobRepository.save(update);
    }

    /**
     * Marcar uso de repuesto
     */
    public void marcarUsoRepuesto(Long trabajoId, Long empleadoId, Long repuestoId,
                                  Integer cantidad, String observaciones) {
        obtenerEmpleadoPorId(empleadoId);
        verificarAsignacionEmpleado(trabajoId, empleadoId);

        String descripcion = String.format("Repuesto usado - ID: %d, Cantidad: %d. %s",
                repuestoId, cantidad, observaciones != null ? observaciones : "");

        UpdateJob update = new UpdateJob();
        update.setJobId(trabajoId);
        update.setCreatedBy(empleadoId);
        update.setStatusUpdateJobId(6L); // USO_REPUESTO
        update.setNotes(descripcion);
        update.setHoursSpent(null);
        update.setCreatedAt(LocalDateTime.now().toString());
        update.setUpdatedAt(LocalDateTime.now().toString());

        updateJobRepository.save(update);
    }

}