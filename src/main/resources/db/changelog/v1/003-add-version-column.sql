-- liquibase formatted sql

-- changeset skorneev:3
ALTER TABLE wallet
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;