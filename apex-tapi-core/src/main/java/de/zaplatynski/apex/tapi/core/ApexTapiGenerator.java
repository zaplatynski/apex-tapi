package de.zaplatynski.apex.tapi.core;

/**
 * Core service for generating Table APIs for Oracle APEX Interactive Grids.
 * This is the main entry point for the core functionality.
 */
public class ApexTapiGenerator {
    
    /**
     * Generates a Table API based on the provided configuration.
     * 
     * @param config the configuration for generating the Table API
     * @return the generated Table API as a string
     */
    public String generateTableApi(ApexTapiConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Configuration cannot be null");
        }
        
        // This is a placeholder implementation
        // In a real implementation, this would generate the actual Table API code
        return String.format("-- Generated Table API for table: %s%n", config.getTableName()) +
               String.format("-- Package: %s%n", config.getPackageName()) +
               "-- This is a placeholder implementation";
    }
    
    /**
     * Validates the provided configuration.
     * 
     * @param config the configuration to validate
     * @throws IllegalArgumentException if the configuration is invalid
     */
    public void validateConfig(ApexTapiConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Configuration cannot be null");
        }
        
        if (config.getTableName() == null || config.getTableName().trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }
        
        if (config.getPackageName() == null || config.getPackageName().trim().isEmpty()) {
            throw new IllegalArgumentException("Package name cannot be null or empty");
        }
    }
}