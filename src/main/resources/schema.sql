CREATE TABLE IF NOT EXISTS "users" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(25),
  hash VARCHAR(255) NOT NULL,
  salt VARCHAR(255) NOT NULL,
  UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS "user_details" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  firstname VARCHAR(50),
  lastname VARCHAR(50),
  birthdate DATE,
  phone VARCHAR(25),
  UNIQUE (user_id),
  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "user_addresses" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  address VARCHAR(255),
  ward VARCHAR(50),
  subdistrict VARCHAR(50),
  city VARCHAR(50),
  state VARCHAR(50),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION,
  UNIQUE (user_id),
  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "institutions" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(100) NOT NULL,
  type VARCHAR(25) NULL,
  UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS "institution_addresses" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  institution_id UUID NOT NULL,
  address VARCHAR(255) NOT NULL,
  ward VARCHAR(50),
  subdistrict VARCHAR(50),
  city VARCHAR(50),
  state VARCHAR(50),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION,
  UNIQUE (institution_id),
  CONSTRAINT fk_institution FOREIGN KEY(institution_id) REFERENCES institutions (id) ON DELETE CASCADE
);
