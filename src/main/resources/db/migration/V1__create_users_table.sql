-- Crear la tabla 'users' si no existe
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled TINYINT(1) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Agregar un usuario inicial (opcional, para pruebas)
INSERT INTO users (username, password, enabled, role) VALUES
('admin', '$2a$10$7QmxTIdfh/1fYdWbG6YksOon7BuFeivU/3E0QyZ9ZP1MsJX7MxOla', true, 'ROLE_ADMIN');