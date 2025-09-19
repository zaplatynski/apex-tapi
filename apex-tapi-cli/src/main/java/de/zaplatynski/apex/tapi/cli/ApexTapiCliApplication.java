package de.zaplatynski.apex.tapi.cli;

import de.zaplatynski.apex.tapi.core.ApexTapiConfig;
import de.zaplatynski.apex.tapi.core.ApexTapiGenerator;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

/**
 * Command-line application for generating APEX Table APIs.
 */
@Command(
    name = "apex-tapi-cli",
    mixinStandardHelpOptions = true,
    version = "apex-tapi-cli 1.0.0",
    description = "Generate Table APIs for Oracle APEX Interactive Grids"
)
public class ApexTapiCliApplication implements Callable<Integer> {
    
    @Parameters(index = "0", description = "The database table name")
    private String tableName;
    
    @Parameters(index = "1", description = "The PL/SQL package name")
    private String packageName;
    
    @Option(names = {"-s", "--schema"}, description = "The schema name")
    private String schemaName;
    
    @Option(names = {"-o", "--output"}, description = "Output directory (default: current directory)")
    private File outputDirectory = new File(".");
    
    @Option(names = {"--no-insert"}, description = "Skip generating insert procedures")
    private boolean noInsert;
    
    @Option(names = {"--no-update"}, description = "Skip generating update procedures")
    private boolean noUpdate;
    
    @Option(names = {"--no-delete"}, description = "Skip generating delete procedures")
    private boolean noDelete;
    
    @Option(names = {"-v", "--verbose"}, description = "Verbose output")
    private boolean verbose;
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ApexTapiCliApplication()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public Integer call() throws Exception {
        if (verbose) {
            System.out.println("Generating APEX Table API...");
            System.out.println("Table: " + tableName);
            System.out.println("Package: " + packageName);
            if (schemaName != null) {
                System.out.println("Schema: " + schemaName);
            }
            System.out.println("Output Directory: " + outputDirectory.getAbsolutePath());
        }
        
        try {
            // Create configuration
            ApexTapiConfig config = new ApexTapiConfig(tableName, packageName);
            config.setSchemaName(schemaName);
            config.setGenerateInsert(!noInsert);
            config.setGenerateUpdate(!noUpdate);
            config.setGenerateDelete(!noDelete);
            
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
            
            System.out.println("Generated API written to: " + outputFile.toString());
            return 0;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        } catch (IOException e) {
            System.err.println("Failed to write generated API: " + e.getMessage());
            return 1;
        }
    }
}