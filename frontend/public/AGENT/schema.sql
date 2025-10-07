-- Base de datos para sistema de barbería
CREATE TABLE barberos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    telefono VARCHAR(20),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE tipos_corte (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    duracion_minutos INT NOT NULL,
    descripcion TEXT
);

CREATE TABLE citas (
    id SERIAL PRIMARY KEY,
    barbero_id INT REFERENCES barberos(id),
    tipo_corte_id INT REFERENCES tipos_corte(id),
    cliente_nombre VARCHAR(100),
    cliente_telefono VARCHAR(20),
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) DEFAULT 'pendiente',
    notas TEXT
);

-- Datos de prueba
INSERT INTO barberos (nombre, especialidad, telefono, activo) VALUES
('Juan Pérez', 'Cortes clásicos', '3001234567', true),
('María García', 'Cortes modernos', '3007654321', true),
('Pedro López', 'Barbería tradicional', '3009876543', true);

INSERT INTO tipos_corte (nombre, precio, duracion_minutos, descripcion) VALUES
('Corte Clásico', 25000, 30, 'Corte tradicional con tijera y máquina'),
('Corte Moderno', 35000, 45, 'Corte degradado con diseño'),
('Corte + Barba', 45000, 60, 'Corte completo más arreglo de barba'),
('Afeitado Clásico', 20000, 30, 'Afeitado tradicional con navaja'),
('Diseño Especial', 50000, 60, 'Diseños personalizados y detallados');