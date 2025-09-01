-- Insert roles
INSERT INTO rols_user (rol_id, name_rol, description_rol) VALUES
(1, 'ADMIN', 'Administrador con acceso total al sistema'),
(2, 'EMPLEADO', 'Empleado general encargado de operaciones básicas'),
(3, 'ESPECIALISTA', 'Técnico especializado en reparaciones complejas'),
(4, 'CLIENTE', 'Cliente del taller mecánico'),
(5, 'PROVEEDOR', 'Proveedor de servicio o repuestos');

-- Insert two-factor authentication types
INSERT INTO type_two_factor (name_type_two_factor, description_type_two_factor) VALUES
('SMS', 'Autenticación mediante código SMS'),
('Email', 'Autenticación mediante código enviado por correo electrónico');

-- Insert users (5 usuarios: 1 Admin, 1 Empleado, 1 Especialista, 2 Clientes)
INSERT INTO users (rol_id, username, password, is_active, email, phone, name, last_name, two_factor_auth, type_two_factor_id) VALUES
(1, 'admin1', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'admin@taller.com', '555-0101', 'Carlos', 'Perez', 1, 1),
(2, 'employee1', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'employee1@taller.com', '555-0102', 'Ana', 'Gomez', 0, NULL),
(3, 'specialist1', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'specialist1@taller.com', '555-0103', 'Luis', 'Martinez', 1, 2),
(4, 'client1', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'client1@gmail.com', '555-0104', 'Maria', 'Lopez', 0, NULL),
(4, 'client2', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'client2@gmail.com', '555-0105', 'Jose', 'Rodriguez', 0, NULL),
(5, 'supplier1', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'supplier1@gmail.com', '555-0104', 'Repuestos Mary', 'Repuestos Mary', 0, NULL),
(5, 'supplier2', '$2a$10$m1.I3Qm.FrKq3l.TxQI4nO3vJ3rLnNQt/TzPQd18XiGTywQYwn0Jm', 1, 'supplier2@gmail.com', '555-0105', 'Jose', 'Rodriguez', 0, NULL);

-- Insert password reset for one user
INSERT INTO password_reset (user_id, token, used) VALUES
(4, 'reset-token-12345', 0);

-- Insert clients (vinculados a usuarios con rol CLIENT)
INSERT INTO clients (user_id, nit, address) VALUES
(4, '1234567890123', 'Calle Principal 123, Ciudad'),
(5, '9876543210987', 'Avenida Robles 456, Ciudad');

-- Insert vehicles (vinculados a clientes)
INSERT INTO vehicles (client_id, licence_plate, brand, model, year, color) VALUES
(1, 'P123ABC', 'Toyota', 'Corolla', 2018, 'Azul'),
(1, 'H456DEF', 'Honda', 'Civic', 2020, 'Rojo'),
(2, 'F789GHI', 'Ford', 'Focus', 2019, 'Blanco');

-- Insert suppliers (usando un cliente como proveedor para simplicidad)
INSERT INTO suppliers (user_id, type_supplier) VALUES
(6, 'company'),
(7, 'person');

-- Insert service types
INSERT INTO services_types (name_service_type, description_service_type, base_price) VALUES
('Cambio de aceite', 'Cambio estándar de aceite de motor', 50.00),
('Reparación de frenos', 'Reemplazo e inspección de pastillas de freno', 120.00),
('Rotación de llantas', 'Rotación de llantas para desgaste uniforme', 30.00),
('Alineación de ruedas', 'Ajuste de ángulos de suspensión y dirección', 80.00),
('Diagnóstico de motor', 'Escaneo de códigos de error y análisis del rendimiento', 75.00),
('Revisión de suspensión', 'Inspección de amortiguadores, resortes y bujes', 65.00),
('Cambio de batería', 'Reemplazo de la batería del vehículo', 95.00),
('Revisión de aire acondicionado', 'Inspección y recarga del sistema de A/C', 110.00),
('Servicio de transmisión', 'Cambio de fluido y filtro de la transmisión', 150.00),
('Reparación de escape', 'Inspección y reparación de fugas o daños en el sistema de escape', 85.00),
('Inspección de 50 puntos', 'Revisión completa de 50 puntos clave del vehículo', 100.00),
('Lavado de motor', 'Limpieza y desengrase del compartimento del motor', 45.00),
('Reemplazo de bujías', 'Cambio de bujías para mejorar la combustión', 60.00),
('Limpieza de inyectores', 'Servicio de limpieza y prueba de inyectores de combustible', 90.00),
('Reemplazo de correa de distribución', 'Cambio de la correa de distribución y tensores', 250.00),
('Revisión de líquido de frenos', 'Inspección y reemplazo del líquido de frenos', 40.00),
('Mantenimiento de radiador', 'Limpieza y relleno del sistema de enfriamiento', 70.00),
('Servicio de faros', 'Restauración o reemplazo de faros empañados', 55.00);

-- Insert parts (vinculadas a proveedores)
INSERT INTO parts (supplier_id, name_part, brand_part, description_part, cost_price, sale_price, stock_part) VALUES
(1, 'Filtro de aceite', 'Bosch', 'Filtro de aceite de alta calidad', 10.00, 15.00, 50),
(1, 'Pastillas de freno', 'ACDelco', 'Pastillas de freno cerámicas', 40.00, 60.00, 20),
(2, 'Llanta', 'Michelin', 'Llanta para toda temporada', 80.00, 120.00, 10);

-- Insert inventory movements (vinculados a partes y creados por empleado)
INSERT INTO inventary_movements (part_id, created_by, type_movement, reference, quantity) VALUES
(1, 2, 'entrada', 'Orden de compra #001', 30),
(2, 2, 'salida', 'Trabajo #001', 4),
(3, 2, 'ajuste', 'Corrección de inventario', -2);

-- Insert job types
INSERT INTO type_jobs (name_type_job, description_type_job) VALUES
('Preventivo', 'Mantenimiento rutinario del vehículo'),
('Correctivo', 'Reparación de componentes de freno');

-- Insert job statuses
INSERT INTO status_jobs (name_status_job, description_status_job) VALUES
('pendiente', 'Trabajo programado pero no iniciado'),
('evaluacion', 'Trabajo en revisión antes de ejecución'),
('autorizado', 'Trabajo aprobado para ejecución'),
('en_curso', 'Trabajo en ejecución'),
('pausado', 'Trabajo detenido temporalmente'),
('finalizado', 'Trabajo finalizado con éxito'),
('cancelado', 'Trabajo cancelado antes de finalizar'),
('finalizado_sin_ejecucion', 'Trabajo finalizado sin haber sido ejecutado');

-- Insert jobs (vinculados a vehículos, creados por empleado, con tipo y estado)
INSERT INTO jobs (created_by, vehicle_id, started_at, finished_at, type_job_id, status_job_id, description, estimated_hours) VALUES
(1, 1, '2025-08-20 09:00:00', NULL, 1, 2, 'Realizar cambio de aceite y rotación de llantas', 2.50),
(1, 2, '2025-08-21 10:00:00', '2025-08-21 14:00:00', 2, 3, 'Reemplazar pastillas de freno', 3.00);

-- Insert job services (vinculados a trabajos y tipos de servicio)
INSERT INTO jobs_services (job_id, service_type_id, quantity, price) VALUES
(1, 1, 1, 50.00),
(1, 3, 1, 30.00),
(2, 2, 1, 120.00);

-- Insert job parts (vinculados a trabajos y partes)
INSERT INTO jobs_parts (job_id, part_id, quantity, price) VALUES
(1, 1, 1, 15.00),
(2, 2, 2, 60.00);

-- Insert status update jobs
INSERT INTO status_update_jobs (name_status_update_job, description_status_update_job) VALUES
('Iniciado', 'El trabajo ha comenzado'),
('Partes instaladas', 'Se han instalado las partes requeridas');

-- Insert updates jobs (vinculados a trabajos y actualizaciones de estado)
INSERT INTO updates_jobs (job_id, created_by, status_update_job_id, notes, hours_spent) VALUES
(1, 3, 1, 'Se inició el proceso de cambio de aceite', 1.00),
(2, 3, 2, 'Las pastillas de freno se instalaron correctamente', 2.00);

-- Insert job assignments (asignación de trabajos a empleado y especialista)
INSERT INTO jobs_assignments (job_id, user_id, role_assignment, notes) VALUES
(1, 2, 'empleado', 'Encargado del cambio de aceite'),
(1, 3, 'especialista', 'Verificar alineación de llantas'),
(2, 3, 'especialista', 'Liderar reparación de frenos');

-- Insert invoices (vinculadas a trabajos y clientes)
INSERT INTO invoices (job_id, client_id, total, invoince_status) VALUES
(1, 1, 95.00, 'pendiente'),
(2, 1, 240.00, 'pagada');

-- Insert payments (vinculados a facturas)
INSERT INTO payments (invoice_id, payment_method, amount, payment_date) VALUES
(2, 'tarjeta', 240.00, '2025-08-21 15:00:00');

-- Insert quotations (vinculadas a trabajos y clientes)
INSERT INTO quotations (job_id, client_id, total, quotation_status, valid_until) VALUES
(1, 1, 95.00, 'aceptada', '2025-09-01 23:59:59'),
(2, 1, 240.00, 'aceptada', '2025-09-01 23:59:59');

-- Insert quotation items (vinculados a cotizaciones)
INSERT INTO quotation_items (quotation_id, description, quantity, unit_price) VALUES
(1, 'Servicio de cambio de aceite', 1, 50.00),
(1, 'Servicio de rotación de llantas', 1, 30.00),
(1, 'Filtro de aceite', 1, 15.00),
(2, 'Servicio de reparación de frenos', 1, 120.00),
(2, 'Pastillas de freno', 2, 60.00);


-- Actualizaciones faltantes
INSERT INTO status_update_jobs (status_update_job_id, name_status_update_job, description_status_update_job) VALUES
(3, 'Avance', 'Registro de avance en el trabajo'),
(4, 'Solicitud Apoyo', 'Solicitud de apoyo de especialista'),
(5, 'Daños Reportados', 'Reporte de daños adicionales encontrados'),
(6, 'Uso Repuesto', 'Registro de uso de repuestos'),
(7, 'Pausa', 'Trabajo pausado temporalmente'),
(8, 'Diagnóstico', 'Diagnóstico realizado'),
(9, 'Prueba', 'Pruebas realizadas');

-- Cliente
INSERT INTO clients (user_id, nit, address) VALUES
(4, '5555555555555', 'Zona 10, Ciudad de Guatemala');

-- Nuevos vehiculos
INSERT INTO vehicles (client_id, licence_plate, brand, model, year, color) VALUES
(1, 'G111JKL', 'Nissan', 'Sentra', 2021, 'Gris'),
(1, 'B222MNO', 'Chevrolet', 'Aveo', 2017, 'Negro'),
(2, 'C333PQR', 'Hyundai', 'Elantra', 2022, 'Plata'),
(3, 'D444STU', 'Kia', 'Rio', 2019, 'Azul'),
(3, 'E555VWX', 'Mazda', 'CX-3', 2020, 'Rojo');

-- Mas jobs con diferentes estados (user_id = 2)
INSERT INTO jobs (created_by, vehicle_id, started_at, finished_at, type_job_id, status_job_id, description, estimated_hours) VALUES
-- Trabajos pendientes (estado 1)
(2, 3, NULL, NULL, 1, 1, 'Mantenimiento preventivo general - cambio de filtros', 1.50),
(2, 4, NULL, NULL, 2, 1, 'Revisión y reparación del sistema de frenos', 3.50),
(2, 5, NULL, NULL, 1, 1, 'Cambio de aceite y revisión de niveles', 1.00),
-- Trabajos en progreso (estado 2)
(2, 6, '2025-08-30 08:00:00', NULL, 2, 2, 'Reparación de transmisión automática', 4.00),
(2, 7, '2025-08-30 10:30:00', NULL, 1, 2, 'Alineación y balanceo de llantas', 2.00),
-- Trabajo completado (estado 3)
(2, 3, '2025-08-29 09:00:00', '2025-08-29 12:00:00', 1, 3, 'Cambio de pastillas de freno completado', 3.00);

-- Asignaciones de trabajo para el empleado
INSERT INTO jobs_assignments (job_id, user_id, role_assignment, notes) VALUES
(3, 2, 'empleado', 'Mantenimiento preventivo asignado'),
(4, 2, 'empleado', 'Reparación de frenos asignada'),
(5, 2, 'empleado', 'Cambio de aceite asignado'),
(6, 2, 'empleado', 'Reparación de transmisión asignada'),
(7, 2, 'empleado', 'Alineación asignada'),
(8, 2, 'empleado', 'Trabajo de frenos completado');

-- Servicios para los nuevos trabajos
INSERT INTO jobs_services (job_id, service_type_id, quantity, price) VALUES
(3, 1, 1, 50.00),  -- Cambio de aceite
(3, 3, 1, 30.00),  -- Rotación de llantas
(4, 2, 1, 120.00), -- Reparación de frenos
(5, 1, 1, 50.00),  -- Cambio de aceite
(6, 2, 1, 120.00), -- Reparación (transmisión)
(7, 3, 1, 30.00),  -- Alineación
(8, 2, 1, 120.00); -- Frenos completado

-- Partes para los nuevos trabajos
INSERT INTO jobs_parts (job_id, part_id, quantity, price) VALUES
(3, 1, 1, 15.00),  -- Filtro de aceite
(4, 2, 2, 60.00),  -- Pastillas de freno
(5, 1, 1, 15.00),  -- Filtro de aceite
(6, 2, 4, 60.00),  -- Pastillas para transmisión
(7, 1, 1, 15.00),  -- Filtro
(8, 2, 2, 60.00);  -- Pastillas completadas

