-- Insertar usuarios de prueba
INSERT INTO users (username, password, enabled, role)
VALUES
('admin', '$2a$10$brRMIrYHSUucsLBpi8cwz.vd.L0eiuWXbs1SEZKAN6RFyafR.oCz6', 1, 'ROLE_ADMIN'),
('user1', '$2a$10$brRMIrYHSUucsLBpi8cwz.vd.L0eiuWXbs1SEZKAN6RFyafR.oCz6', 1, 'ROLE_USER');

-- Insertar tópicos de prueba
INSERT INTO topics (title, content, course, status, user_id)
VALUES
('Primer Tópico', 'Contenido del primer tópico', 'Curso A', 'ABIERTO', 1),
('Segundo Tópico', 'Contenido del segundo tópico', 'Curso B', 'CERRADO', 2);