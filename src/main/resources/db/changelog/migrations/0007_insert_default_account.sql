--liquibase formatted sql

--changeset magaiver:insert-default-account

INSERT INTO account VALUES(1, 'Banco 1','Conta bancária');