package de.zaplatynski.apex.tapi.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApexTapiGeneratorTest {
    
    private ApexTapiGenerator generator;
    
    @BeforeEach
    void setUp() {
        generator = new ApexTapiGenerator();
    }
    
    @Test
    void generateTableApi_withValidConfig_returnsGeneratedApi() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig("EMPLOYEES", "EMP_API");
        
        // When
        String result = generator.generateTableApi(config);
        
        // Then
        assertNotNull(result);
        assertTrue(result.contains("EMPLOYEES"));
        assertTrue(result.contains("EMP_API"));
    }
    
    @Test
    void generateTableApi_withNullConfig_throwsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generateTableApi(null);
        });
    }
    
    @Test
    void validateConfig_withValidConfig_doesNotThrow() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig("EMPLOYEES", "EMP_API");
        
        // When & Then
        assertDoesNotThrow(() -> {
            generator.validateConfig(config);
        });
    }
    
    @Test
    void validateConfig_withNullConfig_throwsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.validateConfig(null);
        });
    }
    
    @Test
    void validateConfig_withNullTableName_throwsException() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig(null, "EMP_API");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.validateConfig(config);
        });
    }
    
    @Test
    void validateConfig_withEmptyTableName_throwsException() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig("", "EMP_API");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.validateConfig(config);
        });
    }
    
    @Test
    void validateConfig_withNullPackageName_throwsException() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig("EMPLOYEES", null);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.validateConfig(config);
        });
    }
    
    @Test
    void validateConfig_withEmptyPackageName_throwsException() {
        // Given
        ApexTapiConfig config = new ApexTapiConfig("EMPLOYEES", "");
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            generator.validateConfig(config);
        });
    }
}