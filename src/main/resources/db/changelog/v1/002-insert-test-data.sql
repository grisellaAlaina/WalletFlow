-- liquibase formatted sql

-- changeset skorneev:2
INSERT INTO wallet (id, balance) VALUES
                                     ('550e8400-e29b-41d4-a716-446655440000', 1000),
                                     ('660e8400-e29b-41d4-a716-446655440001', 2000),
                                     ('770e8400-e29b-41d4-a716-446655440002', 3000);