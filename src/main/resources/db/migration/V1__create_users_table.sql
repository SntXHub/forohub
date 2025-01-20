-- Crea la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    role VARCHAR(255) NOT NULL
);

-- Inserta datos iniciales (solo usuario 'admin')
INSERT INTO users (username, password, enabled, role)
VALUES
('admin', '$2a$10$brRMIrYHSUucsLBpi8cwz.vd.L0eiuWXbs1SEZKAN6RFyafR.oCz6', 1, 'ROLE_ADMIN');