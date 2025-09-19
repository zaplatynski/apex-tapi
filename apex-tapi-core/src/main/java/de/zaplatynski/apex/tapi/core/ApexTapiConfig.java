package de.zaplatynski.apex.tapi.core;

/**
 * Configuration class for APEX Table API generation.
 */
public class ApexTapiConfig {
    
    private String tableName;
    private String packageName;
    private String schemaName;
    private boolean generateInsert = true;
    private boolean generateUpdate = true;
    private boolean generateDelete = true;
    
    public ApexTapiConfig() {
        // Default constructor
    }
    
    public ApexTapiConfig(String tableName, String packageName) {
        this.tableName = tableName;
        this.packageName = packageName;
    }
    
    // Getters and setters
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getSchemaName() {
        return schemaName;
    }
    
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    
    public boolean isGenerateInsert() {
        return generateInsert;
    }
    
    public void setGenerateInsert(boolean generateInsert) {
        this.generateInsert = generateInsert;
    }
    
    public boolean isGenerateUpdate() {
        return generateUpdate;
    }
    
    public void setGenerateUpdate(boolean generateUpdate) {
        this.generateUpdate = generateUpdate;
    }
    
    public boolean isGenerateDelete() {
        return generateDelete;
    }
    
    public void setGenerateDelete(boolean generateDelete) {
        this.generateDelete = generateDelete;
    }
    
    @Override
    public String toString() {
        return "ApexTapiConfig{" +
                "tableName='" + tableName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", generateInsert=" + generateInsert +
                ", generateUpdate=" + generateUpdate +
                ", generateDelete=" + generateDelete +
                '}';
    }
}