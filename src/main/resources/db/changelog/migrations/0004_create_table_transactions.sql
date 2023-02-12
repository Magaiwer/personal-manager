--liquibase formatted sql

--changeset magaiver:create-table-transactions

CREATE TABLE TRANSACTION (
  id SERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  transaction_type VARCHAR(20) NOT NULL,
  amount NUMERIC(12,2) NOT NULL,
  date TIMESTAMP NOT NULL,
  category_id BIGINT,
  account_id BIGINT,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT pk_transaction_id PRIMARY KEY (id),
  CONSTRAINT fk_transaction_category_id FOREIGN KEY(category_id) references category
);