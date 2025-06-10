-- V1__init_schema.sql

-- 0) Создаём enum для ролей
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'role_enum') THEN
    CREATE TYPE role_enum AS ENUM ('STUDENT','TEACHER','ADMIN');
  END IF;
END$$;

-- 1) Пользователи
CREATE TABLE users (
  id          BIGSERIAL    PRIMARY KEY,
  email       VARCHAR(255) NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,
  full_name   VARCHAR(255),
  group_name  VARCHAR(100),
  faculty     VARCHAR(100),
  avatar_url  VARCHAR(512),
  bio         TEXT,
  visibility  VARCHAR(20)  NOT NULL DEFAULT 'PRIVATE'
);

-- 2) Enum для типов достижений
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'achievement_type') THEN
    CREATE TYPE achievement_type AS ENUM (
      'ACADEMIC',
      'SPORT',
      'SOCIAL',
      'CREATIVE'
    );
  END IF;
END$$;

-- 3) Достижения
CREATE TABLE achievements (
  id           BIGSERIAL        PRIMARY KEY,
  student_id   BIGINT           NOT NULL
                   REFERENCES users(id) ON DELETE CASCADE,
  title        VARCHAR(255)     NOT NULL,
  description  TEXT,
  type         achievement_type,
  date         DATE,
  tags         VARCHAR(255),
  file_url     VARCHAR(512)
);

-- 4) Комментарии
CREATE TABLE comments (
  id             BIGSERIAL    PRIMARY KEY,
  achievement_id BIGINT       NOT NULL
                     REFERENCES achievements(id) ON DELETE CASCADE,
  author_id      BIGINT       NOT NULL
                     REFERENCES users(id)        ON DELETE CASCADE,
  text           TEXT         NOT NULL,
  created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 5) Рейтинг
CREATE TABLE ratings (
  id             BIGSERIAL    PRIMARY KEY,
  achievement_id BIGINT       NOT NULL
                     REFERENCES achievements(id) ON DELETE CASCADE,
  author_id      BIGINT       NOT NULL
                     REFERENCES users(id)        ON DELETE CASCADE,
  stars          INTEGER      NOT NULL CHECK (stars BETWEEN 1 AND 5),
  created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_ratings UNIQUE (achievement_id, author_id)
);

-- 6) Таблица user_roles для хранения ролей
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL
    REFERENCES users(id)
    ON DELETE CASCADE,
  role    role_enum NOT NULL
);

-- 7) Индексы для ускорения выборок
CREATE INDEX idx_achievements_student   ON achievements(student_id);
CREATE INDEX idx_comments_achievement   ON comments(achievement_id);
CREATE INDEX idx_comments_author        ON comments(author_id);
CREATE INDEX idx_ratings_achievement    ON ratings(achievement_id);
CREATE INDEX idx_ratings_author         ON ratings(author_id);
CREATE INDEX idx_user_roles_user        ON user_roles(user_id);
