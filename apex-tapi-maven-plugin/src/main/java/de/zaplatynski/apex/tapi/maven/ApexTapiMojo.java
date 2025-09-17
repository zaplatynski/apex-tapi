package de.zaplatynski.apex.tapi.maven;

import de.zaplatynski.apex.tapi.core.ApexTapiConfig;
import de.zaplatynski.apex.tapi.core.ApexTapiGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Maven plugin for generating APEX Table APIs.
 */
@Mojo(name = "generate-tapi", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ApexTapiMojo extends AbstractMojo {
    
    /**
     * The name of the database table to generate the API for.
     */
    @Parameter(property = "tableName", required = true)
    private String tableName;
    
    /**
     * The name of the PL/SQL package to generate.
     */
    @Parameter(property = "packageName", required = true)
    private String packageName;
    
    /**
     * The schema name (optional).
     */
    @Parameter(property = "schemaName")
    private String schemaName;
    
    /**
     * Whether to generate insert procedures.
     */
    @Parameter(property = "generateInsert", defaultValue = "true")
    private boolean generateInsert;
    
    /**
     * Whether to generate update procedures.
     */
    @Parameter(property = "generateUpdate", defaultValue = "true")
    private boolean generateUpdate;
    
    /**
     * Whether to generate delete procedures.
     */
    @Parameter(property = "generateDelete", defaultValue = "true")
    private boolean generateDelete;
    
    /**
     * Output directory for generated files.
     */
    @Parameter(property = "outputDirectory", defaultValue = "${project.build.directory}/generated-sources/apex-tapi")
    private File outputDirectory;
    
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating APEX Table API for table: " + tableName);
        
        try {
            // Create configuration
            ApexTapiConfig config = new ApexTapiConfig(tableName, packageName);
            config.setSchemaName(schemaName);
            config.setGenerateInsert(generateInsert);
            config.setGenerateUpdate(generateUpdate);
            config.setGenerateDelete(generateDelete);
            
            // Generate API
            ApexTapiGenerator generator = new ApexTapiGenerator();
            generator.validateConfig(config);
            String generatedApi = generator.generateTableApi(config);
            
            // Ensure output directory exists
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }
            
            // Write generated API to file
            Path outputFile = Paths.get(outputDirectory.getAbsolutePath(), packageName + ".sql");
            Files.write(outputFile, generatedApi.getBytes());
            
            getLog().info("Generated API written to: " + outputFile.toString());
            
        } catch (IllegalArgumentException e) {
            throw new MojoFailureException("Invalid configuration: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to write generated API: " + e.getMessage(), e);
        }
    }
}