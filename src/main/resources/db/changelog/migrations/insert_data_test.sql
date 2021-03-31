--liquibase formatted sql

--changeset magaiver:data_test_after_migrate context:test

INSERT INTO TRANSACTION (id, name, transaction_type, amount, date, category_id, enabled) VALUES(null, 'Internet','EXPENSE',134.00, current_date, 1, true );
INSERT INTO TRANSACTION (id, name, transaction_type, amount, date, category_id, enabled) VALUES(null, 'Condominio','EXPENSE',200.00, current_date, 1, true );
INSERT INTO TRANSACTION (id, name, transaction_type, amount, date, category_id, enabled) VALUES(null, 'Mercado','EXPENSE',100.00, current_date, 1, true );
