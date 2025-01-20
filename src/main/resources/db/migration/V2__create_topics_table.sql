-- Crea la tabla 'topics'
CREATE TABLE IF NOT EXISTS topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    course VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ABIERTO', 'CERRADO') NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Inserta datos iniciales (si es necesario)
INSERT INTO topics (title, content, course, status, user_id)
VALUES
('Primer Tópico', 'Contenido del primer tópico', 'Curso A', 'ABIERTO', 1),
('Segundo Tópico', 'Contenido del segundo tópico', 'Curso B', 'CERRADO', 1);