--liquibase formatted sql

--changeset magaiver:create-table-transaction

CREATE TABLE transaction (
  id SERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  type VARCHAR(20) NOT NULL,
  amount NUMERIC(12,2) NOT NULL,
  date TIMESTAMP NOT NULL,
  category_id BIGINT NOT NULL,
  account_id BIGINT NOT NULL,
  enable BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_transaction_id PRIMARY KEY (id),
  CONSTRAINT fk_transaction_category_id FOREIGN KEY(category_id) references category,
  CONSTRAINT fk_transaction_account_id FOREIGN KEY(account_id) references account
);

CREATE INDEX ix_account_id_transaction on transaction (account_id);