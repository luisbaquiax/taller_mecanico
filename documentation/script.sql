DROP DATABASE IF EXISTS taller_mecanico;
CREATE DATABASE taller_mecanico;
USE taller_mecanico;

-- roles
CREATE TABLE rols_user(
    rol_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_rol VARCHAR(100) NOT NULL,
    description_rol VARCHAR(200) NOT NULL
);

-- type method two factors
CREATE TABLE type_two_factor(
    type_two_factor_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_type_two_factor VARCHAR(200) NOT NULL,
    description_type_two_factor VARCHAR(200) NOT NULL
);

-- users
CREATE TABLE users(
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rol_id INT NOT NULL,
    username VARCHAR(100) NOT NULL,
    password TEXT NOT NULL,
    is_active TINYINT NOT NULL DEFAULT 1,
    email VARCHAR(200) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    two_factor_auth TINYINT NOT NULL DEFAULT 0,
    type_two_factor_id INT,
    FOREIGN KEY (rol_id) REFERENCES rols_user(rol_id),
    FOREIGN KEY (type_two_factor_id) REFERENCES type_two_factor(type_two_factor_id)
);

CREATE TABLE password_reset(
    reset_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    used TINYINT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE clients(
    client_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nit VARCHAR(13) NOT NULL,
    address VARCHAR(200) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE vehicles(
    vehicle_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    licence_plate VARCHAR(200) NOT NULL UNIQUE,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year VARCHAR(4) NOT NULL,
    color VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

-- suppliers
CREATE TABLE suppliers(
    supplier_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type_supplier ENUM('person', 'company') NOT NULL,-- persona o empresa
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- services types
CREATE TABLE services_types(
    service_type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_service_type VARCHAR(100) NOT NULL,
    description_service_type VARCHAR(200) NOT NULL,
    base_price DECIMAL(10,2) NOT NULL
);

-- parts
CREATE TABLE parts(
    part_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    name_part VARCHAR(100) NOT NULL,
    brand_part VARCHAR(100) NOT NULL,
    description_part VARCHAR(200) NOT NULL,
    cost_price DECIMAL(10,2) NOT NULL,
    sale_price DECIMAL(10,2) NOT NULL,
    stock_part INT NOT NULL,
    is_active TINYINT NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

-- inventary movements
CREATE TABLE inventary_movements(
    mv_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    part_id INT NOT NULL,
    created_by INT,
    type_movement ENUM('entrada', 'salida', 'ajuste') NOT NULL,
    reference VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (part_id) REFERENCES parts(part_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- type-jobs
CREATE TABLE type_jobs(
    type_job_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_type_job VARCHAR(100) NOT NULL,
    description_type_job VARCHAR(200) NOT NULL
);

-- status-jobs
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

-- jobs
CREATE TABLE jobs(
    job_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_by INT NOT NULL,
    vehicle_id INT NOT NULL,
    started_at DATETIME NULL,
    finished_at DATETIME NULL,
    type_job_id INT NOT NULL,
    status_job_id INT NOT NULL,
    description TEXT,
    estimated_hours DECIMAL(6,2) DEFAULT 0.00,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
    FOREIGN KEY (type_job_id) REFERENCES type_jobs(type_job_id),
    FOREIGN KEY (status_job_id) REFERENCES status_jobs(status_job_id)
);

-- jobs services
CREATE TABLE jobs_services(
    job_service_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    service_type_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (service_type_id) REFERENCES services_types(service_type_id)
);

-- jobs parts
CREATE TABLE jobs_parts(
    job_part_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    part_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- status update jobs
CREATE TABLE status_update_jobs(
    status_update_job_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_status_update_job VARCHAR(100) NOT NULL,
    description_status_update_job VARCHAR(200) NOT NULL
);

-- updates jobs
CREATE TABLE updates_jobs(
    update_job_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    created_by INT NULL,
    status_update_job_id INT NOT NULL,
    notes TEXT,
    hours_spent DECIMAL(6,2) DEFAULT 0.00, -- horas trabajadas
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (status_update_job_id) REFERENCES status_update_jobs(status_update_job_id),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- jobs assigments
CREATE TABLE jobs_assignments(
    job_assignment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    role_assignment ENUM('empleado','especialista') NOT NULL,
    notes TEXT,
    assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    unassigned_at DATETIME DEFAULT NULL,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- invoices
CREATE TABLE invoices(
    invoice_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    client_id INT NOT NULL,
    total DOUBLE NOT NULL DEFAULT 0.00,
    invoince_status ENUM('pendiente','pagada','anulada') DEFAULT 'pendiente',
    inssued_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

-- payments
CREATE TABLE payments(
    payment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    payment_method ENUM('efectivo','tarjeta','transferencia') DEFAULT 'efectivo',
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
);

-- quotations
CREATE TABLE quotations(
    quotation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    client_id INT NOT NULL,
    total DOUBLE NOT NULL DEFAULT 0.00,
    quotation_status ENUM('pendiente','aceptada','rechazada') DEFAULT 'pendiente',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    valid_until DATETIME NULL,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

-- quotation items
CREATE TABLE quotation_items(
    quotation_item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quotation_id INT NOT NULL,
    description VARCHAR(255),
    quantity INT DEFAULT 1,
    unit_price DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (quotation_id) REFERENCES quotations(quotation_id)
);
