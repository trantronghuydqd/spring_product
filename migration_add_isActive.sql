-- Migration script: Add isActive column to all tables
-- Run this SQL script to update existing database

-- Add isActive column to product table
ALTER TABLE product ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Add isActive column to category table  
ALTER TABLE category ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Add isActive column to customer table
ALTER TABLE customer ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Add isActive column to comment table
ALTER TABLE comment ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Update all existing records to be active
UPDATE product SET is_active = TRUE WHERE is_active IS NULL;
UPDATE category SET is_active = TRUE WHERE is_active IS NULL;
UPDATE customer SET is_active = TRUE WHERE is_active IS NULL;
UPDATE comment SET is_active = TRUE WHERE is_active IS NULL;
