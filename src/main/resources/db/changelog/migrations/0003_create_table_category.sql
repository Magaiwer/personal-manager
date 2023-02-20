--liquibase formatted sql

--changeset magaiver:create-table-category

CREATE TABLE CATEGORY (
  id SERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(150),
  icon VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_category_id PRIMARY KEY (id),
  CONSTRAINT  uk_name_category unique(name)
);