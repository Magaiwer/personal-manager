--liquibase formatted sql

--changeset magaiver:create-table-account

CREATE TABLE account
(
    id          SERIAL      NOT NULL,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    enable      BOOLEAN NOT NULL DEFAULT TRUE,
    user_id     BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_account_id PRIMARY KEY (id),
    CONSTRAINT fk_user_id_account FOREIGN KEY(user_id) REFERENCES users
);

CREATE INDEX ix_user_id_account on account (user_id);