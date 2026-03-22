CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE cursos_usuarios (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT UNIQUE,
    curso_id BIGINT,
    CONSTRAINT fk_cursos_usuarios_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);