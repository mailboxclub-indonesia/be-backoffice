CREATE TABLE IF NOT EXISTS "users" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(50),
  hash VARCHAR(255) NOT NULL,
  salt VARCHAR(255) NOT NULL,

  last_login TIMESTAMP WITH TIME ZONE,
  created_at TIMESTAMP WITH TIME ZONE,
  updated_at TIMESTAMP WITH TIME ZONE,

  UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS "user_details" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  firstname VARCHAR(50),
  lastname VARCHAR(50),
  gender VARCHAR(10) NULL,
  birthdate DATE,
  phone VARCHAR(25),

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE (user_id),

  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
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

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE (user_id),

  CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS "institutions" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(100) NOT NULL,
  type VARCHAR(25) NULL,

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE(name),

  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
  );

CREATE TABLE IF NOT EXISTS "user_institutions" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  institution_id UUID NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE (user_id, institution_id),

  CONSTRAINT fk_user_institutions_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT fk_user_institutions_institution FOREIGN KEY (institution_id) REFERENCES institutions (id) ON DELETE RESTRICT,
  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
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

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE (institution_id),

  CONSTRAINT fk_institution_id FOREIGN KEY(institution_id) REFERENCES institutions (id) ON DELETE CASCADE,
  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS "customers" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID,
  institution_id UUID,
  firstname VARCHAR(50),
  lastname VARCHAR(50),
  gender VARCHAR(10) NULL,
  birthdate DATE,
  phone VARCHAR(25) UNIQUE,
  email VARCHAR(25) UNIQUE,

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS "customer_addresses" (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  address VARCHAR(255) NOT NULL,
  ward VARCHAR(50),
  subdistrict VARCHAR(50),
  city VARCHAR(50),
  state VARCHAR(50),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION,

  created_at TIMESTAMP WITH TIME ZONE,
  created_by UUID,
  updated_at TIMESTAMP WITH TIME ZONE,
  updated_by UUID,

  UNIQUE (customer_id),

  CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customers (id) ON DELETE CASCADE,
  FOREIGN KEY(created_by) REFERENCES users (id),
  FOREIGN KEY(updated_by) REFERENCES users (id)
);
