--liquibase formatted sql

--changeset magaiver:insert-default-account

INSERT INTO account (id, name, description, enable, user_id, created_at, updated_at) VALUES(1, 'Banco 1','Conta bancária', true, 1, current_date, current_date );