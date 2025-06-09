CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  group_name VARCHAR(100),
  faculty VARCHAR(100),
  avatar_url VARCHAR(512),
  bio TEXT,
  visibility VARCHAR(20) NOT NULL DEFAULT 'PRIVATE'
);

CREATE TABLE achievements (
  id SERIAL PRIMARY KEY,
  student_id INTEGER NOT NULL REFERENCES users(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  type VARCHAR(50),
  date DATE,
  tags VARCHAR(255),
  file_url VARCHAR(512)
);
