-- liquibase formatted sql

-- changeset skorneev:1
CREATE TABLE wallet (
                        id UUID PRIMARY KEY,
                        balance BIGINT NOT NULL
);